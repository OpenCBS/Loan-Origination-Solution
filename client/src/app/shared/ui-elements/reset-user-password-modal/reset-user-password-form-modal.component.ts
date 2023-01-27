import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { User } from '../../models/user.model';
import { AbstractControl, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { AppSettings } from '../../../app.settings';
/**
 * Created by alopatin on 16-May-17.
 */


@Component({
    selector: 'cbs-reset-user-password-modal',
    templateUrl: 'reset-user-password-modal.component.html',
    styleUrls: ['./reset-user-password-form-modal.component.scss']
})
export class ResetUserPasswordFormModalComponent implements OnInit {
    public resetPasswordForm: FormGroup;
    public user: User;
    public opened: boolean;
    public isResponseStatusOk = 'null';
    public isSubmitting: boolean;
    public responseMessage = '';

    @Output() resetUserPassword = new EventEmitter();

    ngOnInit(): void {
        this.createForm();
    }

    createForm() {
        this.resetPasswordForm = new FormGroup({
            newPassword: new FormControl('', [
                Validators.required,
                Validators.minLength(6),
                this.matchPasswordsValidator('confirmNewPassword', true)]),
            confirmNewPassword: new FormControl('', [
                Validators.required,
                Validators.minLength(6),
                this.matchPasswordsValidator('newPassword', false)]),
        });
    }

    openModal(user: User) {
        this.user = user;
        this.createForm();

        this.opened = true;
    }

    getHeaderTitle(): string {
        if (this.user != null) {
            return `Reset password for: ${this.user.firstName} ${this.user.lastName}`;

        }
        return '';
    }

    cancel() {
        this.opened = false;
    }

    submitForm({ value, valid }) {
        let obj: Object = {};
        if (valid) {
            obj['newPassword'] = value.newPassword;
            obj['userId'] = this.user.id;
            this.resetUserPassword.emit(obj);
            this.isSubmitting = true;
        }
    }

    clearAll() {
        setTimeout(() => {
            this.isResponseStatusOk = 'null';
            this.opened = false;
        }, AppSettings.SUCCESS_DELAY);
        this.user = new User();
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

    setResponce(resp) {
        this.isSubmitting = false;
        if (resp.status === 'success') {
            this.isResponseStatusOk = 'ok';
            this.responseMessage = '';
            this.clearAll();
        } else if (resp.status === 'error') {
            this.isResponseStatusOk = 'error';
            this.responseMessage = resp.message;
            setTimeout(() => {
                this.isResponseStatusOk = 'null';
            }, AppSettings.ERROR_DELAY);
        }
    }
}
