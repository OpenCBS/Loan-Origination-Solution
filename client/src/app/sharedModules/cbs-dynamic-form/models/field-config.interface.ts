import { ValidatorFn } from '@angular/forms';

export interface FieldConfig {
    id?: number;
    disabled?: boolean;
    placeholder?: string;
    sectionId?: number;
    code: string;
    caption: string;
    fieldType: string;
    unique?: boolean;
    mandatory?: boolean;
    validation?: ValidatorFn[];
    order?: number;
    extra?: any;
    value?: any;
}
