import { Component, OnInit, AfterContentChecked } from '@angular/core';
import { Router } from '@angular/router';
import {
    UserService,
    AppReadyEvent
} from './shared';

import { ErrorLogService } from './shared/services/error-handler';

import '../assets/style/app.scss';

@Component({
    selector: 'cbs-app',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit, AfterContentChecked {
    constructor(
        private userService: UserService,
        private appReadyEvent: AppReadyEvent,
        private errorLogService: ErrorLogService,
        private router: Router) {}

    ngOnInit() {
        this.userService.populate();

        this.errorLogService.errorOccured$.subscribe(
            err => {
                this.router.navigateByUrl('/server-error');
            }
        );
    }

    ngAfterContentChecked() {
        this.appReadyEvent.trigger();
    }
}
