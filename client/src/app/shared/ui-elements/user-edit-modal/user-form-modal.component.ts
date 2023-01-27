import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { User } from '../../models';

import { UserService, RolesService } from '../../services';
import { AppSettings } from '../../../app.settings';

const COUNTRIES = 'Afghanistan|Albania|Algeria|American Samoa|Andorra|Angola|Anguilla|Antarctica|Antigua and Barbuda|Argentina' +
    '|Armenia|Aruba|Australia|Austria|Azerbaijan|Bahamas|Bahrain|Bangladesh|Barbados|Belarus|Belgium|Belize|Benin|Bermuda|Bhutan' +
    '|Bolivia|Bosnia and Herzegovina|Botswana|Bouvet Island|Brazil|British Indian Ocean Territory|Brunei Darussalam|Bulgaria' +
    '|Burkina Faso|Burundi|Cambodia|Cameroon|Canada|Cape Verde|Cayman Islands|Central African Republic|Chad|Chile|China' +
    '|Christmas Island|Cocos (Keeling) Islands|Colombia|Comoros|Congo|Congo|The Democratic Republic of The|Cook Islands' +
    '|Costa Rica|Cote D’ivoire|Croatia|Cuba|Cyprus|Czech Republic|Denmark|Djibouti|Dominica|Dominican Republic|Ecuador' +
    '|Egypt|El Salvador|Equatorial Guinea|Eritrea|Estonia|Ethiopia|Falkland Islands (Malvinas)|Faroe Islands|Fiji|Finland|France' +
    '|French Guiana|French Polynesia|French Southern Territories|Gabon|Gambia|Georgia|Germany|Ghana|Gibraltar|Greece|Greenland' +
    '|Grenada|Guadeloupe|Guam|Guatemala|Guinea|Guinea-bissau|Guyana|Haiti|Heard Island and Mcdonald Islands' +
    '|Holy See (Vatican City State)|Honduras|Hong Kong|Hungary|Iceland|India|Indonesia|Iran|Islamic Republic of|Iraq|Ireland|Israel' +
    '|Italy|Jamaica|Japan|Jordan|Kazakhstan|Kenya|Kiribati|Korea|Democratic People’s Republic of|Korea|Republic of|Kuwait|Kyrgyzstan' +
    '|Lao People’s Democratic Republic|Latvia|Lebanon|Lesotho|Liberia|Libyan Arab Jamahiriya|Liechtenstein|Lithuania|Luxembourg|Macao' +
    '|Macedonia|The Former Yugoslav Republic of|Madagascar|Malawi|Malaysia|Maldives|Mali|Malta|Marshall Islands|Martinique|Mauritania' +
    '|Mauritius|Mayotte|Mexico|Micronesia|Federated States of|Moldova|Republic of|Monaco|Mongolia|Montserrat|Morocco|Mozambique|Myanmar' +
    '|Namibia|Nauru|Nepal|Netherlands|Netherlands Antilles|New Caledonia|New Zealand|Nicaragua|Niger|Nigeria|Niue|Norfolk Island' +
    '|Northern Mariana Islands|Norway|Oman|Pakistan|Palau|Palestinian Territory|Occupied|Panama|Papua New Guinea|Paraguay|Peru' +
    '|Philippines|Pitcairn|Poland|Portugal|Puerto Rico|Qatar|Reunion|Romania|Russian Federation|Rwanda|Saint Helena' +
    '|Saint Kitts and Nevis|Saint Lucia|Saint Pierre and Miquelon|Saint Vincent and The Grenadines|Samoa|San Marino' +
    '|Sao Tome and Principe|Saudi Arabia|Senegal|Serbia and Montenegro|Seychelles|Sierra Leone|Singapore|Slovakia|Slovenia' +
    '|Solomon Islands|Somalia|South Africa|South Georgia and The South Sandwich Islands|Spain|Sri Lanka|Sudan|Suriname' +
    '|Svalbard and Jan Mayen|Swaziland|Sweden|Switzerland|Syrian Arab Republic|Taiwan|Province of China|Tajikistan|Tanzania' +
    '|United Republic of|Thailand|Timor-leste|Togo|Tokelau|Tonga|Trinidad and Tobago|Tunisia|Turkey|Turkmenistan' +
    '|Turks and Caicos Islands|Tuvalu|Uganda|Ukraine|United Arab Emirates|United Kingdom|United States|' +
    'United States Minor Outlying Islands|Uruguay|Uzbekistan|Vanuatu|Venezuela|Viet Nam|Virgin Islands|British|Virgin Islands|U.S.' +
    '|Wallis and Futuna|Western Sahara|Yemen|Zambia|Zimbabwe';

@Component({
    selector: 'cbs-user-modal',
    templateUrl: 'user-form-modal.component.html',
    styleUrls: ['./user-form-modal.component.scss']
})
export class UserFormModalComponent implements OnInit {
    public user: FormGroup;
    public userData: User = new User();
    public opened = false;
    public isNewUser = false;
    public isSubmitting = false;
    public roles: Object[] = [];
    // initial modal response state
    public isResponseStatusOk = 'null';
    public countries = COUNTRIES.split('|');
    public showVolunteerExtraFields = false;
    private readonly volunteerTypeId = 3;
    public selfUpdate: boolean;
    public errorMessage: String;

