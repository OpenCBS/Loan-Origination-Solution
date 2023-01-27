import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { AppSettings } from '../../app.settings';
import { JwtService } from '../../shared/services/jwt.service';

/**
 * Created by alopatin on 18-May-17.
 */


@Component({
    selector: 'cbs-user-photo-modal',
    templateUrl: 'user-photo-modal.component.html',
    styleUrls: ['user-photo-modal.component.scss']
})
export class UserPhotoModalComponent implements OnInit {
    @Output() onUpload = new EventEmitter();
    public options: Object = {
        authToken: '',
        authTokenPrefix: 'Bearer',
        autoUpload: false,
        previewUrl: 'url'
    };
    public isSubmiting = false;
    public opened = false;
    public showPreview = false;
    public previewFile: any = {};
    public hasBaseDropZoneOver = false;
    public uploadFile: any;
    public sizeLimit = 10240000;
    public isFileSizeLarge = false;

    private events: EventEmitter<any> = new EventEmitter();

    constructor(private jwtService: JwtService) {

    }

    ngOnInit(): void {

        this.options['url'] = `${AppSettings.API_ENDPOINT}users/current/photo`;
        this.options['authToken'] = this.jwtService.getToken();
    }

    openModal() {
        this.isSubmiting = false;
        this.opened = true;
        this.clearFilesQueue();
    }

    cancel() {
        this.opened = false;
    }

    clearFilesQueue() {
        this.showPreview = false;
        this.previewFile = {};
        this.uploadFile = null;
        this.hasBaseDropZoneOver = false;
        this.events.emit('clearQueue');
    }

    beforeFileUpload(uploadingFile): void {
        this.showPreview = false;
        this.isSubmiting = true;
        if (uploadingFile.size > this.sizeLimit) {
            uploadingFile.setAbort();
            alert('File is too large, please select other.');
            this.clearFilesQueue();
            this.isSubmiting = false;
        }
    }

    beforeAdd(data) {
        for (let key in data) {
            if (key !== 'lastModifiedDate' && key !== 'slice' && key !== 'webkitRelativePath') {
                this.previewFile[key] = data[key];
            }
        }
        this.isFileSizeLarge = data.size > this.sizeLimit;
        this.showPreview = true;
    }

    handleUpload(data): void {
        this.uploadFile = null;
        this.showPreview = false;
        if (data && data.response) {
            this.isSubmiting = false;
            data = JSON.parse(data.response);
            this.uploadFile = data;
            this.onUpload.emit(data);
        }
    }

    startUpload() {
        this.events.emit('startUpload');
    }

    fileOverBase(e: any): void {
        this.hasBaseDropZoneOver = e;
    }
}
