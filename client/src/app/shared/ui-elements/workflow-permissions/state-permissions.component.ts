import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
    selector: 'cbs-state-permissions',
    templateUrl: 'state-permissions.component.html',
    styleUrls: ['./state-permissions.component.scss']
})
export class StatePermissionsComponent implements OnInit {
    @Input() activeSelectedPermissions: any[] = [];
    @Input() roles: Object[] = [];
    @Input() users: Object[] = [];
    @Output() onClose = new EventEmitter();
    @Output() onSave = new EventEmitter();

    public isUsersTabActive = true;
    public selectedItems: Object[] = [];
    constructor() { }

    ngOnInit() {
        this.activeSelectedPermissions.map(item => this.selectedItems.push(item));
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

    checkItem(item: Object) {
        if (this.selectedItems.indexOf(item) !== -1) {
            this.selectedItems.splice(this.selectedItems.indexOf(item), 1);
        } else {
            this.selectedItems.push(item);
        }
    }

    cancelPermissions(e) {
        e.preventDefault();
        this.onClose.emit(false);
    }

    savePermissions(e) {
        e.preventDefault();
        this.onSave.emit(this.selectedItems);
    }
}