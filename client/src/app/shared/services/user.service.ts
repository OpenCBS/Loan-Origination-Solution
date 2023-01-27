import {delay, map, distinctUntilChanged} from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable ,  BehaviorSubject ,  ReplaySubject ,  Subject } from 'rxjs';
import { ApiService } from './api.service';
import { JwtService } from './jwt.service';
import { User } from '../models';


@Injectable()
export class UserService {
    private userModalSource = new Subject<any>();
    private avatarChangeSource = new Subject<any>();

    private currentUserSubject = new BehaviorSubject<User>(new User());
    private isAuthenticatedSubject = new ReplaySubject<boolean>(1);

    public userModalChanged$ = this.userModalSource.asObservable();
    public avatarChange = this.avatarChangeSource.asObservable();

    public currentUser = this.currentUserSubject.asObservable().pipe(distinctUntilChanged());
    public isAuthenticated = this.isAuthenticatedSubject.asObservable();

    constructor(
        private apiService: ApiService,
        private http: HttpClient,
        private jwtService: JwtService
    ) {}

    // Verify JWT in localstorage with server & load user's info.
    populate() {
        // If JWT detected, attempt to get & store user's info
        if (this.jwtService.getToken()) {
            this.apiService.get('users/current')
                .subscribe(
                    data => {
                        // Set current user data into observable
                        this.currentUserSubject.next(data.data);
                        // Set isAuthenticated to true
                        this.isAuthenticatedSubject.next(true);
                    },
                    err => this.purgeAuth()
                );
        } else {
            // Remove any potential remnants of previous auth states
            this.purgeAuth();
        }
    }

    announceUserDataChange(status: any) {
        this.userModalSource.next(status);
    }

    setAuth(token: string) {
        // Set isAuthenticated to true
        this.isAuthenticatedSubject.next(true);
        // Save JWT sent from server in localstorage
        this.jwtService.saveToken(token);
        this.populate();
    }

    purgeAuth() {
        // Remove JWT from localstorage
        this.jwtService.destroyToken();
        // Set current user to an empty object
        this.currentUserSubject.next(new User());
        // Set auth status to false
        this.isAuthenticatedSubject.next(false);
    }

    attemptAuth(credentials): Observable<User> {
        return this.apiService.post('auth', credentials).pipe(
            map(
                data => {
                    this.setAuth(data.data);
                    return data;
                }
            ));
    }

    requestPasswordRequestToken(data) {
        return this.apiService.put('users/resetpasswordtoken', data).pipe(delay(200),map(data => data.data),);
    }

    getCurrentUser(): User {
        return this.currentUserSubject.value;
    }

    getUsers(query?: Object): Observable < any > {
        let url = 'search?';
        if (query) {
            for (let key in query) {
                if (key === 'page') {
                    let page = query[key];
                    url = url + key + '=' + --page + '&';
                } else {
                    url = url + key + '=' + query[key] + '&';
                }
            }
        }
        return this.apiService.get(`users/${url.slice(0, -1)}`).pipe(
            delay(200),
            map(
                data => {
                    return data.data;
                }
            ),);
    }

    getAllUsers() {
        let url: string;
        url = 'users/all';
        return this.apiService.get(url).pipe(
            map(data => data.data));
    }

    createNewUser(userData) {
        return this.apiService.post('users', userData).pipe(
            delay(200));
    }

    resetUserPassword(data) {
        return this.apiService.put(`users/resetpassword`, data).pipe(delay(200));
    }

    updateUser(userData, userId) {
        return this.apiService.put(`users/${userId}`, userData).pipe(
            delay(200));
    }

    changeCurrentUserPassword(updatePasswordRequest) {
        return this.apiService.put('users/current/changepassword', updatePasswordRequest).pipe(delay(200));
    }


    updateCurrentUser(updateUser) {
        return this.apiService.put('users/current/', updateUser).pipe(delay(200),map(data => data.data),);
    }

    validateToken(token) {
        return this.apiService.post('users/validateresetpasswordtoken', {data: token}).pipe(
            delay(200));
    }

    resetUserPasswordByToken(data) {
        return this.apiService.put(`users/resetpasswordbytoken`, data).pipe(delay(200));
    }

    emitAvatarChange(data) {
        this.avatarChangeSource.next(data);
    }

    exportVolunteersToXls(): Observable < any > {
        return this.apiService.getBlob('export/volunteers.xls').pipe(
            map(res => new Blob([res._body], {type: 'application/vnd.ms-excel'})));
    }
}
