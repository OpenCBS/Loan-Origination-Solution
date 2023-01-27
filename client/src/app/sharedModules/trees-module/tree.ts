/* tslint:disable */
import { Component, ContentChild, ContentChildren, QueryList, AfterContentInit, OnDestroy, HostListener, HostBinding } from '@angular/core';
import { NglTreeRegistrationService } from './tree-registration.service';
import { NglTreeHeadingDirective } from './tree-heading';
import { NglTreeBranchComponent } from './tree-branch';
import { NglSubTreeComponent } from './sub-tree';
import { uniqueId } from '../util/util';


@Component({
  selector: 'cbs-tree',
  templateUrl: './tree.html',
  providers: [NglTreeRegistrationService],
})
export class NglTreeComponent implements AfterContentInit, OnDestroy {
  @ContentChild(NglTreeHeadingDirective, {static: false}) myHeading: NglTreeHeadingDirective;
  @ContentChildren(NglTreeBranchComponent) myBranches = new QueryList<NglTreeBranchComponent>();
  @ContentChildren(NglSubTreeComponent) mySubTrees = new QueryList<NglSubTreeComponent>();

  private children: any[] = [];
  private level = 1;
  private uniqueId = uniqueId();
  private registrationServiceSubscriptions: any[] = [];

  @HostBinding('tabindex') tabindex = '-1';

  @HostListener('keydown.arrowup', ['$event', '"previous"'])
  @HostListener('keydown.arrowdown', ['$event', '"next"'])
  handleFocusShift($event: Event, direction: 'previous' | 'next') {
    $event.preventDefault();
    let currentFocusIndex = this.children.findIndex(child => child.isFocused());
    if (currentFocusIndex === -1) {
      return;
    }
    const isDirectionNext = direction === 'next';

    let done = false;
    do {
      const nextFocusedIndex = Math.min(
        Math.max(currentFocusIndex + (isDirectionNext ? 1 : -1), 0),
        (this.children.length || 1) - 1
      );
      done = this.children[nextFocusedIndex].focus();
      if (!done) {
        currentFocusIndex += (isDirectionNext ? 1 : -1);
        if (currentFocusIndex === -1 || currentFocusIndex === this.children.length) {
          break;
        }
      }
    } while (!done);
  }

  constructor(private registrationService: NglTreeRegistrationService) {
    this.registrationServiceSubscriptions.push(
        this.registrationService.registerEvent.subscribe((child: any) => this.registerChild(child)),
        this.registrationService.unregisterEvent.subscribe((child: any) => this.unregisterChild(child))
    );
  }

  ngAfterContentInit() {
    setTimeout(() => {
      if (this.myHeading) {
        setTimeout(() => this.myHeading.id = `ngl-tree-heading-${this.uniqueId}`);
      }
      this.myBranches.forEach(myBranch => myBranch.setLevel(this.level));
      this.mySubTrees.forEach(mySubTree => mySubTree.setLevel(this.level));
    });
  }

  ngOnDestroy() {
    this.registrationServiceSubscriptions.forEach(subscription => subscription.unsubscribe());
  }

  private registerChild(child: any) {
    const existingChild = this.children.filter(_child => _child === child);
    if (!existingChild.length) {
      this.children.push(child);
    }
  }

  private unregisterChild(child: any) {
    this.children = this.children.filter(_child => _child !== child);
  }
}
/* tslint:enable */
