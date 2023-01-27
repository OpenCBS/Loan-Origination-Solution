import { Component, OnInit, OnDestroy } from '@angular/core';
import { ApplicationService } from '../../shared';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'cbs-application-activity',
    templateUrl: 'activity.component.html'
})
export class ApplicationActivityComponent implements OnInit, OnDestroy {
    public sub: any;
    public isLoadingLogs = false;
    public applicationLogs: any;
    public id: number;
    public noData = true;

    constructor(
        private applicationService: ApplicationService,
        private route: ActivatedRoute) { }

    ngOnInit() {
        this.sub = this.route.parent.params.subscribe(params => {
            this.id = +params['id'];
            this.getApplicationLogs(this.id);
        });
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    getApplicationLogs(id) {
        this.isLoadingLogs = true;
        this.applicationService.getApplicationLogs(id).subscribe(
            resp => {
                this.isLoadingLogs = false;
                this.applicationLogs = resp.data;
                this.applicationLogs.length ? this.noData = false : this.noData = true;
            },
            err => {
                this.isLoadingLogs = false;
                this.noData = true;
            }
        );
    }
}

