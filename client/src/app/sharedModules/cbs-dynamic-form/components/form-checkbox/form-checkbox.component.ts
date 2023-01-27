/**
 * Created by Chyngyz on 4/11/2017.
 */
import { Component, Input } from '@angular/core';
import { FormGroup } from '@angular/forms';

import { Field } from '../../models/field.interface';
import { FieldConfig } from '../../models/field-config.interface';

@Component({
    selector: 'cbs-form-checkbox',
    template: `
    <div [ngStyle]="style"
        [class]="styleClass ? styleClass : 'slds-form-element'"
        [formGroup]="group"
        [class.slds-has-error]="
            (group.get(config.code).errors?.required && group.get(config.code).touched)
            || (group.get(config.code).invalid && group.get(config.code).touched)">
        <div class="slds-form-element__control">
            <span class="slds-checkbox">
                <abbr class="slds-required" title="required"
                    *ngIf="group.get(config.code).errors?.required || group.get(config.code).invalid">*</abbr>
                <input type="checkbox"
                    [formControlName]="config?.code"
                    [id]="'checkbox-' + config.code + (config.id ? '-' + config.id : '')"/>
                <label class="slds-checkbox__label"
                    [for]="'checkbox-' + config.code + (config.id ? '-' + config.id : '')">
                    <span class="slds-checkbox--faux"></span>
                    <span class="slds-form-element__label">{{ config?.caption }}</span>
                </label>
            </span>
        </div>
    </div>
  `,
    styles: [`
        :host label {
            cursor: pointer;
        }
    `]
})
export class FormCheckboxComponent implements Field {
    @Input() config: FieldConfig;
    @Input() group: FormGroup;
    @Input() style: string;
    @Input() styleClass: string;
}
