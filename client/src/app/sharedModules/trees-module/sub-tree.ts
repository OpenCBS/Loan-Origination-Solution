import {
  Component, Directive, Input, ContentChild, ContentChildren, QueryList,
  HostListener, EventEmitter, ElementRef, AfterContentInit, OnDestroy,
    Renderer2, DoCheck, HostBinding
} from '@angular/core';
import { NglTreeRegistrationService } from './tree-registration.service';
import { NglTreeHeadingDirective } from './tree-heading';
import { NglTreeBranchComponent } from './tree-branch';
import { toBoolean, uniqueId } from '../util/util';

/* START -- NglSubTree */
/* tslint:disable */
@Component({
  selector: '[nglSubTree]',
  templateUrl: './sub-tree.html'
})
export class NglSubTreeComponent implements AfterContentInit, OnDestroy {
  @Input('expanded') set setIsExpanded(isExpanded: any) {
    this.isExpanded = toBoolean(isExpanded);
  }
  @Input('disabled') set isDisabled(isDisabled: any) {
    this._isDisabled = toBoolean(isDisabled);
  }
  get isDisabled() {
    return this._isDisabled;
  }

  uniqueId = uniqueId();

  @HostBinding('attr.role') role = 'treeitem';
  @HostBinding('tabindex') tabindex = '-1';
  @HostBinding('attr.aria-level') private level = 0;
  @HostBinding('attr.aria-expanded') private isExpanded = false;
  @HostBinding('id') id = `ngl-sub-tree-${this.uniqueId}`;

  @ContentChild(NglTreeHeadingDirective, {static: false}) myHeading: NglTreeHeadingDirective;
  @ContentChildren(NglTreeBranchComponent) myBranches = new QueryList<NglTreeBranchComponent>();
  @ContentChildren(NglSubTreeComponent) mySubTrees = new QueryList<NglSubTreeComponent>();

  focusEvent = new EventEmitter(false);
  actionEvent = new EventEmitter<'collapse' | 'expand'>(false);


  private _isDisabled = false;
  private _isTriggerFocused = false;

  constructor(private elementRef: ElementRef, private registrationService: NglTreeRegistrationService) {
    this.registrationService.register(this);
  }

  ngAfterContentInit() {
    if (this.myHeading) {
      setTimeout(() => this.myHeading.id = `ngl-sub-tree-heading-${this.uniqueId}`);
    }
  }

  ngOnDestroy() {
    this.registrationService.unregister(this);
  }

  @HostListener('click', ['$event', '"click"'])
  @HostListener('focus', ['$event', '"focus"'])
  @HostListener('blur', ['$event', '"blur"'])
  handleFocus($event: Event, eventName: 'click' | 'focus' | 'blur') {
    if (eventName === 'click') {
      $event.preventDefault();
      $event.stopPropagation();
    }
    if (eventName !== 'blur') {
      this.focus();
    }
  }

  @HostListener('keydown.arrowleft', ['$event', '"collapse"'])
  @HostListener('keydown.arrowright', ['$event', '"expand"'])
  onKeydown($event: Event, action: 'collapse' | 'expand') {
    $event.preventDefault();
    if (action === 'expand' || this.isExpanded) {
      $event.stopPropagation();
      this.actionEvent.emit(action);
    }
  }

  setLevel(level: number) {
    this.level = level;
    this.myBranches.forEach(myBranch => myBranch.setLevel(this.level + 1));
    this.mySubTrees.filter(mySubTree => mySubTree !== this).forEach(mySubTree => mySubTree.setLevel(this.level + 1));
  }

  toggleExpanded(isExpanded = !this.isExpanded) {
    this.isExpanded = isExpanded;
  }

  focus() {
    if (this.isDisabled) {
      return false;
    }
    this.focusEvent.emit(null);
    return true;
  }

  isFocused() {
    return this._isTriggerFocused;
  }

  setTriggerFocus(isTriggerFocused: boolean) {
    this._isTriggerFocused = isTriggerFocused;
  }
}

/* END -- NglSubTree */

/* START -- NglSubTreeTrigger */

@Directive({
  selector: '[nglSubTreeTrigger]'
})
export class NglSubTreeTriggerDirective implements OnDestroy, DoCheck {
  private isFocused = false;
  private focusSubscription: any;
  private actionSubscription: any;

  @HostBinding('tabindex') tabindex = '0';

  @HostListener('focus', ['true'])
  @HostListener('blur', ['false'])
  handleFocus(isFocused: boolean) {
    this.isFocused = isFocused;
    this.subTree.setTriggerFocus(this.isFocused);
  }

  @HostListener('click', ['$event'])
  toggle($event: Event) {
    $event.preventDefault();
    $event.stopPropagation();
    this.subTree.toggleExpanded();
  }

  constructor(private elementRef: ElementRef, private renderer: Renderer2, private subTree: NglSubTreeComponent) {
    this.focusSubscription = this.subTree.focusEvent.subscribe(() => this.focus());
    this.actionSubscription = this.subTree.actionEvent.subscribe(this.onActionEvent.bind(this));
  }

  ngDoCheck() {
    if (this.subTree.isDisabled) {
      this.renderer.setAttribute(this.elementRef.nativeElement, 'disabled', 'disabled');
    } else {
      // this.renderer.invokeElementMethod(this.elementRef.nativeElement, 'removeAttribute', ['disabled']);
    }
  }

  ngOnDestroy() {
    this.focusSubscription.unsubscribe();
    this.actionSubscription.unsubscribe();
  }

  onActionEvent(action: 'collapse' | 'expand') {
    switch (action) {
      case 'collapse':
        this.isFocused ? this.subTree.toggleExpanded(false) : this.focus();
        break;
      case 'expand':
        if (this.isFocused) {
          this.subTree.toggleExpanded(true);
        }
        break;
    }
  }

  private focus() {
    this.elementRef.nativeElement.focus();
  }
}
/* tslint:enable */
/* END -- NglSubTreeTrigger */
