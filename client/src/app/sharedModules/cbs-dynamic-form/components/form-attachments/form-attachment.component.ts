import { Component, Input } from '@angular/core';
import { FormGroup } from '@angular/forms';

import { Field } from '../../models/field.interface';
import { FieldConfig } from '../../models/field-config.interface';

@Component({
  selector: 'cbs-form-attachment',
  templateUrl: 'form-attachment.component.html',
  styleUrls: ['form-attachment.component.scss']
})
export class FormAttachmentComponent implements Field {
  @Input() config: FieldConfig;
  @Input() group: FormGroup;
  @Input() style: string;
  @Input() styleClass: string;
  public isOpen = false;

  closeUploadModal() {
    this.isOpen = false;
  }

  onUpload(event) {
    if ( event.xhr && event.xhr.status === 200 ) {
      this.closeUploadModal();
    }
  }

  setAttachmentValue(value) {
    if (value) {
      this.group.controls[this.config.code].setValue(value);
    } else {
      this.group.controls[this.config.code].setValue('');
    }
  }

  onError(err) {
    if ( err.xhr && (err.xhr.status >= 400 && err.xhr.status <= 500) ) {
      this.closeUploadModal();
    }
  }

  onClear() {
    this.closeUploadModal();
  }
}
