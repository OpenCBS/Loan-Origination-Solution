/**
 * Created by Chyngyz on 4/20/2017.
 */
import { Component, Input } from '@angular/core';
import { FormGroup } from '@angular/forms';

import { Field } from '../../models/field.interface';
import { FieldConfig } from '../../models/field-config.interface';

@Component({
    selector: 'cbs-form-time',
    template: `
    <div
        [ngStyle]="style"
        [class]="styleClass ? styleClass : 'slds-form-element'"
        [formGroup]="group"
        [class.slds-has-error]="
            (group.get(config.code).errors?.required && group.get(config.code).touched)
            || (group.get(config.code).invalid && group.get(config.code).touched)
            || (group.get(config.code).hasError('invalid-time'))">
        <label class="slds-form-element__label">
            <abbr class="slds-required" title="required"
                *ngIf="group.get(config.code).errors?.required || group.get(config.code).invalid">*</abbr>
            {{ config.caption }}
        </label>
        <div class="slds-form-element__control">
            <input type="text" hidden [formControlName]="config.code">
            <cbs-input-mask
                [(ngModel)]="config.value"
                [ngModelOptions]="{standalone: true}"
                (onComplete)="checkValid(config.value)"
                [disabled]="config.disabled"
                [styleClass]="'slds-input'"
                [mask]="timeMask"
                [slotChar]="slotChar"></cbs-input-mask>
            <ngl-icon category="utility" icon="clock" size="x-small" svgClass="slds-input__icon"></ngl-icon>
        </div>
    </div>
    `,
    styles: [`
        :host {
            display: block;
            width: 100%;
        }
        :host ngl-icon {
            position: absolute;
            top: 5px;
            right: 10px;
        }
        :host ::ng-deep ngl-icon svg {
            fill: #bbb;
        }
    `]
})
export class FormTimeComponent implements Field {
    @Input() config: FieldConfig;
    @Input() group: FormGroup;
    @Input() style: string;
    @Input() styleClass: string;
    @Input() timeMask = '99:99';
    @Input() slotChar = '_';


    timeInvalid() {
        this.setDate();
        this.group.controls[this.config.code].setErrors({
            'invalid-time': true
        });
    }

    setDate(time?) {
        if (time) {
            this.group.controls[this.config.code].setValue(time);
        } else {
            this.group.controls[this.config.code].setValue('');
        }
    }

    checkValid(value) {
        if (value && value.length === 5) {
            const time = value.split(':');
            if ((+time[0] <= 24 && +time[0] >= 0) && (+time[1] <= 59 && +time[1] >= 0)) {
                this.setDate(value);
            } else {
                this.timeInvalid();
            }
        }
    }
}
