import { ModuleWithProviders, NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { NglModule } from 'ng-lightning/ng-lightning';
import { Ng2UploaderModule } from '../sharedModules';
import { UserSettingsComponent } from './userSettings.component';
import { PasswordComponent } from './password/password.component';
import { UserDetailsComponent } from './info/info.component';
import { UserPhotoModalComponent } from './user-photo/user-photo-modal.component';

import {
    SharedModule,
    AuthGuard
} from '../shared';

const userSettingsRouting: ModuleWithProviders = RouterModule.forChild([
    {
        path: 'user-settings',
        component: UserSettingsComponent,
        canActivateChild: [AuthGuard],
        children: [
            {
                path: '',
                redirectTo: 'info',
                pathMatch: 'full'
            },
            {
                path: 'info',
                component: UserDetailsComponent
            },
            {
                path: 'password',
                component: PasswordComponent
            }
        ]
    }
]);

@NgModule({
    imports: [
        SharedModule,
        userSettingsRouting,
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        Ng2UploaderModule,
        NglModule.forRoot({
            svgPath: '/assets/icons'
        }),
    ],
    declarations: [
        UserSettingsComponent,
        UserDetailsComponent,
        PasswordComponent,
        UserPhotoModalComponent
    ],
    providers: [
        AuthGuard
    ]
})
export class UserSettingsModule {
}
