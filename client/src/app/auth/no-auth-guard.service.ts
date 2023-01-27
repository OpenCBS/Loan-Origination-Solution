
import {tap, map, take} from 'rxjs/operators';
import {
    Injectable
} from '@angular/core';
import {
    ActivatedRouteSnapshot,
    CanActivate,
    Router,
    RouterStateSnapshot
} from '@angular/router';
import {
    Observable
} from 'rxjs';

import {
    UserService
} from '../shared';

@Injectable()
export class NoAuthGuard implements CanActivate {
    constructor(
        private router: Router,
        private userService: UserService
    ) {}

    canActivate(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable < boolean > {

        return this.userService.isAuthenticated.pipe(
            take(1),
            map(bool => !bool),
            tap((isAuth) => {
                if (!isAuth && state.url === '/login' ) {
                    this.router.navigate(['/tasks']);
                    return isAuth;
                }
                return isAuth;
            }),);
    }
}
