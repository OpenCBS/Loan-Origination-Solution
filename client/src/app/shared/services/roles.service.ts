
import {map, delay} from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { ApiService } from './api.service';

import { Subject ,  Observable } from 'rxjs';

@Injectable()
export class RolesService {
    private roleModalSource = new Subject< any >();
    public roleModalChanged$ = this.roleModalSource.asObservable();

    constructor(
        private apiService: ApiService
    ) {}

    announceRoleDataChange(status: any) {
        this.roleModalSource.next(status);
    }

    getRoles(role?): Observable < any > {
        return this.apiService.get('roles').pipe(delay(200),map(resp => resp.data),);
    }

    createRole(roleData): Observable < any > {
        return this.apiService.post('roles', roleData).pipe(map(resp => resp.data));
    }

    updateRole(roleData, roleId): Observable < any > {
        return this.apiService.put(`roles/${roleId}`, roleData).pipe(map(resp => resp.data));
    }

    getPermissions(): Observable < any > {
        return this.apiService.get('permissions').pipe(map(resp => resp.data));
    }
}


