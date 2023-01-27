import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { UserService } from '../../shared';
import { User } from '../../shared/models';
import { UserFormModalComponent } from '../../shared/ui-elements/user-edit-modal/user-form-modal.component';
import { UserPhotoModalComponent } from '../user-photo/user-photo-modal.component';
import { AppSettings } from '../../app.settings';

@Component({
    selector: 'cbs-user-details',
    templateUrl: 'info.component.html',
    styleUrls: ['info.component.scss']
})
export class UserDetailsComponent implements OnInit {
    @ViewChild(UserFormModalComponent, {static: false}) private userFormModal: UserFormModalComponent;
    @ViewChild(UserPhotoModalComponent, {static: false}) private userPhotoModal: UserPhotoModalComponent;


    public currentUser: User;
    public userForm: FormGroup;
    public showUpdateUserSettings = false;
    public avatarImgUrl: string;

    private readonly volunteerTypeId = 3;
    private isVolunteer = false;


    constructor(private userService: UserService) {
    }

    ngOnInit() {
        this.currentUser = this.userService.getCurrentUser();
        this.isVolunteer = this.currentUser.role.id === this.volunteerTypeId;
        this.avatarImgUrl = !this.currentUser.photoPath ? ''
            : `${AppSettings.API_ENDPOINT}users/${this.currentUser.id}/photo?key=${Math.random()}`;

        this.userForm = new FormGroup({
            username: new FormControl(this.currentUser.username ? this.currentUser.username : ''),
            firstName: new FormControl(this.currentUser.firstName ? this.currentUser.firstName : '', [Validators.required]),
            lastName: new FormControl(this.currentUser.lastName ? this.currentUser.lastName : '', [Validators.required]),
            email: new FormControl(this.currentUser.email ? this.currentUser.email : '', [
                Validators.required,
                Validators.pattern('[a-zA-Z\\d\\_\\.+\\-]+@[a-zA-Z\\d\\-]+\\.[a-zA-Z\\d\\-\\.]+')]),
            mobilePhone: new FormControl(this.currentUser.mobilePhone ? this.currentUser.mobilePhone : '')
        });
    }

    openUpdateForm() {
        this.userFormModal.openModal(this.currentUser, false, true);
    }

    saveUser(user) {
        this.userService.updateCurrentUser(user.data).subscribe(resp => {
            this.showUpdateUserSettings = false;
            this.userService.announceUserDataChange({status: 'success', isNew: false});
            this.userService.populate();
            this.currentUser = resp;
        }, err => {
            this.userFormModal.setErrorResponse(err);
            this.userService.announceUserDataChange({status: 'error', isNew: false});
        });
    }


    closeUpdateForm(e) {
        e.preventDefault();
        this.showUpdateUserSettings = false;
    }

    submitForm() {
        let user = {
            firstName: this.getFormValue('firstName'),
            lastName: this.getFormValue('lastName'),
            email: this.getFormValue('email'),
            // mobilePhone: this.getFormValue('mobilePhone'),
            // otherLanguages: this.getFormValue('otherLanguages'),
            // address: this.getFormValue('address'),
            // postalCode: this.getFormValue('postalCode'),
            // city: this.getFormValue('city'),
            // placeOfBirthCity: this.getFormValue('placeOfBirthCity'),
            // placeOfBirthCountry: this.getFormValue('placeOfBirthCountry'),
            // country: this.getFormValue('country'),
            // language: this.getFormValue('language'),
            // origin: this.getFormValue('origin'),
            // citizenship: this.getFormValue('citizenship'),
            // birthDate: this.getFormValue('birthDate')
        };

        this.userService.updateCurrentUser(user).subscribe(resp => {
            this.showUpdateUserSettings = false;
            this.userService.announceUserDataChange({status: 'success', isNew: false});

            this.currentUser.email = resp.email;
            this.currentUser.firstName = resp.firstName;
            this.currentUser.lastName = resp.lastName;
            // this.currentUser.mobilePhone = resp.mobilePhone;
            // this.currentUser.otherLanguages = resp.otherLanguages;
            // this.currentUser.address = resp.addressProf;
            // this.currentUser.postalCode = resp.postalCodeProf;
            // this.currentUser.city = resp.city;
            // this.currentUser.placeOfBirthCity = resp.placeOfBirthCity;
            // this.currentUser.placeOfBirthCountry = resp.placeOfBirthCountry;
            // this.currentUser.country = resp.country;
            // this.currentUser.language = resp.language;
            // this.currentUser.origin = resp.origin;
            // this.currentUser.citizenship = resp.citizenship;
            // this.currentUser.birthDate = resp.birthDate;
        }, err => {
            alert(err && err.message ? err.message : 'Some error occurred');
        });
    }

    private getFormValue(key): String {
        return this.userForm.get(key).value;
    }

    public getSupport() {
        if (this.currentUser == null) {
            return '';
        }

        let text = '';
        switch (this.currentUser.support) {
            case 'debt-recovery':
                text = 'Debt recovery';
                break;
            case 'communication-website':
                text = 'Communication / Website';
                break;
            case 'accounting':
                text = 'Accounting';
                break;
            case 'it':
                text = 'IT';
                break;
            default:
                text = 'Reception';
                break;
        }

        return text;
    }


    public getSupportPromoters() {
        if (this.currentUser == null) {
            return '';
        }

        let text: String = '';
        switch (this.currentUser.supportPromoters) {
            case 'business-plan':
                text = 'Business plan';
                break;
            case 'accounting':
                text = 'Accounting';
                break;
            case 'administration':
                text = 'Administration';
                break;
            default:
                text = this.currentUser.supportPromoters;
                break;
        }

        return text;
    }

    addPhoto() {
        this.userPhotoModal.openModal();
    }

    onImageUpload(data) {
        this.avatarImgUrl = `${AppSettings.API_ENDPOINT}users/${data.data.id}/photo?key=${Math.random()}`;
        this.userService.emitAvatarChange(data);
    }

}


