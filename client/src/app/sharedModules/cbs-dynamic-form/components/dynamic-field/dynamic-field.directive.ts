import {
    ComponentFactoryResolver,
    ComponentRef,
    Directive,
    Input,
    OnChanges,
    OnInit,
    Type,
    ViewContainerRef
} from '@angular/core';
import { FormGroup } from '@angular/forms';

import { FormButtonComponent } from '../form-button/form-button.component';
import { FormInputComponent } from '../form-input/form-input.component';
import { FormSelectComponent } from '../form-select/form-select.component';
import { FormMultipleSelectComponent } from '../form-multiple-select/form-multiple-select.component';
import { FormTextareaComponent } from '../form-textarea/form-textarea.component';
import { FormDateComponent } from '../form-date/form-date.component';
import { FormLookupComponent } from '../form-lookup/form-lookup.component';
import { FormCheckboxComponent } from '../form-checkbox/form-checkbox.component';
import { FormTimeComponent } from '../form-time/form-time.component';

import { Field } from '../../models/field.interface';
import { FieldConfig } from '../../models/field-config.interface';
import { FormAttachmentComponent } from '../form-attachments/form-attachment.component';
import { FormGridComponent } from '../form-grid/form-grid.component';

const components: {[type: string]: Type<Field>} = {
    BUTTON: FormButtonComponent,
    TEXT: FormInputComponent,
    EMAIL: FormInputComponent,
    PASSWORD: FormInputComponent,
    NUMERIC: FormInputComponent,
    TEXT_AREA: FormTextareaComponent,
    LIST: FormSelectComponent,
    MULTIPLE_SELECT: FormMultipleSelectComponent,
    DATE: FormDateComponent,
    TIME: FormTimeComponent,
    LOOKUP: FormLookupComponent,
    CHECKBOX: FormCheckboxComponent,
    ATTACHMENT: FormAttachmentComponent,
    GRID: FormGridComponent
};

@Directive({
    selector: '[cbsDynamicField]'
})
export class DynamicFieldDirective implements Field, OnChanges, OnInit {
    @Input()
    config: FieldConfig;

    @Input()
    group: FormGroup;

    component: ComponentRef<Field>;

    constructor(private resolver: ComponentFactoryResolver,
                private container: ViewContainerRef) {
    }

    ngOnChanges() {
        if (this.component) {
            this.component.instance.config = this.config;
            this.component.instance.group = this.group;
        }
    }

    ngOnInit() {
        if (!components[this.config.fieldType]) {
            const supportedTypes = Object.keys(components).join(', ');
            throw new Error(
                `Trying to use an unsupported type (${this.config.fieldType}). Supported types: ${supportedTypes}`
            );
        }
        const component = this.resolver.resolveComponentFactory<Field>(components[this.config.fieldType]);
        this.component = this.container.createComponent(component);
        this.component.instance.config = this.config;
        this.component.instance.group = this.group;
    }
}
