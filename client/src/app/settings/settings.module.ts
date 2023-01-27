import { ModuleWithProviders, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

import { SettingsComponent } from './settings.component';
import { UsersComponent } from './users/users.component';
import { RolesComponent } from './roles/roles.component';
import { WorkflowsComponent } from './workflows/workflows.component';
import { SettingsHomeComponent } from './settings-home/settings-home.component';

import { NglTreesModule } from '../sharedModules';
import { NglModule } from 'ng-lightning/ng-lightning';

import {
    SharedModule,
    AuthGuard,
    UsersGuard,
    RolesGuard
} from '../shared';

const settingsRouting: ModuleWithProviders = RouterModule.forChild([
    {
        path: '',
        redirectTo: 'settings-home',
        pathMatch: 'full'
    },
    {
        path: 'settings',
        component: SettingsComponent,
        canActivate: [AuthGuard],
        children: [
            {
                path: '',
                component: SettingsHomeComponent
            },
            {
                path: 'users',
                component: UsersComponent,
                canActivate: [UsersGuard]
            }, {
                path: 'roles',
                component: RolesComponent,
                canActivate: [RolesGuard]
            }, {
                path: 'workflows',
                component: WorkflowsComponent
            }]
}]);

@NgModule({
    imports: [
        SharedModule,
        NglTreesModule,
        settingsRouting,
        CommonModule,
        NglModule.forRoot({
            svgPath: '/assets/icons'
        })
    ],
    declarations: [
        SettingsComponent,
        UsersComponent,
        RolesComponent,
        WorkflowsComponent,
        SettingsHomeComponent
    ],
    providers: []
})
export class SettingsModule {}
