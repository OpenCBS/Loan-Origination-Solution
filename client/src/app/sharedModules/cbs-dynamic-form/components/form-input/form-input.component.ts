import { Component, Input } from '@angular/core';
import { FormGroup } from '@angular/forms';

import { Field } from '../../models/field.interface';
import { FieldConfig } from '../../models/field-config.interface';

@Component({
  selector: 'cbs-form-input',
  templateUrl: 'form-input.component.html',
  styleUrls: ['form-input.component.scss']
})
export class FormInputComponent implements Field {
  @Input() config: FieldConfig;
  @Input() group: FormGroup;
  @Input() style: string;
  @Input() styleClass: string;

  getType(fieldType) {
    switch (fieldType) {
      case 'TEXT':
        return 'text';
      case 'NUMERIC':
        return 'number';
      case 'EMAIL':
        return 'email';
      case 'PASSWORD':
        return 'password';
    }
  }
}
