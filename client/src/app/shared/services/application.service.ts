
import {map, delay} from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';

import { ApiService } from './api.service';


@Injectable()
export class ApplicationService {
    private applicationFieldSource = new Subject<any>();

    public applicationFieldChanged$ = this.applicationFieldSource.asObservable();


    constructor(
        private apiService: ApiService,
        private http: HttpClient
    ) {}

    announceFieldChange(obj: any) {
        this.applicationFieldSource.next(obj);
    }

    createApplication(data): Observable < any > {
        return this.apiService.post(`applications`, data).pipe(delay(200));
    }

    updateApplication(id, data): Observable < any > {
      return this.apiService.put(`applications/${id}`, data).pipe(delay(200));
    }

    getApplicationTasks(query?: string): Observable < any > {
      let param = query ? '?query=' + query : '';
      return this.apiService.get('applications/tasks' + param).pipe(delay(200));
    }

    deleteApplication(id, comment): Observable < any > {
        return this.apiService.delete(`applications/${id}`, comment);
    }

    getAllApplications(query?: Object): Observable < any > {
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
        return this.apiService.get(`applications/${url.slice(0, -1)}`).pipe(delay(200));
    }

    getApplicationDetails(id): Observable < any > {
        return this.apiService.get(`applications/${id}`).pipe(delay(200));
    }

    getApplicationEvents(id): Observable < any > {
        return this.apiService.get(`applications/${id}/events`).pipe(delay(200));
    }

    getApplicationStateFields(id): Observable < any > {
        return this.apiService.get(`applications/${id}/current-state/fields`).pipe(delay(200));
    }

    postApplicationStateFields(id, data): Observable < any > {
        return this.apiService.post(`applications/${id}/current-state/fields`, data).pipe(delay(200));
    }

    getApplicationAllFields(id): Observable < any > {
        return this.apiService.get(`applications/${id}/fields`).pipe(delay(200));
    }

    postApplicationAllFields(id, data): Observable < any > {
        return this.apiService.post(`applications/${id}/fields`, data).pipe(delay(200));
    }

    postAction(id, data): Observable < any > {
        return this.apiService.post(`applications/${id}`, data).pipe(delay(200));
    }

    getApplicationLogs(id): Observable < any > {
        return this.apiService.get(`applications/${id}/logs`).pipe(delay(200));
    }

    getApplicationWorkLogs(id, force?): Observable < any > {
        return this.apiService.get(`applications/${id}/worklogs`).pipe(delay(200));
    }

    postApplicationWorkLog(id, data): Observable < any > {
        return this.apiService.post(`applications/${id}/worklogs`, data).pipe(delay(200));
    }

    updateApplicationWorkLog(id, data, workLogId): Observable < any > {
        return this.apiService.put(`applications/${id}/worklogs/${workLogId}`, data).pipe(delay(200));
    }

    getApplicationFiles(id, force?, page?): Observable < any > {
        return this.apiService.get(`applications/${id}/attachments?page=${--page}`).pipe(delay(200));
    }

    deleteApplicationFile(id): Observable < any > {
        return this.apiService.delete(`attachments/${id}`);
    }

    getApplicationStatePermissions(id): Observable < any > {
        return this.apiService.get(`applications/${id}/permissions`);
    }

    postApplicationStatePermissions(id, data): Observable < any > {
        return this.apiService.post(`applications/${id}/permissions`, data).pipe(delay(200));
    }

    exportToXls(query?: Object): Observable < any > {
        let url = '?';
        if (query) {
            for (const key in query) {
                if (key === 'page') {
                    let page = query[key];
                    url = url + key + '=' + --page + '&';
                } else {
                    url = url + key + '=' + query[key] + '&';
                }
            }
        }

        return this.apiService.getBlob(`export/applications.xls${url.slice(0, -1)}`).pipe(
            map(res => res));
    }
}
