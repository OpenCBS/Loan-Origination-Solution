import { Directive, ElementRef, ViewContainerRef, TemplateRef, Input } from '@angular/core';

import { AppPermissionsService } from '../services';

@Directive({
    selector: '[cbsVisibleByPermission]'
})
export class VisibleByPermissionDirective {
    @Input() set cbsVisibleByPermission(permission: string[] | string) {
        if (this.permissions.requirePermission(permission)) {
            this.view.createEmbeddedView(this.template);
        } else {
            this.view.clear();
        }
    }
    constructor(
        el: ElementRef,
        private view: ViewContainerRef,
        private template: TemplateRef < any >,
        private permissions: AppPermissionsService) { }
}
