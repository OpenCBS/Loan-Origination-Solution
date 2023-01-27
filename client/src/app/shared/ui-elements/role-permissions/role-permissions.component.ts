import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

import { RolesService  } from '../../services';

@Component({
    selector: 'cbs-role-permissions',
    templateUrl: 'role-permissions.component.html',
    styles: [`
        :host {
            display: block;
            margin-top: 1rem;
        }
        :host ::ng-deep .slds-form-element + .slds-form-element {
            padding-left: 0;
        }

        :host ::ng-deep .cbs-role-permissions__select a {
            display: inline-block;
            margin-top: .25rem;
            margin-bottom: .25rem;
            text-decoration: none;
        }
    `]
})
export class RolePermissionsComponent implements OnInit {
    @Input() allPermissions: Object;
    @Input() appPermissions: Object[];
    @Input() profilePermissions: Object[];
    @Input() userPermissions: Object[];
    @Input() rolePermissions: Object[];
    @Input() alertPermissions: Object[];

    @Output() onPermissionChange = new EventEmitter();

    constructor(private rolesService: RolesService) { }

    ngOnInit() {

    }

    selectAll(e) {
        e.preventDefault();
        this.allPermissions['checked'] = true;
        this.onPermissionChange.emit(this.allPermissions);
    }

    clear(e) {
        e.preventDefault();
        this.allPermissions['checked'] = false;
        this.onPermissionChange.emit(this.allPermissions);
    }

    checkPermission(permission, type) {
        let selected = {};
        if (type === 'app') {
            selected = this.togglePermission(this.appPermissions, permission);
        } else if (type === 'profile') {
            selected = this.togglePermission(this.profilePermissions, permission);
        } else if (type === 'user') {
            selected = this.togglePermission(this.userPermissions, permission);
        } else if (type === 'role') {
            selected = this.togglePermission(this.rolePermissions, permission);
        } else if (type === 'alert') {
            selected = this.togglePermission(this.alertPermissions, permission);
        }

        this.onPermissionChange.emit(selected);
    }

    togglePermission(data, obj) {
        let selectedItem = {};
        data.map(item => {
            if (item['code'] === obj.code) {
                item['checked'] = !item['checked'];
                selectedItem = item;
            }
        });
        return selectedItem;
    }
}
