import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { NglInternalOutletComponent } from './outlet';

@NgModule({
  imports: [CommonModule],
  declarations: [NglInternalOutletComponent],
  exports: [NglInternalOutletComponent],
})
export class NglInternalOutletModule {}
