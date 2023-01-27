
import {first, map, take} from 'rxjs/operators';
import { Injectable } from '@angular/core';
import {
    ActivatedRouteSnapshot,
    CanActivate,
    CanActivateChild,
    Router,
    RouterStateSnapshot
} from '@angular/router';
import { Observable } from 'rxjs';

import { AppSettings  } from '../../../app.settings';
import { AppPermissionsService } from '../app-permissions.service';

import { UserService } from '../user.service';
import { RouteService } from '../route.service';

@Injectable()
export class ApplicationInfoGuard implements CanActivate, CanActivateChild {
    constructor(
        private router: Router,
        private userService: UserService,
        private routeService: RouteService,
        private permissionsService: AppPermissionsService
    ) {}

    canActivate(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable < boolean > | boolean {

        return this.userService.isAuthenticated.pipe(
            take(1),
            map((isAuth) => {
                if (!isAuth) {
                    this.routeService.redirectUrl = state.url;
                    this.router.navigate(['/login']);
                    return isAuth;
                } else {
                    return this.permissionsService.requirePermission(AppSettings.PERMISSION_NAMES['READ_APPLICATION']);
                }
            }),
            first(),);
    }

    canActivateChild(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable < boolean > | boolean {

        return this.userService.isAuthenticated.pipe(
            take(1),
            map((isAuth) => {
                if (!isAuth) {
                    this.routeService.redirectUrl = state.url;
                    this.router.navigate(['/login']);
                    return isAuth;
                } else {
                    return this.permissionsService.requirePermission(AppSettings.PERMISSION_NAMES['READ_APPLICATION']);
                }
            }),
            first(),);
    }
}
