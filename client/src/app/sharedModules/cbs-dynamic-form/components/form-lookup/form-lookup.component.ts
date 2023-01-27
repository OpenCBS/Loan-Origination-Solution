/**
 * Created by Chyngyz on 4/11/2017.
 */
import { Component, OnInit, Input, EventEmitter, Output, ViewChild } from '@angular/core';
import { FormGroup, Validators } from '@angular/forms';

import { Field } from '../../models/field.interface';
import { FieldConfig } from '../../models/field-config.interface';
import { PicklistComponent } from '../picklist/picklist.component';

@Component({
    selector: 'cbs-form-lookup',
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
            <input type="text" hidden [formControlName]="config.code">
            <cbs-picklist
                [config]="config.extra ? config.extra : ''"
                [value]="lookupValue"
                [filterType]="'name'"
                [searchPlaceholder]="searchPlaceholder"
                [selectPlaceholder]="selectPlaceholder"
                (onSelect)="setLookupValue($event)"></cbs-picklist>
        </div>
    </div>
    `,
    styles: [`
        :host {
            display: block;
            width: 100%;
        }
    `]
})
export class FormLookupComponent implements OnInit, Field {
    @ViewChild(PicklistComponent, {static: false}) picklist: PicklistComponent;
    @Input() config: FieldConfig;
    @Input() group: FormGroup;
    @Input() style: string;
    @Input() styleClass: string;
    @Input() searchPlaceholder = 'Search';
    @Input() selectPlaceholder = 'Select';
    @Output() onSelect = new EventEmitter();
    lookupValue: string;

    setLookupValue(value) {
        if (value && value.id) {
            this.group.controls[this.config.code].setValue(value.id);
        } else {
            this.group.controls[this.config.code].setValue('');
        }
        this.onSelect.emit(value);
    }

    clearLookupValue() {
        this.group.controls[this.config.code].setValue('');
        this.lookupValue = '';
        this.picklist.removeWithoutEmit();
    }

    ngOnInit() {
      // TODO
        // if (this.config && this.config.value) {
        //     let valueObj = JSON.parse(this.config.value);
        //     this.lookupValue = valueObj['name'];
        //     this.setLookupValue(valueObj);
        // }

      if (this.config && this.config.value) {
        // let valueObj = JSON.parse(this.config.value);
        // let valueObj = this.config.value;
        this.lookupValue = this.config.value;
        this.setLookupValue(this.config.value);
      }

        if (this.config && this.config.mandatory) {
            this.group.controls[this.config.code].setValidators([Validators.required]);
        }
    }
}
