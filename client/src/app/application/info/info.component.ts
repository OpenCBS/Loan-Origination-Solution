import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ApplicationService } from '../../shared';

@Component({
    selector: 'cbs-application-info',
    templateUrl: 'info.component.html'
})
export class ApplicationInfoComponent implements OnInit, OnDestroy {
    public applicationData: Object[];
    public isLoading = false;
    public errorOnApplicationData = false;

    private sub: any;
    private id: number;


    constructor(private route: ActivatedRoute,
                private applicationService: ApplicationService) {
    }

    ngOnInit() {
        this.sub = this.route.parent.params.subscribe(params => {
            this.id = +params['id'];
            this.getApplicationInfo(this.id);
        });
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }

    getApplicationInfo(id) {
        this.isLoading = true;
        this.applicationService.getApplicationDetails(id).subscribe(
            resp => {
                this.applicationData = resp.data;
                this.isLoading = false;
                this.errorOnApplicationData = false;
            },
            err => {
                this.isLoading = false;
                this.errorOnApplicationData = true;
                console.log(err);
            }
        );
    }
}
