import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormGroup, FormArray, FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { AppSettings } from '../../app.settings';

import { ApplicationService, FormFieldBuilderService, AppPermissionsService } from '../../shared';

@Component({
  selector: 'cbs-application-fields',
  templateUrl: 'fields.component.html',
  styleUrls: ['fields.component.scss']
})
export class ApplicationFieldsComponent implements OnInit, OnDestroy {

  public sub: any;
  public isLoading = false;
  public applicationForm: FormGroup;
  public errorOnApplicationData = false;
  public id: number;
  public noData = true;
  public formFieldSectionsData: any = [];
  public showEditFormFields = false;
  public response = '';
  public formHasChanged = false;

  public permissionReadAppFields: string = AppSettings.PERMISSION_NAMES['READ_APPLICATION_FIELDS'];
  public permissionEditAppFields: string = AppSettings.PERMISSION_NAMES['EDIT_APPLICATION_FIELDS'];

  private cachedFormData: any = [];
  private subscription: any;
  private isAllApplicationFieldsPage = false;

  public isFieldsEditable = false;

  constructor(private fb: FormBuilder,
              private applicationService: ApplicationService,
              private formFieldBuilder: FormFieldBuilderService,
              private route: ActivatedRoute,
              private permissionsService: AppPermissionsService) {
  }

  ngOnInit() {
    this.isAllApplicationFieldsPage = this.route.parent.url['value'].length
      && this.route.parent.url['value'][0]['path'] === 'applications';
    this.isFieldsEditable = this.isAllApplicationFieldsPage
      || this.permissionsService.requirePermission(this.permissionEditAppFields);

    this.sub = this.route.parent.params.subscribe(params => {
      this.id = +params['id'];
      this.initFieldsData(this.id);
      this.buildForm();
    });

    this.subscription = this.applicationService.applicationFieldChanged$.subscribe(
      resp => {
        this.formHasChanged = this.checkChanges();
      }
    );
  }

  openEdit() {
    this.showEditFormFields = true;
    this.initCachedFormData();
    this.applicationFormMarkAsUntouched();
  }

  closeEdit() {
    if (this.checkChanges()) {
      if (confirm('You have changes in the form, would you like to reset?')) {
        this.resetForm();
        this.showEditFormFields = false;
      }
    } else {
      this.showEditFormFields = false;
    }
  }

  saveForm({value, valid}) {
    if (valid) {
      this.isLoading = true;
      const dataToSave = this.getFieldValuesFrom(value.fieldSections);
      this.postApplicationFields(dataToSave);
    } else {
      this.applicationFormMarkAsTouched();
    }
  }

  initFieldsData(id) {
    this.isLoading = true;
    (this.isAllApplicationFieldsPage && this.permissionsService.requirePermission(this.permissionReadAppFields)
        ? this.applicationService.getApplicationAllFields(id)
        : this.applicationService.getApplicationStateFields(id)
    ).subscribe(
      res => {
        this.errorOnApplicationData = false;
        this.noData = true;
        if (res.data && res.data.length) {
          this.noData = false;
          this.formFieldSectionsData = res.data;
          this.initApplicationForm();
        }
        this.isLoading = false;
      },
      err => {
        this.isLoading = false;
        this.errorOnApplicationData = true;
        console.error(err);
      }
    );
  }

  buildForm() {
    this.applicationForm = this.fb.group({
      fieldSections: this.fb.array([])
    });
  }

  resetForm() {
    this.initApplicationForm();
    this.formHasChanged = false;
  }

  initApplicationForm() {
    const sections = this.applicationForm.controls['fieldSections'] as FormArray;
    if (sections.length) {
      sections.value.map(item => {
        sections.removeAt(sections.controls.indexOf(item));
      });
    }

    this.formFieldSectionsData.map((section, index) => {
      sections.push(this.fb.array([]));
      this.generateCustomFields(section.fields, index);
    });
  }

  initCachedFormData() {
    if (this.applicationForm.value.fieldSections.length) {
      this.cachedFormData = [];
      this.applicationForm.value.fieldSections.map(section => {
        section.map(field => {
          this.cachedFormData.push(field);
        });
      });
    }
  }

  applicationFormMarkAsUntouched() {
    const fieldSections = this.applicationForm.controls['fieldSections'] as FormArray;
    if (fieldSections.length) {
      fieldSections.controls.map(section => {
        section['controls'].map(field => {
          field['controls'].value.markAsUntouched();
        });
      });
    }
  }

  public findLabel(elem: any) {
    const caption = this.formFieldSectionsData.filter(data => data.id === elem)[0].caption;
    return caption;
  }

  applicationFormMarkAsTouched() {
    const fieldSections = this.applicationForm.controls['fieldSections'] as FormArray;
    if (fieldSections.length) {
      fieldSections.controls.map(section => {
        section['controls'].map(field => {
          field['controls'].value.markAsTouched();
        });
      });
    }
  }

  checkChanges(): boolean {
    let status = false;
    const sections = this.applicationForm.value.fieldSections;


    if (sections && sections.length) {
      sections.map(section => {
        section.map(field => {
          this.cachedFormData.map(item => {
            if (item.fieldId === field.fieldId && item.value !== field.value) {
              status = true;
            }
            if (field.value && field.value.length === 0 && this.isMandatory(+field.fieldId)) {
              status = false;
            }
          });
        });
      });
    }

    return status;
  }

  private isMandatory(fieldId: number): boolean {
    let mandatory = false;
    this.formFieldSectionsData.map(section => {
      section.fields.map((item) => {
        console.log(item.field, 'item.field');
        if (item.field.id === fieldId) {
          mandatory = item.field.mandatory;
        }
      });
    });
    return mandatory;
  }

  getFieldValuesFrom(data): any {
    const result = [];
    data.map(section => {
      section.map(field => {
        result.push(field);
      });
    });

    return result;
  }

  generateCustomFields(fields, index) {
    const section = this.applicationForm.controls['fieldSections']['controls'][index] as FormArray;
    if (section.length) {
      section.value.map(item => {
        section.removeAt(section.controls.indexOf(item));
      });
    }

    fields.map(item => {
      section.push(this.formFieldBuilder.generateField(item));
    });
  }

  postApplicationFields(data) {
    (this.isAllApplicationFieldsPage && this.permissionsService.requirePermission(this.permissionEditAppFields)
        ? this.applicationService.postApplicationAllFields(this.id, data)
        : this.applicationService.postApplicationStateFields(this.id, data)
    ).subscribe(
      resp => {
        this.initFieldsData(this.id);
        this.showEditFormFields = false;
        this.isLoading = false;
        this.response = 'success';
        setTimeout(() => {
          this.response = '';
        }, 1500);
      },
      err => {
        console.error(err);
        this.isLoading = false;
        this.response = 'error';
        setTimeout(() => {
          this.response = '';
        }, 1500);
        throw new Error(err.message);
      },
      (): void => {
        this.formHasChanged = false;
      }
    );
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
    this.subscription.unsubscribe();
  }
}
