import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ApplicationService, WorkflowService, UserService, RolesService, AppPermissionsService } from '../../shared';
import { AppSettings } from '../../app.settings';
import { Role, User } from '../../shared/models';

@Component({
    selector: 'cbs-application-permissions',
    templateUrl: 'permissions.component.html'
})
export class ApplicationPermissionsComponent implements OnInit {
    public currentWorkflowStates: Object[] = [];
    public applicationData: Object[];
    public isLoading = false;
    public response = '';
    public noData = true;
    public alreadyGivenPermissions: Object[] = [];
    public isSaveEnabled = false;
    public formatedPermissions: Object[];
    public users: Object[];
    public roles: Object[];
    public hasPermission = false;
    public userPermissions: any;
    public currentUser: User;
    public isDeleteApplication = false;
    public opened = false;
    public permissionEditAppPermissions: string = AppSettings.PERMISSION_NAMES['EDIT_APPLICATION_PERMISSIONS'];
    public isPermissionsEditable = false;
    public comment: any;
    public disableButtonYes: boolean;

    private statePermissions: any = [];
    private sub: any;
    private id: number;

    constructor(private route: ActivatedRoute,
                private router: Router,
                private applicationService: ApplicationService,
                private workflowService: WorkflowService,
                private rolesService: RolesService,
                private userService: UserService,
                private toastrService: ToastrService,
                private permissionsService: AppPermissionsService) {
    }

    ngOnInit() {

        this.userService.getAllUsers().subscribe(
            resp => {
                this.users = resp;
            },
            err => console.log(err)
        );
        this.rolesService.getRoles().subscribe(
            resp => {
                this.roles = resp.content;
            },
            err => console.log(err)
        );

        this.sub = this.route.parent.params.subscribe(params => {
            this.id = +params['id'];
            this.applicationService.getApplicationStatePermissions(this.id).subscribe(
                resp => {
                    this.alreadyGivenPermissions = resp.data;
                    this.getApplicationInfo(this.id);
                },
                err => {
                    console.log(err);
                }
            );
        });

        if (this.permissionsService.requirePermission(this.permissionEditAppPermissions)) {
            this.isPermissionsEditable = true;
        }

        this.currentUser = this.userService.getCurrentUser();
        this.userPermissions = this.currentUser['role']['permissions'];
        if (this.userPermissions.includes('ALL') || this.userPermissions.includes('DELETE_APPLICATION')) {
            this.hasPermission = true;
        }
    }

    getApplicationInfo(id) {
        this.isLoading = true;
        this.applicationService.getApplicationDetails(id).subscribe(
            resp => {
                this.applicationData = resp.data;
                this.isLoading = false;
                this.noData = false;
                if (this.applicationData['workflowInfo']) {
                    this.getStates(this.applicationData['workflowInfo']['id']);
                }
            },
            err => {
                this.isLoading = false;
                this.noData = true;
                console.log(err);
            }
        );

    }

    addPermissionsToSendData(objArray) {
        let arr = [];
        objArray.map(item => {
            let tempObj = {};
            if (item.length) {
                tempObj = {
                    id: objArray.indexOf(item),
                    permissions: []
                };
                item.map(elem => {
                    if (elem.hasOwnProperty('code')) {
                        tempObj['permissions'].push({roleId: elem.id});
                    } else if (elem.hasOwnProperty('username')) {
                        tempObj['permissions'].push({userId: elem.id});
                    }
                });
            }
            if (tempObj.hasOwnProperty('id')) {
                arr.push(tempObj);
            }
        });
        return arr;
    }

    savePermissions() {
        this.isLoading = true;
        this.applicationService.postApplicationStatePermissions(this.id, this.formatedPermissions).subscribe(
            resp => {
                this.alreadyGivenPermissions = resp.data;
                this.isLoading = false;
                this.isSaveEnabled = false;
                this.response = 'success';
                setTimeout(() => {
                    this.response = '';
                }, 1500);
            },
            err => {
                this.isLoading = false;
                this.response = 'error';
                setTimeout(() => {
                    this.response = '';
                }, 1500);
                throw new Error(err.message);
            }
        );
    }

    deleteApplication() {
        const comment = {comment: this.comment};
        this.applicationService
            .deleteApplication(this.applicationData['id'], comment)
            .subscribe(
                data => {
                    this.isDeleteApplication = true;
                    this.router.navigate(['/applications']);
                    this.toastrService.success(
                        'You have successfully deleted the application', '', {timeOut: 2000, positionClass: 'toast-top-full-width'});
                },
                err => {
                    console.log(err);
                    this.opened = false;
                    this.toastrService.error(
                        'Oops! Something went wrong, please try again.', '', {timeOut: 2000, positionClass: 'toast-top-full-width'});
                }
            );
    }

    openDeleteModal() {
        this.opened = true;
        this.disableButtonYes = true;
    }

    checkCommentField() {
        this.disableButtonYes = this.comment === '';
    }

    cancel() {
        this.opened = false;
    }

    hasChanges(newPerm, oldPerm): boolean {
        if ((newPerm == null && oldPerm != null) || (newPerm != null && oldPerm == null) || newPerm.length !== oldPerm.length) {
            return true;
        }

        for (let newPermIndex = 0; newPermIndex < newPerm.length; newPermIndex++) {
            let currentNewPerm = newPerm[newPermIndex];

            let filteredOldPermissions = oldPerm.filter(perm => perm.id === currentNewPerm.id);
            if (filteredOldPermissions == null || filteredOldPermissions.length === 0) {
                return true;
            }

            let currentOldPerm = filteredOldPermissions[0];

            let newPermissions = currentNewPerm.permissions;
            let oldPermissions = currentOldPerm.permissions;

            if ((newPermissions == null && oldPermissions != null) || (newPermissions != null && oldPermissions == null) || newPermissions.length !== oldPermissions.length) {
                return true;
            }

            for (let currentPermissionIndex = 0; currentPermissionIndex < newPermissions.length; currentPermissionIndex++) {
                let currentNewPerm = newPermissions[currentPermissionIndex];

                let isRole = currentNewPerm.roleId != null;
                let filteredData = oldPermissions.filter(perm => (isRole && perm.roleId != null && perm.roleId === currentNewPerm.roleId)
                    || (!isRole && perm.userId != null && perm.userId === currentNewPerm.userId));

                if (filteredData == null || filteredData.length === 0) {
                    return true;
                }
            }
        }

        return false;
    }

    getStates(id) {
        this.workflowService.getWorkflowStates(id).subscribe(
            resp => {
                this.currentWorkflowStates = resp;
            },
            err => console.log(err)
        );
    }

    handlePermissions(item, shouldCheck: boolean) {
        this.statePermissions[item[1]] = item[0];
        this.formatedPermissions = this.addPermissionsToSendData(this.statePermissions);

        if (shouldCheck) {
            let hasChangesCheck = this.hasChanges(this.formatedPermissions, this.alreadyGivenPermissions);

            this.isSaveEnabled = hasChangesCheck;
        }
    }
}
