import { ModuleWithProviders, NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

import { NglModule } from 'ng-lightning/ng-lightning';

import { Ng2UploaderModule } from '../sharedModules';

import { AllApplicationsComponent } from './all-applications.component';



import {
    SharedModule,
    ApplicationInfoGuard
} from '../shared';

const allApplicationsRouting: ModuleWithProviders = RouterModule.forChild([
    {
        path: 'applications',
        component: AllApplicationsComponent,
        canActivate: [ApplicationInfoGuard]
    }
]);

@NgModule({
    imports: [
        SharedModule,
        allApplicationsRouting,
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        Ng2UploaderModule,
        NglModule.forRoot({
            svgPath: '/assets/icons'
        }),
    ],
    declarations: [
        AllApplicationsComponent
    ],
    providers: [
        ApplicationInfoGuard
    ]
})
export class AllApplicationsModule {}
