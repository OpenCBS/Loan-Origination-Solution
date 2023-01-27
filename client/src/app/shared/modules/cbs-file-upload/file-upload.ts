import {
  NgModule,
  Component,
  OnInit,
  Input,
  Output,
  EventEmitter,
  TemplateRef,
  AfterContentInit,
  ContentChildren,
  QueryList
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { DomSanitizer } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

import { CbsTemplateDirective, SharedTreetableModule } from '../cbs-tree-table/shared-treetable';
import { ProfileService } from '../../services';
import { AppSettings } from '../../../app.settings';
import { NglModule } from 'ng-lightning';

@Component({
  selector: 'cbs-file-upload',
  templateUrl: 'file-upload.html',
  styleUrls: ['file-upload.scss']
})
export class FileUploadComponent implements OnInit, AfterContentInit {

  @Input() name: string;

  @Input() multiple: boolean;

  @Input() accept: string;

  @Input() disabled: boolean;

  @Input() auto: boolean;

  @Input() maxFileSize: number;

  @Input() invalidFileSizeMessageSummary = '{0}: Invalid file size, ';

  @Input() invalidFileSizeMessageDetail = 'Maximum upload size is {0}.';

  @Input() style: string;

  @Input() includeToken: boolean;

  @Input() tokenName = 'token';

  @Input() title: string;

  @Input() size = 'large';

  @Input() styleClass: string;

  @Input() previewWidth = 50;

  @Input() chooseLabel = 'Choose';

  @Input() config: any;

  @Output() onBeforeUpload: EventEmitter<any> = new EventEmitter();

  @Output() onBeforeSend: EventEmitter<any> = new EventEmitter();

  @Output() onUpload: EventEmitter<any> = new EventEmitter();

  @Output() onError: EventEmitter<any> = new EventEmitter();

  @Output() onClear: EventEmitter<any> = new EventEmitter();

  @Output() onSelect: EventEmitter<any> = new EventEmitter();

  @ContentChildren(CbsTemplateDirective) templates: QueryList<any>;

  public files: File[];

  public progress = 0;

  public profileId: any;

  public fileData: any;

  public uploading = false;

  public url: string;

  public dragHighlight: boolean;

  public apiEndpoint: string;

  public msgs: any[] = [];

  public opened = false;

  public imageUrl = '';

  public fileTemplate: TemplateRef<any>;

  public contentTemplate: TemplateRef<any>;

  public toolbarTemplate: TemplateRef<any>;

  constructor(private sanitizer: DomSanitizer,
              private profileService: ProfileService) {
  }

  ngOnInit() {
    this.files = []
    this.profileId = window.localStorage.getItem('profileId')
    if (this.config.value && this.profileId) {
      this.getAttachmentValue();
    }

    this.apiEndpoint = AppSettings.API_ENDPOINT;
  }

  getAttachmentValue() {
    this.profileService.getAttachment({
      profileId: this.profileId,
      value: this.config
    }).subscribe(res => {
      this.fileData = res;
      this.onFileSelect(res, 'edit')
    })
  }

  ngAfterContentInit(): void {
    this.templates.forEach((item) => {
      switch (item.getType()) {
        case 'file':
          this.fileTemplate = item.template;
          break;

        case 'content':
          this.contentTemplate = item.template;
          break;

        case 'toolbar':
          this.toolbarTemplate = item.template;
          break;

        default:
          this.fileTemplate = item.template;
          break;
      }
    });
  }

  onChooseClick(event, fileInput) {
    fileInput.value = null;
    fileInput.click();
  }

  onFileSelect(event, type?) {
    this.msgs = [];
    if (!this.multiple) {
      this.files = [];
    }

    let files = [];
    if (type === 'edit') {
      files.push(event)
    } else {
      files = event.dataTransfer ? event.dataTransfer.files : event.target.files;
    }

    for (let i = 0; i < files.length; i++) {
      const file = files[i];
      if (this.validate(file)) {
        if (type === 'edit') {
          file.objectURL = this.sanitizer.bypassSecurityTrustResourceUrl((window.URL.createObjectURL(event)));
        } else if(this.isImage(file)) {
          file.objectURL = this.sanitizer.bypassSecurityTrustUrl((window.URL.createObjectURL(files[i])));
        }

        this.files.push(files[i]);
      }
    }

    this.onSelect.emit({originalEvent: event, files: files});
  }

  openAttachment(file) {
    this.imageUrl = file.objectURL;
    this.opened = true;
  }

  resetModal() {
    this.imageUrl = '';
  }

  validate(file: File): boolean {
    if (this.maxFileSize && file.size > this.maxFileSize) {
      this.msgs.push({
        summary: this.invalidFileSizeMessageSummary.replace('{0}', file.name),
        detail: this.invalidFileSizeMessageDetail.replace('{0}', this.formatSize(this.maxFileSize))
      });
      return false;
    }

    return true;
  }

  isImage(file: File): boolean {
    return /^image\//.test(file.type);
  }

  onImageLoad(img: any) {
    window.URL.revokeObjectURL(img.src);
  }

  upload(data) {
    this.url = `profiles/${data.id}/${data.fieldValue[0].sectionCode}/${data.fieldValue[0].fieldCode}/attachment`;
    this.uploading = true;
    this.msgs = [];
    const xhr = new XMLHttpRequest(),
      formData = new FormData();

    this.onBeforeUpload.emit({
      'xhr': xhr,
      'formData': formData
    });

    for (let i = 0; i < data.fieldValue[0].files.length; i++) {
      formData.append('file', data.fieldValue[0].files[0].files[i], data.fieldValue[0].files[0].files[i].name);
    }

    xhr.upload.addEventListener('progress', (e: ProgressEvent) => {
      if (e.lengthComputable) {
        this.progress = Math.round((e.loaded * 100) / e.total);
      }
    }, false);


    xhr.onreadystatechange = () => {
      if (xhr.readyState === 4) {
        this.progress = 0;
        if (xhr.status >= 200 && xhr.status < 300) {
          this.onUpload.emit({xhr: xhr, files: data.fieldValue[0].files});
        } else {
          this.onError.emit({xhr: xhr, files: data.fieldValue[0].files});
        }
        this.clear();
        this.uploading = false;
      }
    };

    console.log(AppSettings.API_ENDPOINT);
    xhr.open('POST', `${AppSettings.API_ENDPOINT}${this.url}`, false);

    const token = window.localStorage.getItem(this.tokenName);
    xhr.setRequestHeader('Authorization', `Bearer ${token}`);


    this.onBeforeSend.emit({
      'xhr': xhr,
      'formData': formData
    });

    xhr.send(formData);
  }

  clear() {
    this.files = [];
    this.onClear.emit();
  }

  remove(index: number) {
    this.files.splice(index, 1);
  }

  hasFiles(): boolean {
    return this.files && this.files.length > 0;
  }

  onDragEnter(e) {
    if (!this.disabled) {
      e.stopPropagation();
      e.preventDefault();
    }
  }

  onDragOver(e) {
    if (!this.disabled) {
      this.dragHighlight = true;
      e.stopPropagation();
      e.preventDefault();
    }
  }

  onDragLeave(e) {
    if (!this.disabled) {
      this.dragHighlight = false;
    }
  }

  onDrop(e) {
    if (!this.disabled) {
      this.dragHighlight = false;
      e.stopPropagation();
      e.preventDefault();

      this.onFileSelect(e);
    }
  }

  formatSize(bytes) {
    if (bytes === 0) {
      return '0 B';
    }
    const k = 1000,
      dm = 3,
      sizes = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],
      i = Math.floor(Math.log(bytes) / Math.log(k));

    return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
  }
}

@NgModule({
  imports: [CommonModule, SharedTreetableModule, FormsModule, NglModule],
  exports: [FileUploadComponent, SharedTreetableModule],
  declarations: [FileUploadComponent]
})
export class FileUploadModule {
}
