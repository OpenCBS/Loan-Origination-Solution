
import {map} from 'rxjs/operators';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, NavigationExtras, Router } from '@angular/router';

import {
    UserService,
    UserFormModalComponent,
    ResetUserPasswordFormModalComponent
} from '../../shared';

import { User } from '../../shared/models';

import { AppSettings } from '../../app.settings';

@Component({
    selector: 'cbs-users',
    templateUrl: 'users.component.html'
})
export class UsersComponent implements OnInit {
    @ViewChild(UserFormModalComponent, {static: false}) private userFormModal: UserFormModalComponent;
    @ViewChild(ResetUserPasswordFormModalComponent, {static: false}) private resetUserPasswordFormModal: ResetUserPasswordFormModalComponent;

    public usersData: Object[] = [];
    public queryPageObject: any;
    public queryObject: any = {};
    public pagerData: any = {};
    public currentPage = 0;
    public isLoading = true;
    public noData = true;
    public currentUser: User;
    public searchQuery = '';

    public permissionCreateUser: string = AppSettings.PERMISSION_NAMES['CREATE_USER'];
    public permissionEditUser: string = AppSettings.PERMISSION_NAMES['EDIT_USER'];
    public permissionDeleteUser: string = AppSettings.PERMISSION_NAMES['DELETE_USER'];
    public permissionResetUsersPasswords: string = AppSettings.PERMISSION_NAMES['RESET_USER_PASSWORD'];

    constructor(
        private userService: UserService,
        private route: ActivatedRoute,
        private router: Router) { }

    ngOnInit() {
        this.currentUser = this.userService.getCurrentUser();

        this.queryPageObject = this.route
            .queryParams.pipe(
            map(params => params || null));

        this.queryPageObject.subscribe((obj: any) => {
            // This is a hack for cloning object without the getters and setters,
            // here the obj keys are non-writable and non-configurable
            this.queryObject = JSON.parse(JSON.stringify(obj));
            this.populate(this.queryObject);
            this.refreshPage();
        });
    }

    clearSearch() {
        this.changeSearchQueryParams();
    }

    search(query) {
        this.changeSearchQueryParams(query);
    }

    changeSearchQueryParams(query?) {
        this.queryObject.query = query || '';
        this.queryObject.page = 1;

        let navigationExtras: NavigationExtras = {
            queryParams: this.queryObject.query ? this.queryObject : {}
        };

        this.router.navigate(['settings', 'users'], navigationExtras);
    }

    refreshPage() {
        this.populate(this.queryObject);
    }

    populate(obj: Object) {
        this.userService.getUsers(obj).subscribe(
            data => {
                if (data.content.length) {
                    this.noData = false;
                    this.isLoading = false;
                    this.usersData = data.content;

                    this.pagerData.size = data.size;
                    this.pagerData.total = data.totalElements;
                    this.currentPage = data.number + 1;
                } else {
                    this.usersData = data.content;
                    this.isLoading = false;
                    this.noData = true;
                }
            },
            err => {
                this.isLoading = false;
                this.noData = true;
            }
        );
    }

    userPasswordReseted(data) {
        this.userService.resetUserPassword(data).subscribe(resp => {
            this.refreshPage();
             let response = {
                 status: 'success',
                 message: ''
             };
            this.resetUserPasswordFormModal.setResponce(response);

            if (this.currentUser && this.currentUser.id === data.userId) {
                this.userService.announceUserDataChange({status: 'success', isNew: false});
            }

        }, err => {
            let response = {
                status: 'error',
                message: err.message
            };


            this.resetUserPasswordFormModal.setResponce(response);
        });
    }

    saveUser(user) {
        if (user.isNew) {
            this.userService.createNewUser(user.data).subscribe(
                resp => {
                    this.userService.announceUserDataChange({status: 'success', isNew: true});
                    this.refreshPage();
                },
                err => {
                    this.userService.announceUserDataChange({status: 'error', isNew: true});
                    this.userFormModal.setErrorResponse(err);
                });
        } else {
            this.userService.updateUser(user.data, user.id).subscribe(
                resp => {
                    this.userService.announceUserDataChange({status: 'success', isNew: false});
                    this.refreshPage();
                },
                err => {
                    this.userService.announceUserDataChange({status: 'error', isNew: false});
                    this.userFormModal.setErrorResponse(err);
                });
        }
    }

    addNewUser() {
        this.userFormModal.openModal(new User(), true);
    }

    editUser(user: User) {
        this.userFormModal.openModal(user, false);
    }

    resetUserPassword(user: User) {
        this.resetUserPasswordFormModal.openModal(user);
    }

    disableUser(user: User) {
        if (confirm(`Please confirm that you want to disable user: ${user.firstName} ${user.lastName}`)) {
            let obj = this.getUserObject(user);
            obj['enabled'] = false;
            this.userService.updateUser(obj, user.id).subscribe(
                resp => {
                    this.refreshPage();
                },
                err => {
                    console.log(err);
                    alert('Unable to disable user.');
                });
        }
    }

    enableUser(user: User) {
        if (confirm(`Please confirm that you want to enable user: ${user.firstName} ${user.lastName}`)) {
            let obj = this.getUserObject(user);
            obj['enabled'] = true;
            this.userService.updateUser(obj, user.id).subscribe(
                resp => {
                    this.refreshPage();
                },
                err => {
                    console.log(err);
                    alert('Unable to disable user.');
                });
        }
    }

    getUserObject(data) {
        let obj = {};
        for (let key in data) {
            if (data.hasOwnProperty(key)) {
                if (key === 'role') {
                    obj['roleId'] = data[key]['id'];
                } else if (key !== 'id') {
                    let element = data[key];
                    obj[key] = element;
                }
            }
        }
        return obj;
    }

    gotoPage(page: number) {
        this.queryObject.page = page;
        this.isLoading = true;

        let navigationExtras: NavigationExtras = {
            queryParams: this.queryObject
        };
        this.router.navigate(['settings', 'users'], navigationExtras);
    }

    exportVolunteersToXls() {
        this.userService.exportVolunteersToXls()
            .subscribe(
                resp => {
                    let url = window.URL.createObjectURL(resp);
                    let anchor = document.createElement('a');
                    anchor.download = 'VolunteersReport.xls';
                    anchor.href = url;
                    anchor.click();
                },
                err => {
                    console.log(err);
                }
            );
    }
}

