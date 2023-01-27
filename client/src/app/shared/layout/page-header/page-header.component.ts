import { Component, OnInit, Input, HostBinding } from '@angular/core';

@Component({
    selector: 'cbs-page-header',
    templateUrl: 'page-header.component.html',
    styleUrls: ['./page-header.component.scss']
})
export class PageHeaderComponent implements OnInit {
    @Input() public headerTitle = '';
    @Input() public headerHeading = '';
    @Input() public svgIconName: string[] = [];

    @HostBinding('class.no-padding')
    @Input()
    public noPadding = false;

    @HostBinding('class.no-background')
    @Input()
    public noBackground = false;

    @HostBinding('class.no-border-bottom')
    @Input()
    public noBorderBottom = false;

    constructor() { }

    ngOnInit() { }
}


