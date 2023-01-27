import { Component, OnInit, Input } from '@angular/core';

@Component({
    selector: 'cbs-sidebar-layout',
    templateUrl: 'sidebar-l.component.html',
    styleUrls: ['./sidebar-l.component.scss']
})
export class SidebarLayoutComponent implements OnInit {
    @Input() noBodyPaddings = true;
    constructor() { }

    ngOnInit() { }
}

