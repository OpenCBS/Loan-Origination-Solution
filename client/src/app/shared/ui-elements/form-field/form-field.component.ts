import { debounceTime } from 'rxjs/operators';
import { Component, OnInit, Input } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { DatePipe } from '@angular/common';

import { ApplicationService } from '../../services';
import { AppSettings } from '../../../app.settings';

@Component({
  selector: 'cbs-form-field',
  templateUrl: 'form-field.component.html',
  styleUrls: ['form-field.component.scss'],
  providers: [DatePipe]
})
export class FormFieldComponent implements OnInit {
  @Input() public fieldData: any;
  @Input() public fieldForm: FormGroup;
  public fieldsReadOnly = false;
  public fieldType = '';
  public fieldCaption = '';
  public fieldValue = '';
  public fieldId: number;
  public dateValue = '';
  public listValues: String[];
  public openNglDatepicker = false;
  public gridHeaders = [];
  public gridValues = [];
  public gridTotal: number;
  public multiSelectOptions = [];
  public multiSelectValues = [];
  public formConfigCountry = {url: `${AppSettings.API_ENDPOINT}country/lookup`};

  public dateFormat: string = AppSettings.DATE_FORMAT;

  constructor(private applicationService: ApplicationService) {
  }

  ngOnInit() {
    this.buildField();
    this.fieldForm.valueChanges.pipe(
      debounceTime(350))
      .subscribe(
        value => {
          this.applicationService.announceFieldChange(value);
        }
      );
  }

  buildField() {
    this.fieldData.map(item => {
      if (item.field.id === +this.fieldForm.value.fieldId) {
        this.fieldId = item.field.id;
        this.fieldCaption = item.field.caption;
        this.fieldType = item.field.fieldType;
        this.fieldsReadOnly = item.readOnly ? item.readOnly : false;
        if (this.fieldsReadOnly) {
          this.fieldValue = item.value;
        }

        if (item.field.fieldType === 'LIST' && item.field.extra) {
          this.listValues = item.field.extra.split('|');
        }
        if (item.field.fieldType === 'DATE') {
          this.dateValue = item.value ? item.value : '';
        }
        if (item.field.fieldType === 'GRID') {
          this.gridValues = item.value ? JSON.parse(item.value).data : JSON.parse(item.field.extra).data;
          this.calculateTotal();

          for (let i in this.gridValues[0]) {
            if (i !== 'id') {
              this.gridHeaders.push({
                value: i
              });
            }
          }
        }
        if (item.field.fieldType === 'MULTIPLE_SELECT') {
          this.multiSelectOptions = [];
          let items = JSON.parse(item.field.extra).items;
          items.map(item => {
            this.multiSelectOptions.push({
              value: item
            });
          });

          if (item.value) {
            this.multiSelectValues = JSON.parse(item.value);
          }
        }
      }
    });
  }

  getListValue(field, value) {
    (field === 'Guarantor' && value === ' NO') ? console.log('Hide') : console.log('Show')
  }

  getGridValues() {
    let gridValue = {
      data: {}
    };
    gridValue.data = this.gridValues;
    let senData = JSON.stringify(gridValue);
    this.fieldForm.controls['value'].setValue(senData);
    this.calculateTotal();
  }

  calculateTotal() {
    let total = 0;
    for(let val of this.gridValues) {
      if (val.value) {
        total += parseInt(val.value);
      }
    }
    this.gridTotal = total;
  }

  setMultiSelectValues(value) {
    this.fieldForm.controls['value'].setValue(JSON.stringify(value));
  }

  setDate(date) {
    if (date) {
      this.fieldForm.controls['value'].setValue(date);
    } else {
      this.fieldForm.controls['value'].setValue('');
    }
  }

  dateInvalid() {
    this.fieldForm.controls['value'].setErrors({
      'invalid-date': true
    });
  }
}

