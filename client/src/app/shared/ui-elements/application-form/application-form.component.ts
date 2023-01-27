import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Validators, FormGroup, FormBuilder } from '@angular/forms';

import { WorkflowService, ApplicationModalService, UserService, RolesService } from '../../services';
import { Profile } from '../../models';
import { AppSettings } from '../../../app.settings';

@Component({
    selector: 'cbs-application-form-modal',
    templateUrl: 'application-form.component.html',
    styleUrls: ['./application-form.component.scss']
})
export class ApplicationFormModalComponent implements OnInit {
    @Output() createApplication = new EventEmitter();
    public opened = false;
    public profile: Profile;
    public applicationForm: FormGroup;
    public workflows: any = [''];
    public workflowFields: any = [];
    public isSubmiting = false;
    public isResponseStatusOk = 'null';
    public currentWorkflowStates: Object[] = [];
    public currentStepId = 0;
    public formDataToSubmit: Object = {};
    public defaultWorkflow;
    public applicationStep: Object[] = [
        {
            id: 0,
            isCurrent: true,
            title: 'Application Details',
            complete: false
        },
        {
            id: 1,
            isCurrent: false,
            title: 'States and Permissions',
            complete: false
        },
        {
            id: 2,
            isCurrent: false,
            title: 'Confirm and Submit',
            complete: false
        }
    ];

    public users: Object[] = [];
    public roles: Object[] = [];

    private workflowsData: any;
    private statePermissions: any = [];

    constructor(
        private fb: FormBuilder,
        private workflowService: WorkflowService,
        private applicationModalService: ApplicationModalService,
        private userService: UserService,
        private rolesService: RolesService) { }

    ngOnInit() {
        this.buildForm(new Profile());
        this.userService.getAllUsers().subscribe(
            resp => this.users = resp,
            err => console.log(err)
        );
        this.rolesService.getRoles().subscribe(
            resp => this.roles = resp.content,
            err => console.log(err)
        );
        this.workflowService.getWorkflows().subscribe(
            resp => {
                this.workflowsData = resp;
                this.workflows.pop();
                resp.content.map(item => this.workflows.push(item.name));

                if (this.workflowsData.content  && this.workflowsData.content.length > 0) {

                    let defaultWorkflows = this.workflowsData.content.filter(w => w.isDefault);

                    if (defaultWorkflows  && defaultWorkflows.length > 0) {
                        this.defaultWorkflow = defaultWorkflows[0];
                    }
                }
            },
            err => console.log(err)
        );

        this.applicationModalService.applicationModalChanged$.subscribe(resp => {
            if (resp.status === 'success') {
                this.isSubmiting = false;
                this.isResponseStatusOk = 'ok';
                setTimeout(() => {
                    this.isResponseStatusOk = 'null';
                    this.opened = false;
                }, AppSettings.SUCCESS_DELAY);
            } else if (resp.status === 'error') {
                this.isSubmiting = false;
                this.isResponseStatusOk = 'error';
                setTimeout(() => {
                    this.isResponseStatusOk = 'null';
                    this.opened = false;
                }, AppSettings.ERROR_DELAY);
            }
        });
    }

    buildForm(profileName) {
        this.applicationForm = this.fb.group({
            name: ['', [Validators.required]],
            responsibleUserId: ['', [Validators.required]],
            profileId: [`${profileName.id}`, [Validators.required]],
            workflowId: [this.defaultWorkflow ? this.defaultWorkflow.name : '', [Validators.required]]
        });
    }

    getStates(id) {
        this.workflowService.getWorkflowStates(id).subscribe(
            resp => {
                this.currentWorkflowStates = resp;
            },
            err => console.log(err)
        );
    }

    getWorkflowIdFromName(name) {
        let obj = this.workflowsData.content.filter(item => item['name'] === name);
        if (obj.length === 1) {
            return obj[0]['id'];
        } else {
            return null;
        }
    }

    getWorkflowNameFormId(id) {
        let obj = this.workflowsData.content.filter(item => item['id'] === id);
        if (obj.length === 1) {
            return obj[0]['name'];
        } else {
            return null;
        }
    }
    getUserNameFromId(id) {
        let obj = this.users.filter(item => item['id'] === id);
        if (obj.length) {
            return `${obj[0]['firstName']} ${obj[0]['lastName']}`;
        } else {
            return '';
        }
    }

    openModal(profile: Profile) {
        this.currentStepId = 0;
        this.buildForm(profile);
        this.opened = true;
        this.profile = profile;
        this.setCurrentStep(0, true, true);
    }

    cancel() {
        this.opened = false;
    }

    changeWorkflowId(obj): void {
        this.workflowsData.content.map(item => {
            if (item.name === obj.workflowId) {
                obj.workflowId = item.id;
            }
        });
    }

    setCurrentStep(id: number, isNext: boolean, dropAll?: boolean) {
        this.applicationStep.map(item => {
            // Temp work
            if (isNext) {
                if (item['id'] === (id - 1)) {
                    item['complete'] = true;
                }
            } else {
                if (item['id'] === (id + 1)) {
                    item['complete'] = false;
                }
            }
            if (item['id'] === id) {
                item['isCurrent'] = true;
            } else {
                item['isCurrent'] = false;
            }

            if (dropAll) {
                item['complete'] = false;
            }
        });
    }

    getFieldNameFromId(id) {
        this.workflowFields.map(item => {
            console.log(item, id);
            if (item['id'] === +id) {
                console.log(item.caption);
                return item.caption;
            }
        });
    }

    buildData(data) {
        this.formDataToSubmit['applicationName'] = data.name;
        this.formDataToSubmit['workflowName'] = this.getWorkflowNameFormId(+data.workflowId);
        this.formDataToSubmit['responsibleUser'] = this.getUserNameFromId(+data.responsibleUserId);
        this.formDataToSubmit['clientName'] = `${this.profile.firstName} ${this.profile.lastName}`;
    }

    goBack(id) {
        this.currentStepId = id;
        this.setCurrentStep(id, false);
    }

    goNext(id, {value, valid}) {
        if (valid && id === 1) {
            this.changeWorkflowId(value);
            this.getStates(value.workflowId);
        } else if (valid && id === 2) {
            this.buildData(value);
        }
        this.currentStepId = id;
        this.setCurrentStep(id, true);
    }

    handlePermissions(item) {
        this.statePermissions[item[1]] = item[0];
    }

    addPermissionsToSendData(formData) {
        formData['statePermissions'] = [];
        this.statePermissions.map(item => {
            let tempObj = {};
            if (item.length) {
                tempObj = {
                    id: this.statePermissions.indexOf(item),
                    permissions: []
                };
                item.map(elem => {
                    if (elem.hasOwnProperty('code')) {
                        tempObj['permissions'].push({ roleId: elem.id });
                    } else if (elem.hasOwnProperty('username')) {
                        tempObj['permissions'].push({ userId: elem.id });
                    }
                });
            }
            if (tempObj.hasOwnProperty('id')) {
                formData['statePermissions'].push(tempObj);
            }
        });
    }

    save({value, valid}) {
        if (valid) {
            this.isSubmiting = true;
            this.changeWorkflowId(value);
            this.addPermissionsToSendData(value);
            this.createApplication.emit(value);
            console.log(value);
        }
    }

}
