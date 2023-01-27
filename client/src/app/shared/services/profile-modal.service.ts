import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable()
export class ProfileModalService {
    private profileModalSource = new Subject< any >();
    profileModalChanged$ = this.profileModalSource.asObservable();
    announceDataChange(status: any) {
        this.profileModalSource.next(status);
    }
}