    public volunteerFields = {
        phoneNumber: '',
        address: '',
        street: '',
        postalCode: '',
        currentOccupation: '',
        citizenship: '',
        spokenLanguages: '',
        preferredWorkingLanguages: '',
        specialization: '',
        availability: '',
        alreadyVolunteered: '',
        support: '',
        supportOther: '',
        supportPromoters: '',
        supportPromotersOther: '',
    };

    private userId: number;

    @Output() saveUserData = new EventEmitter();

    constructor(private userService: UserService,
                private roleService: RolesService,
                private fb: FormBuilder) {
    }

    ngOnInit() {
        this.userService.userModalChanged$.subscribe(resp => {
            this.isSubmitting = false;
            if (resp.status === 'success') {
                this.isResponseStatusOk = 'ok';
                this.clearAll();
            } else if (resp.status === 'error') {
                this.isResponseStatusOk = 'error';
                setTimeout(() => {
                    this.isResponseStatusOk = 'null';
                }, AppSettings.ERROR_DELAY);
            }
        });

        this.roleService.getRoles().subscribe(
            resp => {
                this.roles = resp.content;
            },
            err => {
                console.log(err);
            }
        );

        this.createForm(new User());
    }

    createForm(user: User) {
        this.user = new FormGroup({
            firstName: new FormControl('', [Validators.required, Validators.minLength(3)]),
            lastName: new FormControl('', [Validators.required, Validators.minLength(3)]),
            username: new FormControl('', [Validators.required, Validators.minLength(3)]),
            password: new FormControl('', [Validators.required, Validators.minLength(6)]),
            email: new FormControl('', [Validators.required, Validators
                .pattern('[a-zA-Z\\d\\_\\.+\\-]+@[a-zA-Z\\d\\-]+\\.[a-zA-Z\\d\\-\\.]+')]),
            mobilePhone: new FormControl(''),
            roleId: new FormControl('', [Validators.required]),
            enabled: new FormControl(user.enabled ? user.enabled : true)
        });
    }

    editForm(user: User) {
        this.user = new FormGroup({
            firstName: new FormControl(user.firstName ? user.firstName : '', [Validators.required, Validators.minLength(3)]),
            lastName: new FormControl(user.lastName ? user.lastName : '', [Validators.required, Validators.minLength(3)]),
            username: new FormControl(user.username ? user.username : '', [Validators.required, Validators.minLength(3)]),
            email: new FormControl(user.email ? user.email : '', [Validators.required]),
            mobilePhone: new FormControl(user.mobilePhone ? user.mobilePhone : ''),
            roleId: new FormControl(user.role ? user.role.id : '', [Validators.required]),
            enabled: new FormControl(user.enabled ? user.enabled : false)
        });
    }

    additionalFields(addBool, formControlName?, value?) {
        if (addBool) {
            this.user.addControl(formControlName, this.fb.control(value ? value : ''));
        } else {
            this.user.removeControl(formControlName);
        }
    }

    getTitle() {
        if (this.isNewUser) {
            return 'Add New User';
        }

        if (this.selfUpdate) {
            return 'Update My Settings';
        }

        return 'Edit User';
    }

    getSaveButtonText() {
        if (this.isNewUser) {
            return 'Create';
        }

        if (this.selfUpdate) {
            return 'Update';
        }


        return 'Update user';
    }

    openModal(user: User, isCreateMode: boolean, selfUpdate?: boolean) {
        if (!isCreateMode) {
            this.editForm(user);
            let extraFields = Object.assign({}, this.volunteerFields);
            for (let key in extraFields) {
                if (extraFields.hasOwnProperty(key)) {
                    extraFields[key] = user[key] ? user[key] : '';
                }
            }

            if (user.role && user.role.id === this.volunteerTypeId) {
                this.checkRoleType(3, extraFields);
            } else {
                this.checkRoleType(null, extraFields);
            }

            this.selfUpdate = selfUpdate;

        } else {
            this.createForm(new User());
            this.checkRoleType(null, this.volunteerFields);
        }

        this.opened = true;
        this.userData = user;
        this.isNewUser = isCreateMode;
        this.userId = user.id;
    }

    checkRoleType(value, dataFields) {
        if (+value === this.volunteerTypeId) {
            for (let key in dataFields) {
                if (dataFields.hasOwnProperty(key)) {
                    this.additionalFields(true, key, dataFields[key]);
                }
            }
            this.showVolunteerExtraFields = true;
        } else if (this.showVolunteerExtraFields) {
            this.showVolunteerExtraFields = false;
            for (let key in dataFields) {
                if (dataFields.hasOwnProperty(key)) {
                    this.additionalFields(false, key);
                }
            }
        }
    }

    clearAll() {
        setTimeout(() => {
            this.isResponseStatusOk = 'null';
            this.opened = false;
        }, AppSettings.SUCCESS_DELAY);
        this.userData = new User();
        if (this.showVolunteerExtraFields) {
            this.checkRoleType(null, this.volunteerFields);
        }
    }

    cancel() {
        this.opened = false;
        this.errorMessage = '';
    }

    submitForm({value, valid}) {
        let obj: Object = {};
        if (valid) {
            obj['data'] = value;
            obj['isNew'] = this.isNewUser;
            obj['id'] = this.userId;
            this.saveUserData.emit(obj);
            this.isSubmitting = true;
        }
    }

    setErrorResponse(err) {
        this.errorMessage = err.message;
    }
}
