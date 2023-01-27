import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { NglTreeComponent } from './tree';
import { NglTreeBranchComponent } from './tree-branch';
import { NglTreeHeadingDirective } from './tree-heading';
import { NglSubTreeComponent, NglSubTreeTriggerDirective } from './sub-tree';
import { NglInternalOutletModule } from '../util/outlet.module';

import { NglTreeRegistrationService } from './tree-registration.service';

const NGL_TREE_DIRECTIVES = [
  NglTreeComponent,
  NglTreeBranchComponent,
  NglTreeHeadingDirective,
  NglSubTreeComponent,
  NglSubTreeTriggerDirective,
];

@NgModule({
  declarations: [NGL_TREE_DIRECTIVES],
  exports: [NGL_TREE_DIRECTIVES],
  imports: [CommonModule, NglInternalOutletModule],
  providers: [NglTreeRegistrationService],
})
export class NglTreesModule {}
