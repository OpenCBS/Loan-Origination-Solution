/**
 * Created by Chyngyz on 4/10/2017.
 */
import { Component, Input } from '@angular/core';
import { FormGroup } from '@angular/forms';

import { Field } from '../../models/field.interface';
import { FieldConfig } from '../../models/field-config.interface';

@Component({
    selector: 'cbs-form-textarea',
    template: `
    <div [ngStyle]="style"
        [class]="styleClass ? styleClass : 'slds-form-element'"
        [formGroup]="group"
        [class.slds-has-error]="
            (group.get(config.code).errors?.required && group.get(config.code).touched)
            || (group.get(config.code).invalid && group.get(config.code).touched)">
        <label class="slds-form-element__label">
            <abbr class="slds-required" title="required"
                *ngIf="group.get(config.code).errors?.required || group.get(config.code).invalid">*</abbr>
            {{ config.caption }}
        </label>
        <div class="slds-form-element__control">
            <textarea
                class="slds-textarea"
                [formControlName]="config.code"
                [attr.placeholder]="config.placeholder"
                [attr.cols]="colSize"
                [attr.rows]="rowSize"></textarea>
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
export class FormTextareaComponent implements Field {
    @Input() config: FieldConfig;
    @Input() group: FormGroup;
    @Input() style: string;
    @Input() styleClass: string;
    @Input() rowSize = 3;
    @Input() colSize = 20;
}
