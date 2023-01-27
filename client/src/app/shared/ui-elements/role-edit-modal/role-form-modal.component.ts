import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Role } from '../../models';
import { AppSettings } from '../../../app.settings';

import { RolesService } from '../../services';

@Component({
    selector: 'cbs-role-modal',
    templateUrl: 'role-form-modal.component.html',
    styleUrls: ['./role-form-modal.component.scss']
})
export class RoleFormModalComponent implements OnInit {
    public role: FormGroup;
    public roleData: Role = new Role();
    public opened = false;
    public isNewRole = false;
    public isSubmitting = false;
    public hasFormChanges = false;

    public allPermissions: Object;
    public appPermissions: Object[];
    public profilePermissions: Object[];
    public userPermissions: Object[];
    public rolePermissions: Object[];
    public alertPermissions: Object[];

    // initial modal response state
    public isResponseStatusOk = 'null';

    private roleId: number;
    private permissionsData: Object[] = [];
    private permissions: string[] = [];

    @Output() saveRoleData = new EventEmitter();

    constructor(
        private rolesService: RolesService) { }

    ngOnInit() {
        this.rolesService.roleModalChanged$.subscribe(resp => {
            this.isSubmitting = false;
            if (resp.status === 'success') {
                this.isResponseStatusOk = 'ok';
                this.clearAll();
            } else if (resp.status === 'error') {
                this.isResponseStatusOk = 'error';
                setTimeout(() => {
                    this.isResponseStatusOk = 'null';
                }, AppSettings.ERROR_DELAY);
            }
        });

        this.createForm(new Role());
        this.getPermissions();
    }

    getPermissions() {
        this.rolesService.getPermissions().subscribe(
            resp => {
                this.permissionsData = resp;
                this.resetPermissions();
                this.splitIntoParts();
            },
            err => {
                console.log(err);
            }
        );
    }

    resetPermissions() {
        this.permissionsData.map(item => {
            item['checked'] = false;
            item['disabled'] = false;
        });
        this.permissions.splice(0);
    }

    defineCheckedPermissions() {
        this.permissionsData.map(item => {
            let allPriveleges = false;
            if (this.permissions.length) {
                this.permissions.map(permission => {
                    if (permission === 'ALL' || allPriveleges) {
                        allPriveleges = true;
                        item['checked'] = true;
                    } else if (permission === item['code']) {
                        item['checked'] = true;
                    }
                    this.checkDependencies(permission);
                });
            } else {
                item['checked'] = false;
                item['disabled'] = false;
            }
        });
        this.splitIntoParts();
    }

    asignPermissions(rolePermissions) {
        if (rolePermissions.some(item => item === AppSettings.PERMISSION_NAMES['ALL'])) {
            for (let key in AppSettings.PERMISSION_NAMES) {
                if (AppSettings.PERMISSION_NAMES.hasOwnProperty(key)) {
                    let element = AppSettings.PERMISSION_NAMES[key];
                    if (element !== AppSettings.PERMISSION_NAMES['ALL']) {
                        this.permissions.push(element);
                    }
                }
            }
        } else {
            rolePermissions.map(item => {
                this.permissions.push(item);
            });
        }
    }

    splitIntoParts() {
        this.allPermissions = this.permissionsData[0];
        this.profilePermissions = this.permissionsData.slice(1, 5);
        this.appPermissions = this.permissionsData.slice(5, 17);
        this.userPermissions = this.permissionsData.slice(17, 21);
        this.rolePermissions = this.permissionsData.slice(21, 24);
        this.alertPermissions = this.permissionsData.slice(24, 25);
    }

    createForm(role: Role) {
        this.role = new FormGroup({
            code: new FormControl( role.code ? role.code : '', [Validators.required, Validators.minLength(3)]),
            name: new FormControl( role.name ? role.name : '', [Validators.required, Validators.minLength(3)])
        });
    }

    openModal(role: Role, bool: boolean) {
        if (!bool) {
            this.createForm(role);
            this.resetPermissions();
            this.asignPermissions(role.permissions);
            this.defineCheckedPermissions();
        } else {
            this.createForm(new Role());
            this.resetPermissions();
        }
        this.opened = true;
        this.roleData = role;
        this.roleId = role.id;
        this.isNewRole = bool;
    }

    clearAll() {
        setTimeout(() => {
            this.isResponseStatusOk = 'null';
            this.opened = false;
        }, AppSettings.SUCCESS_DELAY);
        this.roleData = new Role();
        this.role.reset();
    }

    cancel() {
        this.opened = false;
    }

    onPermissionChange(permission) {
        if (permission.code === AppSettings.PERMISSION_NAMES['ALL'] && permission.checked) {
            this.permissions.splice(0);
            this.asignPermissions([permission.code]);
            this.defineCheckedPermissions();
        } else if (permission.code === AppSettings.PERMISSION_NAMES['ALL'] && !permission.checked) {
            this.permissions.splice(0);
            this.resetPermissions();
            this.defineCheckedPermissions();
        } else if (permission.checked) {
            this.permissions.push(permission.code);
            this.defineCheckedPermissions();
        } else if (!permission.checked) {
            if (this.permissions.some(item => item === AppSettings.PERMISSION_NAMES['ALL'])) {
                this.asignPermissions(this.permissions);
                this.permissions.splice(this.permissions.indexOf(AppSettings.PERMISSION_NAMES['ALL']), 1);
            }
            this.permissions.splice(this.permissions.indexOf(permission.code), 1);
            this.uncheckDependencies(permission.code);
        }
        // console.log(this.permissions);
    }

