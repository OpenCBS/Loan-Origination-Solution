
import {delay} from 'rxjs/operators';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';

import { UserService, RouteService } from '../shared';

@Component({
    selector: 'cbs-auth',
    templateUrl: 'auth.component.html',
    styleUrls: ['auth.component.scss']
})
export class AuthComponent implements OnInit, OnDestroy {
    public isSubmitting = false;
    public wrongDataSubmit = false;

    private sub: any;

    constructor(
        private router: Router,
        private userService: UserService,
        private routeService: RouteService
    ) { }

    ngOnInit() { }

    submitForm({ value, valid }) {
        this.isSubmitting = true;
        this.wrongDataSubmit = false;

        if (valid) {
            this.sub = this.userService
                .attemptAuth(value).pipe(
                delay(500))
                .subscribe(
                    data => {
                        this.isSubmitting = false;
                        this.wrongDataSubmit = false;
                        this.router.navigate([this.routeService.getRedirectUrl()]);
                    },
                    err => {
                        this.wrongDataSubmit = true;
                        this.isSubmitting = false;
                    }
                );
        }
    }

    ngOnDestroy() {
        if (this.sub) {
            this.sub.unsubscribe();
        }
    }

    goToResetPasswordRequestPage(e) {
        e.preventDefault();
        this.router.navigate(['/reset-password-request']);
    }
}
