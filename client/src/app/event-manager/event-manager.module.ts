/**
 * Created by Chyngyz on 4/19/2017.
 */
import { ModuleWithProviders, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NglModule } from 'ng-lightning/ng-lightning';

import { ScheduleModule } from '../sharedModules/schedule/schedule.module';

import { EventManagerComponent } from './event-manager.component';


import { SharedModule, AuthGuard } from '../shared';

const eventManagerRouting: ModuleWithProviders = RouterModule.forChild([{
    path: 'event-manager',
    component: EventManagerComponent,
    canActivate: [AuthGuard]
}]);

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        SharedModule,
        eventManagerRouting,
        ScheduleModule,
        NglModule.forRoot({
            svgPath: '/assets/icons'
        })
    ],
    declarations: [
        EventManagerComponent
    ],
    providers: [
        AuthGuard
    ]
})
export class EventManagerModule {}