    checkDependencies(code) {
        if (code === AppSettings.PERMISSION_NAMES['READ_APPLICATION']) {
            this.preCheckFormat([AppSettings.PERMISSION_NAMES['READ_USER']], true );
        } else if (code === AppSettings.PERMISSION_NAMES['CREATE_APPLICATION']) {
            this.preCheckFormat([
                AppSettings.PERMISSION_NAMES['READ_PROFILE'],
                AppSettings.PERMISSION_NAMES['READ_ROLE'],
                AppSettings.PERMISSION_NAMES['READ_USER'],
                AppSettings.PERMISSION_NAMES['EDIT_APPLICATION_PERMISSIONS']
                ], true );
        } else if (code === AppSettings.PERMISSION_NAMES['READ_APPLICATION_PERMISSIONS']) {
            this.preCheckFormat([
                AppSettings.PERMISSION_NAMES['READ_ROLE'],
                AppSettings.PERMISSION_NAMES['READ_USER']
                ], true );
        }
    }

    uncheckDependencies(code) {
        let hasReadAppPermission = this.permissions.indexOf(AppSettings.PERMISSION_NAMES['READ_APPLICATION']) !== -1;
        let hasCreateAppPermission = this.permissions.indexOf(AppSettings.PERMISSION_NAMES['CREATE_APPLICATION']) !== -1;
        let hasReadAppPermPermission = this.permissions.indexOf(AppSettings.PERMISSION_NAMES['READ_APPLICATION_PERMISSIONS']) !== -1;

        if (code === AppSettings.PERMISSION_NAMES['READ_APPLICATION']) {
            if (!hasCreateAppPermission && !hasReadAppPermPermission) {
                this.preCheckFormat([AppSettings.PERMISSION_NAMES['READ_USER']], false );
            }
        } else if (code === AppSettings.PERMISSION_NAMES['CREATE_APPLICATION']) {
            if (hasReadAppPermission && !hasReadAppPermPermission) {
                this.preCheckFormat([
                    AppSettings.PERMISSION_NAMES['READ_PROFILE'],
                    AppSettings.PERMISSION_NAMES['READ_ROLE'],
                    AppSettings.PERMISSION_NAMES['EDIT_APPLICATION_PERMISSIONS']
                    ], false );
            } else if (hasReadAppPermPermission) {
                this.preCheckFormat([
                    AppSettings.PERMISSION_NAMES['READ_PROFILE'],
                    AppSettings.PERMISSION_NAMES['EDIT_APPLICATION_PERMISSIONS']
                    ], false );
            } else if (!hasReadAppPermission) {
                this.preCheckFormat([
                    AppSettings.PERMISSION_NAMES['READ_PROFILE'],
                    AppSettings.PERMISSION_NAMES['READ_ROLE'],
                    AppSettings.PERMISSION_NAMES['READ_USER'],
                    AppSettings.PERMISSION_NAMES['EDIT_APPLICATION_PERMISSIONS']
                    ], false );
            }
        } else if (code === AppSettings.PERMISSION_NAMES['READ_APPLICATION_PERMISSIONS']) {
            if (hasReadAppPermission && !hasCreateAppPermission) {
                this.preCheckFormat([
                    AppSettings.PERMISSION_NAMES['READ_ROLE']
                    ], false );
            } else if (!hasCreateAppPermission) {
                this.preCheckFormat([
                    AppSettings.PERMISSION_NAMES['READ_ROLE'],
                    AppSettings.PERMISSION_NAMES['READ_USER']
                    ], false );
            }
        }
    }

    preCheckFormat(roles: string[], isOnAddition: boolean): void {
        roles.map(item => {
            if (this.permissions.indexOf(item) === -1 && isOnAddition) {
                this.permissions.push(item);
            } else if (this.permissions.indexOf(item) !== -1 && !isOnAddition) {
                this.permissions.splice(this.permissions.indexOf(item), 1);
            }

            this.permissionsData.map(permission => {
                if (permission['code'] === item && isOnAddition) {
                    permission['disabled'] = true;
                } else if ((permission['code'] === item && !isOnAddition)) {
                    permission['disabled'] = false;
                    permission['checked'] = false;
                }
            });
        });

    }

    submitForm({ value, valid }) {
        let obj: Object = {};
        let valueObj: Object = {};
        if (valid) {
            if (this.permissions.indexOf(AppSettings.PERMISSION_NAMES['ALL']) !== -1 ||
                this.permissions.length === Object.keys(AppSettings.PERMISSION_NAMES).length - 1) {
                this.permissions.splice(0);
                this.permissions.push(AppSettings.PERMISSION_NAMES['ALL']);
            }
            for (let key in value) {
                if (value.hasOwnProperty(key)) {
                    valueObj[key] = value[key];
                }
            }
            valueObj['permissions'] = this.permissions;
            obj['data'] = valueObj;
            obj['isNew'] = this.isNewRole;
            obj['id'] = this.roleId;
            // console.log(obj);
            this.saveRoleData.emit(obj);
            this.isSubmitting = true;
        }
    }
}
