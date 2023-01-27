import { Component, OnInit, Output, EventEmitter, OnDestroy } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Profile } from '../../models';

import { ProfileModalService, ProfileService } from '../../services';
import { AppSettings } from '../../../app.settings';
import { getCurrentProfileFields, getProfileCustomFields } from '../../../profiles/profile.selectors';
import { CustomFieldSectionMeta, CustomFieldSectionValue } from '../../../core/models';
import { FieldConfig } from '../../../sharedModules/cbs-dynamic-form/models/field-config.interface';

@Component({
  selector: 'cbs-profile-modal',
  templateUrl: 'profile-form-modal.component.html',
  styleUrls: ['./profile-form-modal.component.scss']
})
export class ProfileFormModalComponent implements OnInit, OnDestroy {
  public fields: any = [];
  public opened = false;
  public isNewProfile = false;
  public isSubmitting = false;
  public formHasChanged = false;
  public isResponseStatusOk = 'null';
  public loading = true;
  public profileFieldSections: CustomFieldSectionMeta[] = [];
  public profileEditFieldSections: CustomFieldSectionValue[] = [];
  public profileForm: FormGroup;

  private profileId: number;
  private profileEditFieldsSub: any;
  private profileFieldsSub: any;
  private cachedProfile: Profile = new Profile();

  @Output() saveProfileData = new EventEmitter();

  constructor(private fb: FormBuilder,
              private profileService: ProfileService,
              private modalService: ProfileModalService) {
  }

  ngOnInit() {
    this.buildProfileForm();
    this.modalService.profileModalChanged$.subscribe(resp => {
      this.isSubmitting = false;
      if (resp.status === 'success') {
        this.isResponseStatusOk = 'ok';
        this.clearAll();
      } else if (resp.status === 'error') {
        this.isResponseStatusOk = 'error';
        setTimeout(() => {
          this.isResponseStatusOk = 'null';
        }, AppSettings.ERROR_DELAY);
      }
    });

    this.profileFieldsSub = this.profileService.getProfileFields().pipe(getProfileCustomFields())
      .subscribe((sectionsMeta: CustomFieldSectionMeta[]) => {
          this.profileFieldSections = sectionsMeta;
        this.generateSections(this.profileFieldSections);
        }
      );
  }

  openModal(profile: Profile, bool: boolean) {
    if (!bool) {
      for (let key in profile) {
        if (profile.hasOwnProperty(key)) {
          this.cachedProfile[key] = profile[key];
        }
      }

      this.profileEditFieldsSub = this.profileService.getProfileInfo(profile.id).pipe(getCurrentProfileFields())
        .subscribe((sectionsData: CustomFieldSectionValue[]) => {
          if ( sectionsData.length ) {
            this.profileEditFieldSections = sectionsData;

            const sections = <FormArray>this.profileForm.controls['fieldSections'];

            if ( sections.length ) {
              sections.value.map(item => {
                sections.removeAt(sections.controls.indexOf(item));
              });
            }

            this.profileEditFieldSections.map((section, index) => {
              sections.push(this.fb.array([]));
              this.generateCustomFields(section.values, index);
            });
          }
        });
    }
    this.opened = true;
    this.isNewProfile = bool;
    this.profileId = profile.id;
  }

  clearAll() {
    setTimeout(() => {
      this.isResponseStatusOk = 'null';
      this.opened = false;
    }, AppSettings.SUCCESS_DELAY);
  }

  cancel() {
    this.opened = false;
    this.formHasChanged = false;
  }

  submit({valid, value} ) {
    if ( valid ) {
      const profileDataToSend = {
        profileSections: []
      };
      const valueArray = this.flattenArray(value.fieldSections);

      this.profileFieldSections.map(section => {
        profileDataToSend.profileSections.push({
          sectionCode: section['code'],
          values: section.customFields.map(field => {
            return this.getFieldsValue(field, valueArray);
          })
        });
      });
      let obj: Object = {};
        obj['data'] = profileDataToSend;
        obj['isNew'] = this.isNewProfile;
        obj['id'] = this.profileId;
        this.saveProfileData.emit(obj);
        this.isSubmitting = true;
        this.formHasChanged = false;
    }
  }

  getFieldsValue(field, valueArray) {
    for (let valItem of valueArray ) {
      const key = Object.keys(valItem)[0];
      if ( key === field['code'] ) {
        return {
          code: field['code'],
          values: field.fieldType === 'MULTIPLE_SELECT' && valItem[key] ? this.getMultipleSelectFieldValues(valItem[key]) : valItem[key]
            ? [valItem[key]] : []
        }
      }
    }
  }

  getMultipleSelectFieldValues(values) {
    return values.map(val => val.value)
  }

  flattenArray(array, selector?) {
    let tempArray = [];
    array.map(item => {
      if ( Array.isArray(item) ) {
        tempArray = [...tempArray, ...item];
      }
      if ( selector && !Array.isArray(item) ) {
        tempArray = [...tempArray, ...item[selector]];
      }
    });
    return tempArray;
  }

  submitForm({value, valid}) {
    let obj: Object = {};
    if (valid) {
      obj['data'] = value;
      obj['isNew'] = this.isNewProfile;
      obj['id'] = this.profileId;
      this.saveProfileData.emit(obj);
      this.isSubmitting = true;
      this.formHasChanged = false;
    }
  }

  ngOnDestroy() {
    const sections = <FormArray>this.profileForm.controls['fieldSections'];
    if ( sections.length ) {
      sections.value.map(item => {
        sections.removeAt(sections.controls.indexOf(item));
      });
    }
  }

  buildProfileForm() {
    this.profileForm = this.fb.group({
      fieldSections: this.fb.array([])
    });
  }

  generateSections(sectionsMeta) {
    if ( sectionsMeta.length ) {
      const sections = <FormArray>this.profileForm.controls['fieldSections'];

      if ( sections.length ) {
        sections.value.map(item => {
          sections.removeAt(sections.controls.indexOf(item));
        });
      }

      sectionsMeta.map((section, index) => {
        sections.push(this.fb.array([]));
        this.generateCustomFields(section.customFields, index);
      });
      this.loading = false;
    }
  }

  generateCustomFields(fields, index) {
    const section = <FormArray>this.profileForm.controls['fieldSections']['controls'][index];

    if ( section.length ) {
      section.value.map(item => {
        section.removeAt(section.controls.indexOf(item));
      });
    }

    fields.map(item => {
      const group = this.fb.group({});
      group.addControl(item.code, this.createControl(item));
      section.push(group);
    });
  }

  createControl(config: FieldConfig) {
    const {disabled, validation, value, mandatory} = config;
    const validationOptions = validation || [];
    if ( mandatory ) {
      validationOptions.push(Validators.required);
    }
    return this.fb.control({disabled, value}, validationOptions);
  }
}
