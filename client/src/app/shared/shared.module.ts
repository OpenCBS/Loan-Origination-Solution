import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { NglModule } from 'ng-lightning/ng-lightning';

import { DynamicFormModule } from '../sharedModules/cbs-dynamic-form/dynamic-form.module';

import {
    HeaderComponent,
    FooterComponent,
    SidebarLayoutComponent,
    PageHeaderComponent,
    BodyBoardComponent
} from './layout';

import {
  CbsLogoSvgComponent,
  CbsLogoSvgHorizontalComponent,
  UserDropdownComponent,
  ProfileFormModalComponent,
  TickCrossComponent,
  ApplicationFormModalComponent,
  FormFieldComponent,
  DoctypeComponent,
  WorkflowStateFieldComponent,
  ManageEventModalComponent,
  PermissionItemComponent,
  ActivityLogComponent,
  LoadingIndicatorComponent,
  StatePermissionsComponent,
  UserFormModalComponent,
  ResetUserPasswordFormModalComponent,
  RoleFormModalComponent,
  ApplicationsSearchComponent,
  RolePermissionsComponent,
  SearchInputComponent,
  CbsSectionComponent,
  CbsFormLayoutComponent,
  CbsFormReadonlyControlComponent
} from './ui-elements';

import {
    HumanizeSizePipe,
    OrderByPipe
} from './pipes';

import {
    AppReadyEvent,
    AppPermissionsService,
    UserService,
    ApiService,
    JwtService,
    RouteService,
    ProfileService,
    AlertsService,
    ProfileModalService,
    QueryFlowService,
    WorkflowService,
    FormFieldBuilderService,
    ApplicationService,
    ApplicationModalService,
    PickRandomColorService,
    WindowSrv,
    RolesService,
    StateService,
    ColorService,
    EventService,
    // Guards
    AuthGuard,
    ProfilesGuard,
    ApplicationInfoGuard,
    AlertsGuard,
    ApplicationFilesGuard,
    ApplicationWorklogsGuard,
    ApplicationFieldsGuard,
    ApplicationStatePermissionsGuard,
    UsersGuard,
    RolesGuard
} from './services';

import {
    EmailValidatorDirective,
    VisibleByPermissionDirective
} from './directives';
import { MultiSelectModule, TableModule } from 'primeng';

const COMPONENTS = [
    // UI Components
    HeaderComponent,
    FooterComponent,
    SidebarLayoutComponent,
    PageHeaderComponent,
    BodyBoardComponent,
    CbsLogoSvgComponent,
    CbsLogoSvgHorizontalComponent,
    UserDropdownComponent,
    ProfileFormModalComponent,
    TickCrossComponent,
    ApplicationFormModalComponent,
    FormFieldComponent,
    DoctypeComponent,
    WorkflowStateFieldComponent,
    ManageEventModalComponent,
    PermissionItemComponent,
    ActivityLogComponent,
    LoadingIndicatorComponent,
    StatePermissionsComponent,
    UserFormModalComponent,
    ResetUserPasswordFormModalComponent,
    RoleFormModalComponent,
    ApplicationsSearchComponent,
    RolePermissionsComponent,
    SearchInputComponent,
    CbsSectionComponent,
    CbsFormLayoutComponent,
    CbsFormReadonlyControlComponent,
    // Pipes
    HumanizeSizePipe,
    OrderByPipe,
    // Directives
    EmailValidatorDirective,
    VisibleByPermissionDirective
];

const SERVICES = [
    // Guards
    AuthGuard,
    ProfilesGuard,
    ApplicationInfoGuard,
    AlertsGuard,
    ApplicationFilesGuard,
    ApplicationWorklogsGuard,
    ApplicationFieldsGuard,
    ApplicationStatePermissionsGuard,
    UsersGuard,
    RolesGuard,
    // Services
    AppReadyEvent,
    AppPermissionsService,
    UserService,
    ApiService,
    JwtService,
    RouteService,
    ProfileService,
    AlertsService,
    ProfileModalService,
    QueryFlowService,
    WorkflowService,
    FormFieldBuilderService,
    ApplicationService,
    ApplicationModalService,
    PickRandomColorService,
    RolesService,
    StateService,
    ColorService,
    EventService,
    { provide: WindowSrv, useValue: window }
];


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule,
    NglModule,
    DynamicFormModule,
    TableModule,
    MultiSelectModule
  ],
  declarations: [
    COMPONENTS
  ],
  exports: [
    ...COMPONENTS,
    DynamicFormModule
  ],
    providers: SERVICES
})
export class SharedModule {}
