
import {map} from 'rxjs/operators';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { WorkflowService } from '../../shared';

@Component({
    selector: 'cbs-workflows',
    templateUrl: 'workflows.component.html'
})
export class WorkflowsComponent implements OnInit {
    public workflowsData: Object[] = [];
    public queryPageObject: any;
    public queryObject: any = {};
    public pagerData: any = {};
    public currentPage = 0;
    public isLoading = true;
    public noData = true;

    constructor(
        private route: ActivatedRoute,
        private workflowService: WorkflowService
    ) { }

    ngOnInit() {
        this.queryPageObject = this.route
            .queryParams.pipe(
            map(params => params || null));
        this.queryPageObject.subscribe((obj: any) => {
            // This is a hack for cloning object without the getters and setters,
            // here the obj keys are non-writable and non-configurable
            this.queryObject = JSON.parse(JSON.stringify(obj));

            this.refreshPage();
        });
    }

    refreshPage() {
        if (this.queryObject.hasOwnProperty('page')) {
            this.populate(+this.queryObject.page);
        } else {
            this.populate(1);
        }
    }

    populate(page: number) {
        this.workflowService.getWorkflows(page).subscribe(
            resp => {
                if (resp.content.length) {
                    this.noData = false;
                    this.isLoading = false;
                    this.workflowsData = resp.content;

                    this.pagerData.size = resp.size;
                    this.pagerData.total = resp.totalElements;
                    this.currentPage = resp.number + 1;
                } else {
                    console.log('No data');
                    this.isLoading = false;
                    this.noData = true;
                }
            },
            err => {
                console.log(err);
            }
        );
    }

    gotoPage() {

    }
}