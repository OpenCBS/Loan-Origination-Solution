import { ModuleWithProviders, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { AuthComponent } from './auth.component';
import { NoAuthGuard } from './no-auth-guard.service';
import { SharedModule } from '../shared';
import { NglModule } from 'ng-lightning/ng-lightning';
import { ResetPasswordRequestComponent } from './reset-password-request/reset-password-request.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';

const authRouting: ModuleWithProviders = RouterModule.forChild([
  {
    path: 'login',
    component: AuthComponent,
    canActivate: [NoAuthGuard]
  },
    {
        path: 'reset-password-request',
        component: ResetPasswordRequestComponent,
    },
    {
        path: 'reset-password',
        component: ResetPasswordComponent,
    }
]);

@NgModule({
  imports: [
    authRouting,
    SharedModule,
    FormsModule,
    NglModule,
    CommonModule,
      ReactiveFormsModule,
  ],
  declarations: [
    AuthComponent,
      ResetPasswordRequestComponent,
      ResetPasswordComponent
  ],
  providers: [
    NoAuthGuard
  ]
})
export class AuthModule {}
