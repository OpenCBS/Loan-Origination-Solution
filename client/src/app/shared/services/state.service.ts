
import {delay} from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { ApiService } from './api.service';

import { Observable } from 'rxjs';

@Injectable()
export class StateService {

    constructor(private apiService: ApiService) { }

    getStateFieldsMeta(id): Observable < any > {
        return this.apiService.get(`states/${id}/fields`).pipe(delay(200));
    }
}