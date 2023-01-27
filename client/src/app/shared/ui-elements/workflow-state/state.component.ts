import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

import { AppSettings } from '../../../app.settings';

@Component({
    selector: 'cbs-state-field',
    templateUrl: 'state.component.html',
    styleUrls: ['./state.component.scss']
})
export class WorkflowStateFieldComponent implements OnInit {
    @Input() public stateData: any;
    @Input() public alreadyGivenPermissions?: Object[] = [];
    @Input() public users: Object[] = [];
    @Input() public roles: Object[] = [];
    @Output() public permissionsInitialized = new EventEmitter();
    @Output() public saveNewPermissions = new EventEmitter();
    @Output() public permissionsChanged = new EventEmitter();

    public permissionsOpened = false;
    public isUsersTabActive = true;
    public activePermissions: string[] = [];
    public selectedItems: Object[] = [];
    public cachedItems: Object[] = [];

    public permissionEditAppPermissions: string = AppSettings.PERMISSION_NAMES['EDIT_APPLICATION_PERMISSIONS'];

    constructor() { }

    ngOnInit() {
        this.getDefinedPermissions();
        this.getLabelForDefinedPermissions();
        this.permissionsInitialized.emit([this.selectedItems, this.stateData.id]);
    }

    informParentOnPermissions() {
        this.saveNewPermissions.emit([this.selectedItems, this.stateData.id]);
    }


    getDefinedPermissions() {
        if (this.alreadyGivenPermissions && this.alreadyGivenPermissions.length) {
            this.alreadyGivenPermissions.map(permissionObj => {
                if (permissionObj['id'] === this.stateData.id) {
                    if (permissionObj['permissions'].length) {
                        this.makeSelectedItem(permissionObj['permissions']);
                    }
                }
            });
        } else if (this.stateData.permissions && this.stateData.permissions.length) {
            this.makeSelectedItem(this.stateData.permissions);
        }
    }

    makeSelectedItem(permissions) {
        permissions.map(item => {
            if (item.roleId) {
                this.roles.map(role => {
                    if (role['id'] === item.roleId) {
                        this.selectedItems.push(role);
                        this.cachedItems.push(role);
                    }
                });
            } else if (item.userId) {
                this.users.map(user => {
                    if (user['id'] === item.userId) {
                        this.selectedItems.push(user);
                        this.cachedItems.push(user);
                    }
                });
            }
        });
    }

    getLabelForDefinedPermissions() {
        let usersExist = false, rolesExist = false;
        // Check for number of roles and users
        this.selectedItems.map(item => {
            if (item.hasOwnProperty('code')) {
                rolesExist = true;
            }
            if (item.hasOwnProperty('username')) {
                usersExist = true;
            }
        });
        // Clean active permissions
        this.activePermissions.splice(0);
        // Define new permissions
        if (usersExist && rolesExist) {
            this.activePermissions.push('Several users and roles are granted permission');
        } else if (usersExist) {
            if (this.selectedItems.length > 2) {
                this.activePermissions.push('Several users are granted permission');
            } else {
                this.selectedItems.map(item => {
                    this.activePermissions.push(`${item['firstName']} ${item['lastName']}`);
                });
            }
        } else if (rolesExist) {
            if (this.selectedItems.length > 2) {
                this.activePermissions.push('Several roles are granted permission');
            } else {
                this.selectedItems.map(item => {
                    this.activePermissions.push(item['name']);
                });
            }
        } else {
            this.activePermissions.push('No permissions defined');
        }
    }

    activateUsers(e) {
        this.isUsersTabActive = true;
        e.preventDefault();
        return false;
    }

    activateRoles(e) {
        this.isUsersTabActive = false;
        e.preventDefault();
        return false;
    }

    updatePermissions(permissions) {
        this.permissionsOpened = false;
        this.selectedItems.splice(0);
        permissions.map(item => this.selectedItems.push(item));
        this.getLabelForDefinedPermissions();
        this.informParentOnPermissions();
    }
}

