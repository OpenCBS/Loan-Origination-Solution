import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';

@Component({
    selector: 'cbs-permission-item',
    template: `
        <a href="javascript:void(0);" tabindex="-1" [ngClass]="{'selected': isActive}" (click)="checkCurrent()">
            <span class="slds-truncate" *ngIf="permissionType === 'user'">{{permissionItem.firstName}} {{permissionItem.lastName}}</span>
            <span class="slds-truncate" *ngIf="permissionType === 'role'">{{permissionItem.name}}</span>
            <svg aria-hidden="true" class="slds-icon slds-icon--selected slds-icon--x-small slds-icon-text-default slds-m-right--x-small">
                <use xlink:href="/assets/icons/utility-sprite/svg/symbols.svg#check"></use>
            </svg>
        </a>
    `
})
export class PermissionItemComponent implements OnInit {
    @Input() permissionItem: any;
    @Input() activeDefinedPermissions: any;
    @Input() permissionType: any;
    @Output() togglePermission = new EventEmitter();

    public isActive = false;
    constructor() { }

    ngOnInit() {
        this.markAsActive();
    }

    markAsActive() {
        if (!this.activeDefinedPermissions.length) {
            this.isActive = false;
        }
        if (this.activeDefinedPermissions.indexOf(this.permissionItem) === -1) {
            this.isActive = false;
        } else {
            this.isActive = true;
        }
    }


    checkCurrent() {
        this.togglePermission.emit(this.permissionItem);
        this.markAsActive();
    }


}
