import { Component, OnInit, Input } from '@angular/core';

@Component({
    selector: 'cbs-doctype-icon',
    templateUrl: 'doctype.component.html',
    styles: [':host { display: inline-block;}']
})
export class DoctypeComponent implements OnInit {
    @Input() public fileType = 'unknown';
    @Input() public fileName = '';
    public type = 'unknown';

    private fileTypes = [
        'ai',
        'attachment',
        'audio',
        'box_notes',
        'csv',
        'eps',
        'excel',
        'exe',
        'flash',
        'gdoc',
        'gdocs',
        'gform',
        'gpres',
        'gsheet',
        'html',
        'image',
        'keynote',
        'link',
        'mp4',
        'overlay',
        'pack',
        'pages',
        'pdf',
        'ppt',
        'psd',
        'rtf',
        'slide',
        'stypi',
        'txt',
        'unknown',
        'video',
        'visio',
        'webex',
        'word',
        'xml',
        'zip'
    ];
    constructor() { }

    ngOnInit() {
        if (this.fileType && this.fileType !== this.type) {
            let fileTypeArry = this.fileType.split('/');
            switch (fileTypeArry[1]) {
                case 'png':
                case 'jpg':
                case 'jpeg':
                case 'gif':
                    this.type = 'image';
                    break;
                case 'docm':
                case 'docx':
                case 'dotx':
                case 'dotm':
                case 'docb':
                case 'doc':
                    this.type = 'word';
                    break;
                case 'xls':
                case 'xlt':
                case 'xlm':
                case 'xlsx':
                case 'xlsm':
                case 'xltx':
                case 'xltm':
                    this.type = 'excel';
                    break;
                case 'pptx':
                case 'potx':
                    this.type = 'ppt';
                    break;
                case 'plain':
                    this.type = 'txt';
                    break;
                default:
                    this.type = 'unknown';
            }

            this.fileTypes.map(item => {
                if (item === fileTypeArry[1]) {
                    this.type = item;
                }
            });
        }
    }
}


