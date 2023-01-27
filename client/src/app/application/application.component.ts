import { Component, OnInit, OnDestroy, Renderer2, ElementRef, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { ApplicationService, AppPermissionsService, UserService } from '../shared';

import { AppSettings } from '../app.settings';
import { ColorService } from '../shared/services/get-color.service';
import { User } from '../shared/models';

@Component({
    selector: 'cbs-application',
    templateUrl: 'application.component.html',
    styleUrls: ['./application.component.scss']
})
export class ApplicationDetailsComponent implements OnInit, OnDestroy {
    private sub: any;
    private cachedCaption: string;
    private isAllAppsPage: boolean;
    public currentUser: User;

    public id: number;
    public isLoading = true;
    public errorOnApplicationData = false;
    public applicationData: any;
    public selectedAction = '';
    public isResponseStatusOk = '';
    public leaveNote = false;
    public actionId: number;
    public isSubmiting = false;
    public dropdownOpen = false;
    public errorMessage = '';
    public hasPermission = false;
    public userPermissions: any;
    public opened = false;
    public errMessage = "Mandatory fields are missing: "

    public formChanged = false;
    public isApplicationNameEditable = false;
    public isApplicationNameEditView = false;
    @ViewChild('name', {static: false, read: ElementRef}) applicationNameInput: ElementRef;

    public isPermissionsVisible = false;
    public isEventsVisible = true;
    public isFieldsVisible = true;
    public isActionsEditable = true;

    public permissionReadAppFields: string = AppSettings.PERMISSION_NAMES['READ_APPLICATION_FIELDS'];
    public permissionReadAppFiles: string = AppSettings.PERMISSION_NAMES['READ_APPLICATION_ATTACHMENTS'];
    public permissionReadAppWorklogs: string = AppSettings.PERMISSION_NAMES['READ_APPLICATION_WORKLOGS'];
    public permissionEditAppDetails: string = AppSettings.PERMISSION_NAMES['EDIT_APPLICATION'];
    public permissionReadAppPermissions: string = AppSettings.PERMISSION_NAMES['READ_APPLICATION_PERMISSIONS'];

    constructor(private route: ActivatedRoute,
                private router: Router,
                private applicationService: ApplicationService,
                private permissionsService: AppPermissionsService,
                private colorService: ColorService,
                private userService: UserService,
                private renderer: Renderer2) {
    }

    ngOnInit() {
        this.sub = this.route.params.subscribe(params => {
            this.id = +params['id'];
            this.getApplicationInfo(this.id);
        });
        this.isAllAppsPage = this.route.url['value'].length && this.route.url['value'][0]['path'] === 'applications';
        if (this.isAllAppsPage && this.permissionsService.requirePermission(this.permissionReadAppPermissions)) {
            this.isPermissionsVisible = true;
        }

        if (this.isAllAppsPage && !this.permissionsService.requirePermission(this.permissionReadAppFields)) {
            this.isFieldsVisible = false;
        }

        if (this.isAllAppsPage && !this.permissionsService.requirePermission(this.permissionEditAppDetails)) {
            this.isActionsEditable = false;
        }

        this.isApplicationNameEditable = this.isAllAppsPage && this.permissionsService.requirePermission(this.permissionEditAppDetails);
        this.currentUser = this.userService.getCurrentUser();
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    cancelConfirm() {
        this.leaveNote = false;
        this.selectedAction = '';
    }

    fireAction(actionName) {
        this.applicationData.actions.map(item => {
            if (item.name === actionName) {
                this.actionId = item.id;
                this.leaveNote = true;
            }
        });
    }

    getApplicationInfo(id) {
        this.applicationService.getApplicationDetails(id).subscribe(
            resp => {
                this.isLoading = false;
                this.applicationData = resp.data;
                this.errorOnApplicationData = false;
            },
            err => {
                this.isLoading = false;
                this.errorOnApplicationData = true;
                if (err.errorCode === 'invalid') {
                    throw new Error(`Error: ${err.message}, HttpStatus: ${err.httpStatus}`);
                }
            }
        );
    }

    activateApplicationNameEdit(cachedCaption) {
        this.cachedCaption = cachedCaption;
        this.isApplicationNameEditView = true;
        this.focusApplicationNameInput();
    }

    closeApplicationNameEdit() {
        this.cachedCaption = '';
        this.formChanged = false;

        this.isApplicationNameEditView = false;
    }

    checkApplicationNameChange(value) {
        this.formChanged = value !== this.cachedCaption;
    }

    submitApplicationName({valid, value}) {
        if (valid && value.name !== this.cachedCaption) {
            this.updateApplicationName(value.name);
        }
    }

    updateApplicationName(newName) {
        let obj = {};
        obj['name'] = newName;

        this.applicationService.updateApplication(this.applicationData.id, obj).subscribe(
            resp => {
                this.applicationData.name = newName;
                this.closeApplicationNameEdit();
            },
            err => {
                alert(err.message);
            }
        );
    }

    cancel() {
        this.opened = false;
    }

    focusApplicationNameInput() {
        setTimeout(() => {
            // this.renderer.invokeElementMethod(this.applicationNameInput.nativeElement, 'focus');
        });
    }

    submitAction({value, valid}) {
        if (valid) {
            this.leaveNote = false;
            this.selectedAction = '';
            this.isSubmiting = true;
            let obj = {};
            obj['data'] = value;
            obj['id'] = this.applicationData.id;
            this.postActionChange(obj);
        }
    }

    postActionChange(obj) {
        this.applicationService.postAction(obj.id, obj.data)
            .subscribe(
                resp => {
                    this.isSubmiting = false;
                    this.isResponseStatusOk = 'ok';
                    setTimeout(() => {
                        this.isResponseStatusOk = 'null';
                    }, AppSettings.SUCCESS_DELAY);
                    this.router.navigate([this.isAllAppsPage ? '/applications' : '/']);
                },
                err => {
                    this.errorMessage = err && err.error.message ? err.error.message.substring(1, err.error.message.length-1).split(",") : '';
                    this.isSubmiting = false;
                    this.isResponseStatusOk = 'null';
                    this.isResponseStatusOk = 'error';
                }
            );
    }

    getStateColor(id) {
        return this.colorService.getColor(id);
    }
}

