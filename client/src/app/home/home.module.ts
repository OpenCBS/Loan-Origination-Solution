import { ModuleWithProviders, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

import { NglModule } from 'ng-lightning/ng-lightning';
import { HomeComponent } from './home.component';

import { SharedModule, AuthGuard } from '../shared';

const homeRouting: ModuleWithProviders = RouterModule.forChild([{
    path: '',
    redirectTo: 'tasks',
    pathMatch: 'full'
}, {
    path: 'tasks',
    component: HomeComponent,
    canActivate: [AuthGuard]
}]);

@NgModule({
    imports: [
        SharedModule,
        homeRouting,
        CommonModule,
        NglModule.forRoot({
            svgPath: '/assets/icons'
        }),
    ],
    declarations: [
        HomeComponent
    ],
    providers: [
        AuthGuard
    ]
})
export class HomeModule {}
