import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { UserService } from '../../shared/services/user.service';
import { ActivatedRoute, Params, Router } from '@angular/router';
import {delay} from 'rxjs/operators';

/**
 * Created by alopatin on 17-May-17.
 */

@Component({
    selector: 'cbs-reset-password',
    templateUrl: 'reset-password.component.html',
    styleUrls: ['reset-password.component.scss']
})
export class ResetPasswordComponent implements  OnInit {
    public tokenValid = true;
    public isSubmitting = true;
    public wrongDataSubmit = false;
    private token: String;
    public passwordChanged = false;
    public passwordForm: FormGroup;

    constructor(private  userService: UserService,
                private activatedRoute: ActivatedRoute,
                private router: Router) {
    }

    ngOnInit(): void {
        this.passwordForm = new FormGroup({
           password: new FormControl('', [
                Validators.required,
                Validators.minLength(6),
                this.matchPasswordsValidator('confirmPassword', true)
            ]),
            confirmPassword: new FormControl('', [
                Validators.required,
                Validators.minLength(6),
                this.matchPasswordsValidator('password', false)
            ])
        });


        this.activatedRoute.queryParams.subscribe((params: Params) => {
            this.token = params['token'];

            if (!this.token) {
                this.tokenValid = false;
                return;
            }

            this.userService.validateToken(this.token).subscribe(resp => {
                    this.tokenValid = true;
                    this.isSubmitting = false;
                },
                err => {
                    this.tokenValid = false;
                    this.isSubmitting = false;
                });
        });
    }

    changePassword() {
        this.isSubmitting = true;
        this.wrongDataSubmit = false;

        let request: Object = {
            newPassword: this.passwordForm.get('password').value,
            data: this.token
        };

        if ( this.tokenValid) {

            this.userService
                .resetUserPasswordByToken(request)
                .pipe(delay(500))
                .subscribe(
                    data => {
                        this.isSubmitting = false;
                        this.wrongDataSubmit = false;
                        this.passwordChanged = true;
                    },
                    err => {
                        this.wrongDataSubmit = true;
                        this.isSubmitting = false;
                        this.passwordChanged = false;
                    }
                );
        }
    }

    goToLoginPage(e) {
        e.preventDefault();
        this.router.navigate(['/login']);
    }

    matchPasswordsValidator(matchControl: string, reverse: boolean): ValidatorFn {
        return (c: AbstractControl) => {
            let v = c.value;
            let e = c.root.get(matchControl);
            if (e && v !== e.value && !reverse) {
                return {
                    validateEqual: false
                };
            }

            if (e && v === e.value && reverse) {
                delete e.errors['validateEqual'];
                if (!Object.keys(e.errors).length) {
                    e.setErrors(null);
                }
            }

            if (e && v !== e.value && reverse) {
                e.setErrors({ validateEqual: false });
            }

            return null;
        };
    }
}
