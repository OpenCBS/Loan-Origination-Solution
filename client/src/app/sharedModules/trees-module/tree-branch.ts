import { Component, ElementRef, HostListener, HostBinding, OnDestroy } from '@angular/core';
import { NglTreeRegistrationService } from './tree-registration.service';
/* tslint:disable */
@Component({
  selector: '[nglTreeBranch]',
  templateUrl: './tree-branch.html',
})
export class NglTreeBranchComponent implements OnDestroy {
  private _isFocused = false;

  @HostBinding('attr.role') role = 'treeitem';
  @HostBinding('tabindex') tabindex = '0';
  @HostBinding('attr.aria-level') private level = 0;

  @HostListener('click', ['$event'])
  onClick($event: Event) {
    $event.preventDefault();
    $event.stopPropagation();
    this.focus();
  }

  @HostListener('focus', ['true'])
  @HostListener('blur', ['false'])
  handleFocus(isFocused: boolean) {
    this._isFocused = isFocused;
  }

  constructor(private elementRef: ElementRef, private registrationService: NglTreeRegistrationService) {
    this.registrationService.register(this);
  }

  ngOnDestroy() {
    this.registrationService.unregister(this);
  }

  setLevel(level: number) {
    this.level = level;
  }

  focus() {
    this.elementRef.nativeElement.focus();
    return true;
  }

  isFocused() {
    return this._isFocused;
  }
}
/* tslint:enable */
