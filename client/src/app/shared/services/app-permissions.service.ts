import { Injectable } from '@angular/core';

import { UserService } from './user.service';

import { AppSettings  } from '../../app.settings';

@Injectable()
export class AppPermissionsService {

    constructor(private userService: UserService) { }

    requirePermission(permission: string[] | string) {
        let currentUser = this.userService.getCurrentUser();
        let currentUserPermissions = currentUser.role ? currentUser.role.permissions : [];
        return currentUserPermissions.some(item => {
            if (item === AppSettings.PERMISSION_NAMES['ALL']) {
                return true;
            } else {
                if (Array.isArray(permission)) {
                    return permission.some(elem => {
                        return elem === item;
                    });
                } else {
                    return item === permission;
                }
            }
        });
    }
}
