import { ModuleWithProviders, NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

import { NglModule } from 'ng-lightning/ng-lightning';

import { Ng2UploaderModule } from '../sharedModules';

import { ApplicationDetailsComponent } from './application.component';

import { ApplicationInfoComponent } from './info/info.component';
import { ApplicationFilesComponent } from './files/files.component';
import { ApplicationLogsComponent } from './logs/logs.component';
import { ApplicationFieldsComponent } from './fields/fields.component';
import { ApplicationActivityComponent } from './activity/activity.component';
import { ApplicationPermissionsComponent } from './permissions/permissions.component';
import { ApplicationEventsComponent } from './events/application-events.component';

import {
    SharedModule,
    AuthGuard,
    ApplicationInfoGuard,
    ApplicationFilesGuard,
    ApplicationWorklogsGuard,
    ApplicationFieldsGuard,
    ApplicationStatePermissionsGuard
} from '../shared';

const applicationRouting: ModuleWithProviders = RouterModule.forChild([
    {
        path: 'tasks/:id',
        component: ApplicationDetailsComponent,
        canActivate: [AuthGuard],
        children: [
            {
                path: '',
                redirectTo: 'info',
                pathMatch: 'full'
            },
            {
                path: 'info',
                component: ApplicationInfoComponent
            },
            {
                path: 'files',
                component: ApplicationFilesComponent,
                canActivate: [ApplicationFilesGuard]
            },
            {
                path: 'fields',
                component: ApplicationFieldsComponent,
            },
            {
                path: 'logs',
                component: ApplicationLogsComponent,
                canActivate: [ApplicationWorklogsGuard]
            },
            {
                path: 'activity',
                component: ApplicationActivityComponent
            },
            {
                path: 'events',
                component: ApplicationEventsComponent
            }
        ]
    },
    {
        path: 'applications/:id',
        component: ApplicationDetailsComponent,
        canActivateChild: [ApplicationInfoGuard],
        children: [
            {
                path: '',
                redirectTo: 'info',
                pathMatch: 'full'
            },
            {
                path: 'info',
                component: ApplicationInfoComponent
            },
            {
                path: 'files',
                component: ApplicationFilesComponent,
                canActivate: [ApplicationFilesGuard]
            },
            {
                path: 'fields',
                component: ApplicationFieldsComponent,
                canActivate: [ApplicationFieldsGuard]
            },
            {
                path: 'logs',
                component: ApplicationLogsComponent,
                canActivate: [ApplicationWorklogsGuard]
            },
            {
                path: 'activity',
                component: ApplicationActivityComponent
            },
            {
                path: 'permissions',
                component: ApplicationPermissionsComponent,
                canActivate: [ApplicationStatePermissionsGuard]
            },
            {
                path: 'events',
                component: ApplicationEventsComponent
            }
        ]
    }
]);

@NgModule({
    imports: [
        SharedModule,
        applicationRouting,
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        Ng2UploaderModule,
        NglModule.forRoot({
            svgPath: '/assets/icons'
        }),
    ],
    declarations: [
        ApplicationDetailsComponent,
        ApplicationInfoComponent,
        ApplicationFilesComponent,
        ApplicationLogsComponent,
        ApplicationFieldsComponent,
        ApplicationActivityComponent,
        ApplicationPermissionsComponent,
        ApplicationEventsComponent
    ],
    providers: [
        AuthGuard,
        ApplicationInfoGuard,
        ApplicationFilesGuard,
        ApplicationWorklogsGuard,
        ApplicationFieldsGuard,
        ApplicationStatePermissionsGuard
    ]
})
export class ApplicationModule {}
