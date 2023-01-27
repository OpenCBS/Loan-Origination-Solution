
import {map} from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';




import { ApiService } from './api.service';


@Injectable()
export class WorkflowService {

    constructor(
        private apiService: ApiService
    ) {}

    getWorkflows(page?: number): Observable < any > {
        let url: string;
        page ? url = `workflows?page=${--page}` : url = 'workflows';
        return this.apiService.get(url).pipe(
                map(resp => resp.data));
    }

    getWorkflowFieldSections(id): Observable < any > {
        return this.apiService.get(`workflows/${id}/field-sections`).pipe(
            // .delay(1000)
            map(resp => resp.data));
    }

    getWorkflowStates(id): Observable < any > {
        return this.apiService.get(`workflows/${id}/states`).pipe(
            map(resp => {
                return resp.data;
            }));
    }
}


