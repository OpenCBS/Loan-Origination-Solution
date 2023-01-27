
import {tap, take} from 'rxjs/operators';
import { Injectable } from '@angular/core';
import {
    ActivatedRouteSnapshot,
    CanActivate,
    CanActivateChild,
    Router,
    RouterStateSnapshot
} from '@angular/router';
import { Observable } from 'rxjs';

import { UserService } from '../user.service';
import { RouteService } from '../route.service';

@Injectable()
export class AuthGuard implements CanActivate, CanActivateChild {
    constructor(
        private router: Router,
        private userService: UserService,
        private routeService: RouteService
    ) {}

    canActivate(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable < boolean > | boolean {

        return this.userService.isAuthenticated.pipe(take(1),
            tap((isAuth) => {
                if (!isAuth) {
                    this.routeService.redirectUrl = state.url;
                    this.router.navigate(['/login']);
                    return isAuth;
                }
                return isAuth;
            }),);
    }

    canActivateChild(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable < boolean > | boolean {

        return this.userService.isAuthenticated.pipe(take(1),
            tap((isAuth) => {
                if (!isAuth) {
                    this.routeService.redirectUrl = state.url;
                    this.router.navigate(['/login']);
                    return isAuth;
                }
                return isAuth;
            }),);
    }
}
