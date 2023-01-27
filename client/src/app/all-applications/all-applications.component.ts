
import {map} from 'rxjs/operators';
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, NavigationExtras } from '@angular/router';

import { ApplicationService, ColorService } from '../shared';
import * as moment from 'moment';
import * as FileSaver from 'file-saver';

@Component({
    selector: 'cbs-all-applications',
    templateUrl: 'all-applications.component.html',
    styleUrls: ['./all-applications.component.scss']
})
export class AllApplicationsComponent implements OnInit {

    public applicationsData: Object[] = [];
    public queryPageObject;
    public queryObject: any = {};
    public pagerData: any = {};
    public currentPage = 0;
    public isLoading = true;
    public noData = true;
    public sortDirection = 1;
    public noBodyPaddings = false;
    public propertyName = 'id';
    public currentTotal: number;

    constructor(private router: Router,
                private route: ActivatedRoute,
                private applicationService: ApplicationService,
                private colorService: ColorService) {
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

    populate(query?: Object) {
        this.isLoading = true;
        this.applicationService.getAllApplications(query).subscribe(
            resp => {
                this.isLoading = false;

                this.pagerData.size = resp.data.size;
                this.pagerData.total = resp.data.totalElements;
                this.currentPage = resp.data.number + 1;
                this.currentTotal = resp.data.number * this.pagerData.size + resp.data.numberOfElements;

                if (resp.data.content.length) {
                    this.applicationsData = resp.data.content;
                    this.noData = false;
                } else {
                    this.noData = true;
                }
            },
            err => {
                this.isLoading = false;

                this.noData = true;
                console.log(err);
            }
        );
    }

    gotoPage(page: number) {
        this.queryObject.page = page;
        let navigationExtras: NavigationExtras = {
            queryParams: this.queryObject
        };
        this.router.navigate(['/applications'], navigationExtras);
    }

    refreshPage(query?: Object) {
        let navigationExtras: NavigationExtras;
        if (query) {
            navigationExtras = {
                queryParams: query
            };
        } else {
            navigationExtras = {
                queryParams: {
                    page: 1
                }
            };
        }
        this.router.navigate(['/applications'], navigationExtras);
    }

    gotoDetails(appId) {
        this.router.navigate([`applications/${appId}`]);
    }

    formatQuery(data) {
        this.queryObject = {};
        for (let key in data) {
            if (data[key]) {
                this.queryObject[key] = data[key];
            }
        }
    }

    search(query) {
        if (query) {
            this.formatQuery(query);
            this.refreshPage(this.queryObject);
        } else {
            this.refreshPage();
        }
    }

    getStateColor(id) {
        return this.colorService.getColor(id);
    }

    exportToXls() {
        this.applicationService.exportToXls(this.queryObject)
            .subscribe(
                resp => {
                  FileSaver.saveAs(resp, `Applications${ moment(new Date()).format('YYYYMMDDHHmm')}.xls`);
                },
                err => {
                    console.log(err);
                }
            );
    }
}
