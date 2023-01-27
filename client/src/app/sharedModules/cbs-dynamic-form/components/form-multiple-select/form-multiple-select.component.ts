import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';

import { Field } from '../../models/field.interface';
import { FieldConfig } from '../../models/field-config.interface';

@Component({
    selector: 'cbs-form-multiple-select',
    template: `
      <div [ngStyle]="style"
           [class]="styleClass ? styleClass : 'slds-form-element'"
           [formGroup]="group"
           [class.slds-has-error]="
            (group.get(config.code)?.errors?.required && group.get(config.code).touched)
            || (group.get(config.code)?.invalid && group.get(config.code).touched)">
        <label class="slds-form-element__label">
          <abbr class="slds-required" title="required"
                *ngIf="group.get(config.code)?.errors?.required || group.get(config.code)?.invalid">*</abbr>
          {{ config.caption }}
        </label>
        <div class="slds-form-element__control">
          <div class="slds-select_container">
            <p-multiSelect
              styleClass="table-multiselect"
              [showToggleAll]="false"
              [maxSelectedLabels]="10"
              [selectionLimit]="10"
              [options]="options"
              [ngModel]="values"
              [ngModelOptions]="{standalone: true}"
              (ngModelChange)="getValues($event)"
              [panelStyleClass]="'slds-dropdown slds-dropdown_left'"
              optionLabel="value"
              selectedItemsLabel="{0} columns selected"
              defaultLabel="Select">
              <ng-template let-option let-i="index" pTemplate="item">
                <div>{{option.label}}</div>
              </ng-template>
            </p-multiSelect>
          </div>
        </div>
      </div>
    `,
  styleUrls: ['./form-multiple-select.component.scss']
})
export class FormMultipleSelectComponent implements OnInit, Field {
    @Input() config: FieldConfig;
    @Input() group: FormGroup;
    @Input() style: string;
    @Input() styleClass: string;
    public options = [];
    public values = [];

  ngOnInit() {
    this.options = [];
    this.config.extra.items.map(item => {
      this.options.push({
        value: item
      });
    });

    if (this.config.value) {
      this.config.value.map(value => {
        this.values.push({
          value: value
        });
      })
    }
  }

  getValues(value) {
    this.group.controls[this.config.code].setValue(value);
  }
}
