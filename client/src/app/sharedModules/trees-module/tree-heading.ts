import { Directive, HostBinding } from '@angular/core';
/* tslint:disable */
@Directive({
  selector: '[nglTreeHeading]'
})
export class NglTreeHeadingDirective {
  @HostBinding('tabindex') tabindex = '-1';
  @HostBinding('attr.role') role = 'presentation';
  @HostBinding('id') id: string = '';
}
/* tslint:enable */
