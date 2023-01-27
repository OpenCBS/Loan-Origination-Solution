import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { UserService } from '../../services/user.service';
import { User } from '../../models/user.model';

import { AppSettings } from '../../../app.settings';

@Component({
    selector: 'cbs-header',
    templateUrl: 'header.component.html',
    styleUrls: ['header.component.scss']
})
export class HeaderComponent implements OnInit {
    public user: User = new User();
    public avatarImgUrl: string;

    public permissionReadProfile = AppSettings.PERMISSION_NAMES['READ_PROFILE'];
    public permissionReadApplication = AppSettings.PERMISSION_NAMES['READ_APPLICATION'];
    public permissionReadUser = AppSettings.PERMISSION_NAMES['READ_USER'];
    public permissionReadRoles = AppSettings.PERMISSION_NAMES['READ_ROLE'];
    public permissionReadAlerts = AppSettings.PERMISSION_NAMES['READ_ALERTS'];

    constructor(
        private userService: UserService,
        private router: Router) { }

    ngOnInit() {
        this.user = this.userService.getCurrentUser();
        this.avatarImgUrl =  !this.user.photoPath ? '' : `${AppSettings.API_ENDPOINT}users/${this.user.id}/photo?key=${Math.random()}`;

        this.userService.avatarChange.subscribe(data => {
            this.avatarImgUrl = `${AppSettings.API_ENDPOINT}users/${this.user.id}/photo?key=${Math.random()}`;
        });
    }

    logout() {
        this.userService.purgeAuth();
        this.router.navigate(['/login']);
    }
}
