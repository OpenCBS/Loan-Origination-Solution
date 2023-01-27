import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
@Injectable()
export class ApplicationModalService {
    private applicationModalSource = new Subject< any >();
    private applicationModalStateSource = new Subject< any >();
    applicationModalChanged$ = this.applicationModalSource.asObservable();
    applicationModalStateChanged$ = this.applicationModalStateSource.asObservable();

    announceDataChange(status: any) {
        this.applicationModalSource.next(status);
    }

    announceStateChange(status: any) {
        this.applicationModalStateSource.next(status);
    }
}
