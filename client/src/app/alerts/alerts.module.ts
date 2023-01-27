import { ModuleWithProviders, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AlertsComponent } from './alerts.component';
import { SharedModule } from '../shared';
import { CommonModule } from '@angular/common';
import { NglModule } from 'ng-lightning';
import { AlertsGuard } from '../shared/services/guards';

const alertsRouting: ModuleWithProviders = RouterModule.forChild([{
    path: 'alerts',
    component: AlertsComponent,
    canActivate: [AlertsGuard]
}]);

@NgModule({
    imports: [
        SharedModule,
        alertsRouting,
        CommonModule,
        NglModule.forRoot({
            svgPath: '/assets/icons'
        })
    ],
    declarations: [
        AlertsComponent
    ],
    providers: [
        AlertsGuard
    ]
})
export class AlertsModule {}
