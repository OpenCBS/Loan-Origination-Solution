import { Component, OnInit } from '@angular/core';

import { UserService } from '../shared';
import { User } from '../shared/models';

@Component({
    selector: 'cbs-user-settings',
    templateUrl: 'userSettings.component.html',
    styleUrls: ['./userSettings.component.scss']
})
export class UserSettingsComponent implements OnInit {
    public currentUser: User;
    constructor(private userService: UserService) { }

    ngOnInit() {
        this.currentUser = this.userService.getCurrentUser();
    }

}

