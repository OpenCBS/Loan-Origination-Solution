import { Injectable } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { FormField } from '../models';

@Injectable()
export class FormFieldBuilderService {

    constructor(private fb: FormBuilder) { }

    generateField(fieldProperty: FormField) {
        let obj: Object = {};
        obj['fieldId'] = this.generateArrayOptions(fieldProperty, true);
        obj['value'] = this.generateArrayOptions(fieldProperty, false, true);
        return this.fb.group(obj);
    }

    generateArrayOptions(options, addId?, addVal?) {
        let arr: any = [''];
        if (addId) {
            arr = [options.field.id + ''];
        }
        if (addVal) {
            if (options.value) {
                arr = [options.value];
            }
        }
        if (options.field.mandatory) {
            arr.push(Validators.required);
        }
        return arr;
    }
}

