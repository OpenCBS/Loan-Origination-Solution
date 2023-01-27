
import {map, delay} from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AlertsService {
    constructor(
        private apiService: ApiService,
        private http: HttpClient
    ) {}

    getAlerts(query?: Object): Observable < any > {
        let url = '?';
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
        return this.apiService.get(`notification-history/${url.slice(0, -1)}`).pipe(
            delay(200),
            map(
                data => {
                    return data.data;
                }
            ),);
    }
}
