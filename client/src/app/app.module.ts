import {
    NgModule,
    ApplicationRef,
    ModuleWithProviders
} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { ToastrModule } from 'ngx-toastr';

import { AppComponent } from './app.component';

import { HomeModule } from './home/home.module';
import { AboutModule } from './about/about.module';
import { AuthModule } from './auth/auth.module';
import { ProfilesModule } from './profiles/profiles.module';
import { SettingsModule } from './settings/settings.module';
import { ApplicationModule } from './application/application.module';
import { UserSettingsModule } from './userSettings/userSettings.module';
import { AllApplicationsModule } from './all-applications/all-applications.module';
import { ErrorsModule } from './errors/errors.module';
import { EventManagerModule } from './event-manager/event-manager.module';


import {
    removeNgStyles,
    createNewHosts
} from '@angularclass/hmr';

import { SharedModule } from './shared';

import {
    ErrorLogService,
    LOGGING_ERROR_HANDLER_OPTIONS,
    LOGGING_ERROR_HANDLER_PROVIDERS
} from './shared/services/error-handler';
import { AlertsModule } from './alerts/alerts.module';

const rootRouting: ModuleWithProviders = RouterModule.forRoot([
    {
        path: '**',
        redirectTo: '404'
    }
], {
    useHash: true
});

@NgModule({
    declarations: [
        AppComponent
    ],
    imports: [
        BrowserModule,
        HomeModule,
        AboutModule,
        ProfilesModule,
        AlertsModule,
        AuthModule,
        SettingsModule,
        SharedModule,
        AllApplicationsModule,
        UserSettingsModule,
        EventManagerModule,
        ErrorsModule,
        ApplicationModule,
        rootRouting,
        ToastrModule.forRoot()
    ],
    providers: [
        ErrorLogService,
        LOGGING_ERROR_HANDLER_PROVIDERS,
        {
            provide: LOGGING_ERROR_HANDLER_OPTIONS,
            useValue: {
                rethrowError: false,
                unwrapError: false
            }
        }
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
    constructor(public appRef: ApplicationRef) {}
    hmrOnInit(store) {
        console.log('HMR store', store);
    }
    hmrOnDestroy(store) {
        const cmpLocation = this.appRef.components.map(cmp => cmp.location.nativeElement);
        // recreate elements
        store.disposeOldHosts = createNewHosts(cmpLocation);
        // remove styles
        removeNgStyles();
    }
    hmrAfterDestroy(store) {
        // display new elements
        store.disposeOldHosts();
        delete store.disposeOldHosts;
    }
}
