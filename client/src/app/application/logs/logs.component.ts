import { Component, OnInit, OnDestroy, ViewChild, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ApplicationService, UserService } from '../../shared';
import { Validators, FormGroup, FormControl, FormBuilder } from '@angular/forms';

import { AppSettings } from '../../app.settings';
import { FormDateComponent } from '../../sharedModules/cbs-dynamic-form/components/form-date/form-date.component';
import { User } from '../../shared/models';

declare var moment: any;

@Component({
    selector: 'cbs-application-logs',
    templateUrl: 'logs.component.html',
    styles: [`textarea {
        height: 13rem
    }`]
})
export class ApplicationLogsComponent implements OnInit, OnDestroy {
    @ViewChild(FormDateComponent, {static: false}) formDateComponent: FormDateComponent;
    public applicationData: any;
    public isLoading = false;
    public errorOnApplicationData = false;
    public opened = false;
    public isSubmitting = false;
    public logForm: FormGroup;
    public isNew: boolean;
    public currentWorkLog: any;
    public noData = true;
    public currentUser: User;
    public hasPermission = false;
    public disableButtonSend: boolean;
    public userPermissions: any;
    public response = '';
    public showReadMore = true;
    public formDateConfig = {
        disabled: false,
        placeholder: '',
        name: 'enteredDate',
        caption: 'Date',
        fieldType: 'DATE',
        required: false,
        value: ''
    };

    public permissionCreateAppWorklogs: string = AppSettings.PERMISSION_NAMES['CREATE_APPLICATION_WORKLOGS'];

    private sub: any;
    private id: number;


    constructor(private route: ActivatedRoute,
                private applicationService: ApplicationService,
                private userService: UserService,
                private fb: FormBuilder) {
    }

    ngOnInit() {
        this.sub = this.route.parent.params.subscribe(params => {
            this.id = +params['id'];
            this.getApplicationLogs(this.id);
        });

        this.logForm = new FormGroup({
            spentHours: new FormControl('', [Validators.required]),
            note: new FormControl('', [Validators.required]),
            enteredDate: new FormControl('', [Validators.required]),
        });

        this.currentUser = this.userService.getCurrentUser();
        this.userPermissions = this.currentUser['role']['permissions'];
        if (this.userPermissions.includes('ALL') || this.userPermissions.includes('EDIT_APPLICATION_WORKLOGS')) {
            this.hasPermission = true;
        }
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    getApplicationLogs(id) {
        this.isLoading = true;
        this.applicationService.getApplicationWorkLogs(id).subscribe(
             resp => {
                    this.applicationData = resp.data.content.map((item) => {
                        let newItem = Object.assign({}, item, {
                            expanded: false
                        });
                        return newItem;
                    });

                this.isLoading = false;
                this.applicationData && this.applicationData.length ? this.noData = false : this.noData = true;
                this.errorOnApplicationData = false;
            },
            err => {
                this.noData = true;
                this.isLoading = false;
                this.errorOnApplicationData = true;
                console.log(err);
            }
        );
    }

    submitWorklog() {
        this.isNew ? this.createForm(this.logForm.value) : this.updateForm(this.logForm.value);
    }

    createForm(value) {
        if (value) {
            value.enteredDate = value.enteredDate + ' 00:00:00';

            this.applicationService
                .postApplicationWorkLog(this.id, value)
                .subscribe(
                    data => {
                        this.getApplicationLogs(this.id);
                        this.opened = false;
                    }
                );
        }
    }

    updateForm(value) {
        if (value) {
            value.enteredDate = value.enteredDate + ' 00:00:00';

            this.applicationService
                .updateApplicationWorkLog(this.id, value, this.currentWorkLog['id'])
                .subscribe(
                    data => {
                        this.getApplicationLogs(this.id);
                        this.opened = false;
                        this.response = 'success';
                        setTimeout(() => {
                            this.response = '';
                        }, 1500);
                    },
                    err => {
                        console.log(err);
                        this.isLoading = false;
                        this.response = 'error';
                        setTimeout(() => {
                            this.response = '';
                        }, 1500);
                        throw new Error(err.message);
                    },
                );
        }
    }

    openCreateModal() {
        this.isNew = true;
        this.logForm.reset();
        this.logForm.controls['enteredDate'].setValue('');
        this.formDateConfig = Object.assign({}, this.formDateConfig, {
            value: 0
        });
        this.formDateComponent.resetValue();
        this.opened = true;
    }

    openEditModal(workLog) {
        this.isNew = false;
        this.opened = true;
        this.currentWorkLog = workLog;
        const currentDate = moment(workLog.enteredDate).format('YYYY-MM-DD');
        this.formDateConfig = Object.assign({}, this.formDateConfig, {
            value: currentDate
        });
        this.logForm.controls['note'].setValue(workLog.note);
        this.logForm.controls['spentHours'].setValue(workLog.spentHours);
        this.logForm.controls['enteredDate'].setValue(workLog.enteredDate);
        this.disableButtonSend = true;
        this.logForm.valueChanges.subscribe(newWorkLog => {
            this.disableButtonSend = (newWorkLog.spentHours === workLog.spentHours
                && newWorkLog.note === workLog.note
                && newWorkLog.enteredDate === moment(workLog.enteredDate).format('YYYY-MM-DD'));
        });
    }

    cancel() {
        this.opened = false;
        this.formDateConfig = Object.assign({}, this.formDateConfig, {
            value: null
        });
    }

    readMore(workLogText, log) {
        log.expanded = !log.expanded;
        workLogText.style.height = log.expanded ? 'max-content' : '65px';
    }
}


