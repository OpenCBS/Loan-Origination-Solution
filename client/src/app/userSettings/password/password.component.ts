import { Component, OnInit, OnDestroy } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { UserService } from '../../shared/services/user.service';
import { Router } from '@angular/router';

@Component({
    selector: 'cbs-password',
    templateUrl: 'password.component.html',
    styleUrls: ['password.component.scss']
})
export class PasswordComponent implements OnInit, OnDestroy {
    public passwordForm: FormGroup;

    constructor(private userService: UserService,
                private router: Router) {
    }

    ngOnInit() {
        this.passwordForm = new FormGroup({
            oldPassword: new FormControl('', [
                Validators.required
            ]),
            newPassword: new FormControl('', [
                Validators.required,
                Validators.minLength(6),
                this.matchPasswordsValidator('confirmPassword', true)
            ]),
            confirmPassword: new FormControl('', [
                Validators.required,
                Validators.minLength(6),
                this.matchPasswordsValidator('newPassword', false)
            ])
        });
    }

    ngOnDestroy() {

    }

    submitForm() {
        let request: Object = {
            newPassword: this.passwordForm.get('newPassword').value,
            currentPassword: this.passwordForm.get('oldPassword').value
        };

        this.userService.changeCurrentUserPassword(request).subscribe(
            () => {
                this.userService.announceUserDataChange({status: 'success', isNew: false});
                this.router.navigate(['/']);
            },
            err => {
                alert(err && err.message ? err.message : 'Some error occurred');
            }
        );
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
                e.setErrors({validateEqual: false});
            }

            return null;
        };
    }
}


