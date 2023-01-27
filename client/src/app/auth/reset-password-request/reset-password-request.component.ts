import { Component, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../shared/services/user.service';
import {delay} from 'rxjs/operators';

/**
 * Created by alopatin on 17-May-17.
 */


@Component({
    selector: 'cbs-reset-password-request',
    templateUrl: 'reset-password-request.component.html',
    styleUrls: ['reset-password-request.component.scss']
})
export class ResetPasswordRequestComponent implements OnDestroy {
    public isSubmitting = false;
    public wrongDataSubmit = false;
    public tokenSent = false;

    private sub: any;

    constructor(
        private router: Router,
        private userService: UserService,
    ) { }


    ngOnDestroy(): void {
        if (this.sub) {
            this.sub.unsubscribe();
        }
    }

    goToLoginPage(e) {
        e.preventDefault();
        this.router.navigate(['/login']);
    }

    submitForm({ value, valid }) {
        this.isSubmitting = true;
        this.wrongDataSubmit = false;

        if (valid) {
            this.sub = this.userService
                .requestPasswordRequestToken({data: value.email})
                .pipe(delay(500))
                .subscribe(
                    data => {
                        this.isSubmitting = false;
                        this.wrongDataSubmit = false;
                        this.tokenSent = true;
                    },
                    err => {
                        this.wrongDataSubmit = true;
                        this.isSubmitting = false;
                    }
                );
        }
    }
}
