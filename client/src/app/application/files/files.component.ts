/* tslint:disable */
import { Component, OnInit, OnDestroy, EventEmitter } from '@angular/core';
import { Router, ActivatedRoute, NavigationExtras } from '@angular/router';
import { ApplicationService, JwtService } from '../../shared';
import { AppSettings } from '../../app.settings';
import {map} from 'rxjs/operators';

@Component({
    selector: 'cbs-application-files',
    templateUrl: 'files.component.html',
    styleUrls: ['./files.component.scss']
})
export class ApplicationFilesComponent implements OnInit, OnDestroy {
    public applicationData: any;
    public isLoading = false;
    public noData = true;
    public errorOnApplicationData = false;
    public opened = false;
    public isResponseStatusOk = '';
    public isSubmiting = false;
    public previewFile: any = {};
    public showPreview = false;
    public isFileSizeLarge = false;
    public sortDirection = 1;
    public propertyName: string;

    public uploadFile: any;
    public sizeLimit = 10240000;
    public hasBaseDropZoneOver = false;
    public options: Object = {
        authToken: '',
        authTokenPrefix: 'Bearer',
        autoUpload: false,
        previewUrl: 'url'
    };
    public queryPageObject: any;
    public queryObject: any = {};
    public pagerData: any = {};
    public currentPage = 0;

    public permissionCreateAppFiles: string = AppSettings.PERMISSION_NAMES['CREATE_APPLICATION_ATTACHMENTS'];
    public permissionDeleteAppFiles: string = AppSettings.PERMISSION_NAMES['DELETE_APPLICATION_ATTACHMENTS'];

    private events: EventEmitter<any> = new EventEmitter();


    private sub: any;
    private id: number;
    private firstTime = true;


    constructor(
        private router: Router,
        private route: ActivatedRoute,
        private applicationService: ApplicationService,
        private jwtService: JwtService) { }

    ngOnInit() {
        this.queryPageObject = this.route
                .queryParams
          .pipe(map(params => params || null));
        this.sub = this.route.parent.params.subscribe(params => {
            this.id = +params['id'];
            this.options['url'] = `${AppSettings.API_ENDPOINT}applications/${this.id}/attachments`;
            this.options['authToken'] = this.jwtService.getToken();


            this.queryPageObject.subscribe((obj: any) => {
                // This is a hack for cloning object without the getters and setters,
                // here the obj keys are non-writable and non-configurable
                this.queryObject = JSON.parse(JSON.stringify(obj));

                if (this.queryObject.hasOwnProperty('page')) {
                    this.getApplicationFiles(this.id, true, +this.queryObject.page);
                } else {
                    this.getApplicationFiles(this.id, true, 1);
                }
            });
        });

    }

    gotoPage(page: number) {
        this.queryObject.page = page;
        this.isLoading = true;

        let navigationExtras: NavigationExtras = {
            queryParams: this.queryObject
        };
        this.router.navigate([`/application/${this.id}/files`], navigationExtras);
    }

    ngOnDestroy() {
        this.firstTime = true;
        this.sub.unsubscribe();
    }

    getUrl(fileName) {
        return `${AppSettings.API_ENDPOINT}applications/${this.id}/attachments/${fileName}`;
    }

    getApplicationFiles(id, force?, page?) {
        if (this.firstTime) {
            this.isLoading = true;
        }
        this.applicationService.getApplicationFiles(id, force, page).subscribe(
            resp => {
                this.firstTime = false;
                this.applicationData = resp.data;
                this.isLoading = false;
                this.applicationData && this.applicationData.content.length ? this.noData = false : this.noData = true;
                this.pagerData.size = resp.data.size;
                this.pagerData.total = resp.data.totalElements;
                this.currentPage = resp.data.number + 1;

                this.errorOnApplicationData = false;
            },
            err => {
                this.firstTime = false;
                this.isLoading = false;
                this.noData = true;
                this.errorOnApplicationData = true;
                console.log(err);
            }
        );
    }

    openModal() {
        this.isSubmiting = false;
        this.opened = true;
        this.clearFilesQueue();
    }

    cancel() {
        this.opened = false;
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
        if (data.size > this.sizeLimit) {
            this.isFileSizeLarge = true;
        } else {
            this.isFileSizeLarge = false;
        }
        this.showPreview = true;
    }

    handleUpload(data): void {
        this.uploadFile = null;
        this.showPreview = false;
        if (data && data.response) {
            this.isSubmiting = false;
            data = JSON.parse(data.response);
            this.uploadFile = data;
            this.getApplicationFiles(this.id, true);
        }
    }

    clearFilesQueue() {
        this.showPreview = false;
        this.previewFile = {};
        this.uploadFile = null;
        this.hasBaseDropZoneOver = false;
        this.events.emit('clearQueue');
    }

    startUpload() {
        this.events.emit('startUpload');
    }

    fileOverBase(e: any): void {
        this.hasBaseDropZoneOver = e;
    }

    deleteFile(file) {
        if (confirm(`Please confirm that you want to delete file: ${file.name}`)) {

            this.applicationService.deleteApplicationFile(file.id).subscribe(
                resp => {
                    this.getApplicationFiles(this.id, true);
                },
                err => {
                    console.log(err);
                    alert('Unable to delete the file.');
                });
        }
    }
}
/* tslint:enable */
