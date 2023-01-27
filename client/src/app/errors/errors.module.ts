import { ModuleWithProviders, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { NotFoundComponent } from './404/404.component';
import { ServerErrorComponent } from './server-error/server-error.component';

const errorsRouting: ModuleWithProviders = RouterModule.forChild([
    {
        path: '404',
        component: NotFoundComponent
    },
    {
        path: 'server-error',
        component: ServerErrorComponent
    }
]);

@NgModule({
    imports: [
        errorsRouting,
        CommonModule
    ],
    declarations: [
        NotFoundComponent,
        ServerErrorComponent
    ],
    providers: [ ]
})
export class ErrorsModule {}
