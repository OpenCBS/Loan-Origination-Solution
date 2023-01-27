import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

import { NglModule } from 'ng-lightning';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';

import { DynamicFieldDirective } from './components/dynamic-field/dynamic-field.directive';
import { DynamicFormComponent } from './containers/dynamic-form/dynamic-form.component';
import { FormButtonComponent } from './components/form-button/form-button.component';
import { FormInputComponent } from './components/form-input/form-input.component';
import { FormSelectComponent } from './components/form-select/form-select.component';
import { FormMultipleSelectComponent } from './components/form-multiple-select/form-multiple-select.component';
import { FormTextareaComponent } from './components/form-textarea/form-textarea.component';
import { FormDateComponent } from './components/form-date/form-date.component';
import { DatepickerComponent } from './components/datepicker/datepicker.component';
import { InputMaskComponent } from './components/input-mask/inputmask.component';
import { PicklistComponent } from './components/picklist/picklist.component';
import { FormLookupComponent } from './components/form-lookup/form-lookup.component';
import { FormCheckboxComponent } from './components/form-checkbox/form-checkbox.component';
import { FormTimeComponent } from './components/form-time/form-time.component';
import { TranslateModule } from '@ngx-translate/core';
import { MultiSelectModule } from 'primeng/multiselect';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormAttachmentComponent } from './components/form-attachments/form-attachment.component';
import { FileUploadModule } from '../../shared/modules/cbs-file-upload/file-upload';
import { FormGridComponent } from './components/form-grid/form-grid.component';
import { TableModule } from 'primeng';

const COMPONENTS = [
  DynamicFieldDirective,
  DynamicFormComponent,
  FormButtonComponent,
  FormInputComponent,
  FormSelectComponent,
  FormMultipleSelectComponent,
  FormTextareaComponent,
  FormDateComponent,
  InputMaskComponent,
  DatepickerComponent,
  PicklistComponent,
  FormLookupComponent,
  FormCheckboxComponent,
  FormTimeComponent,
  FormAttachmentComponent,
  FormGridComponent
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    TranslateModule,
    InfiniteScrollModule,
    NglModule.forRoot({
      svgPath: '/assets/icons'
    }),
    BrowserModule,
    BrowserAnimationsModule,
    MultiSelectModule,
    FileUploadModule,
    TableModule,
  ],
  declarations: COMPONENTS,
  exports: [
    ...COMPONENTS,
    InfiniteScrollModule
  ],
  entryComponents: [
    FormButtonComponent,
    FormInputComponent,
    FormSelectComponent,
    FormMultipleSelectComponent,
    FormTextareaComponent,
    FormDateComponent,
    InputMaskComponent,
    DatepickerComponent,
    FormLookupComponent,
    FormCheckboxComponent,
    FormTimeComponent,
    FormAttachmentComponent,
    FormGridComponent
  ]
})
export class DynamicFormModule {
}
