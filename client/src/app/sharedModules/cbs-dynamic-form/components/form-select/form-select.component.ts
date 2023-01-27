import { Component, Input } from '@angular/core';
import { FormGroup } from '@angular/forms';

import { Field } from '../../models/field.interface';
import { FieldConfig } from '../../models/field-config.interface';

@Component({
    selector: 'cbs-form-select',
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
                <select [formControlName]="config.code" [value]="config.value ? config.value : ''" class="slds-select">
                    <option value="">{{ config.placeholder }}</option>
                    <option *ngFor="let option of config.extra['items']">
                      {{ option }}
                    </option>
                  </select>
            </div>
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
export class FormSelectComponent implements Field {
    @Input() config: FieldConfig;
    @Input() group: FormGroup;
    @Input() style: string;
    @Input() styleClass: string;
}
