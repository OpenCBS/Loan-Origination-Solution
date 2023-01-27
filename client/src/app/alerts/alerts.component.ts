
import {map} from 'rxjs/operators';
import { Component, OnInit } from '@angular/core';
import { Alert } from '../shared/models/alert.model';
import { ActivatedRoute, NavigationExtras, Router } from '@angular/router';
import { AlertsService } from '../shared/services/alerts.service';

@Component({
    selector: 'cbs-alerts',
    templateUrl: 'alerts.component.html',
    styleUrls: ['./alerts.component.scss']
})
export class AlertsComponent  implements OnInit {
    public alertsData: [Alert] = [new Alert()];
    public queryPageObject: any;
    public queryObject: any = {};
    public pagerData: any = {};
    public currentPage = 0;
    public isLoading = true;
    public noData = true;
    public sortDirection = 1;
    public propertyName = 'id';

    public searchQuery = '';
    constructor(private route: ActivatedRoute,
                private router: Router,
                private alertsService: AlertsService) {

    }

    ngOnInit() {
        this.queryPageObject = this.route
            .queryParams.pipe(
            map(params => params || null));
        this.queryPageObject.subscribe((obj: any) => {
            // This is a hack for cloning object without the getters and setters,
            // here the obj keys are non-writable and non-configurable
            this.queryObject = JSON.parse(JSON.stringify(obj));

            this.populate(this.queryObject);
        });
    }

    populate(obj: Object) {
        this.alertsService.getAlerts(obj).subscribe(
            data => {
                if (data.content.length) {
                    this.noData = false;
                    this.isLoading = false;
                    this.alertsData = data.content;

                    this.pagerData.size = data.size;
                    this.pagerData.total = data.totalElements;
                    this.currentPage = data.number + 1;
                } else {
                    this.isLoading = false;
                    this.noData = true;
                }
            },
            err => {
                this.isLoading = false;
                this.noData = true;
            }
        );
    }

    gotoPage(page: number) {
        this.queryObject.page = page;
        this.isLoading = true;

        let navigationExtras: NavigationExtras = {
            queryParams: this.queryObject
        };
        this.router.navigate(['/alerts'], navigationExtras);
    }

    refreshCurrentRoute() {
        this.populate(this.queryObject);
    }

    clearSearch() {
        this.changeSearchQueryParams();
    }

    search(query) {
        this.changeSearchQueryParams(query);
    }

    changeSearchQueryParams(query?) {
        this.queryObject.query = query || '';
        this.queryObject.page = 1;

        let navigationExtras: NavigationExtras = {
            queryParams: this.queryObject.query ? this.queryObject : {}
        };

        this.router.navigate(['/alerts'], navigationExtras);
    }

}
