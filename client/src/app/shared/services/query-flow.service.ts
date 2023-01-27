import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable()
export class QueryFlowService {
    private queryDataSource = new Subject<any>();

    queryDataChanged$ = this.queryDataSource.asObservable();

    announceDataChange(obj: any) {
        this.queryDataSource.next(obj);
    }
}
