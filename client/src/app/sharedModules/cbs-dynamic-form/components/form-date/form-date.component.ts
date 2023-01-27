/**
 * Created by Chyngyz on 4/10/2017.
 */
import { Component, Input, ViewChild } from '@angular/core';
import { FormGroup } from '@angular/forms';

import { Field } from '../../models/field.interface';
import { FieldConfig } from '../../models/field-config.interface';
import { DatepickerComponent } from "../datepicker/datepicker.component";

@Component({
    selector: 'cbs-form-date',
    template: `
    <div
        [ngStyle]="style"
        [class]="styleClass ? styleClass : 'slds-form-element'"
        [formGroup]="group"
        [class.slds-has-error]="
            (group.get(config.code)?.errors?.required && group.get(config.code).touched)
            || (group.get(config.code)?.invalid && group.get(config.code).touched)
            || (group.get(config.code)?.hasError('invalid-date'))">
        <label class="slds-form-element__label">
            <abbr class="slds-required" title="required"
                *ngIf="group.get(config.code)?.errors?.required || group.get(config.code)?.invalid">*</abbr>
            {{ config.caption }}
        </label>
        <div class="slds-form-element__control">
            <input type="text" hidden [formControlName]="config.code">
            <cbs-datepicker
                [value]="config.value ? config.value : ''"
                [enableDropdownPicker]="enableDropdownPicker"
                [showIcon]="showIcon"
                (valueInvalid)="dateInvalid()"
                (valueChange)="setDate($event)"></cbs-datepicker>
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
export class FormDateComponent implements Field {
    @ViewChild(DatepickerComponent, {static: false}) datepicker: DatepickerComponent;
    @Input() config: FieldConfig;
    @Input() group: FormGroup;
    @Input() style: string;
    @Input() styleClass: string;
    @Input() enableDropdownPicker = true;
    @Input() showIcon = true;


    dateInvalid() {
        this.setDate();
        this.group.controls[this.config.code].setErrors({
            'invalid-date': true
        });
    }

    setDate(date?) {
        if (date) {
            this.group.controls[this.config.code].setValue(date);
        } else {
            this.group.controls[this.config.code].setValue('');
        }
    }

    resetValue() {
        this.datepicker.resetValue();
    }
}
