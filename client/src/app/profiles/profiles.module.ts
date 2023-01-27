import { ModuleWithProviders, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ProfilesComponent } from './profiles.component';

import { NglModule } from 'ng-lightning/ng-lightning';

import { SharedModule, ProfilesGuard } from '../shared';
import { FileUploadComponent } from '../shared/modules/cbs-file-upload/file-upload';

const profilesRouting: ModuleWithProviders = RouterModule.forChild([{
    path: 'profiles',
    component: ProfilesComponent,
    canActivate: [ProfilesGuard]
}]);

@NgModule({
    imports: [
        SharedModule,
        profilesRouting,
        CommonModule,
        NglModule.forRoot({
            svgPath: '/assets/icons'
        })
    ],
    declarations: [
        ProfilesComponent
    ],
    providers: [
        ProfilesGuard,
        FileUploadComponent
    ]
})
export class ProfilesModule {}
