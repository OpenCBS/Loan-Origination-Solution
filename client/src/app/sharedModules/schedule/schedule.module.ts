/**
 * Created by Chyngyz on 4/19/2017.
 */
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ScheduleComponent } from './schedule.component';

@NgModule({
    imports: [CommonModule],
    exports: [ScheduleComponent],
    declarations: [ScheduleComponent]
})
export class ScheduleModule { }
