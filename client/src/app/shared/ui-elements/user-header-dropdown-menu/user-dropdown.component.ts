import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'cbs-user-dropdown',
    templateUrl: 'user-dropdown.component.html',
    styleUrls: ['user-dropdown.component.scss']
})
export class UserDropdownComponent implements OnInit {
    public open = false;
    constructor() { }

    ngOnInit() { }
}
