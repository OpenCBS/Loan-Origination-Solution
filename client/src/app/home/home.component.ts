
import {map} from 'rxjs/operators';
import { Component, OnInit } from '@angular/core';
import { Router, NavigationExtras, ActivatedRoute } from '@angular/router';

import {
  ApplicationService,
  WorkflowService,
  ColorService
} from '../shared';

@Component({
  selector: 'cbs-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  public isLoading = true;
  public applicationsData: Object[] = [];
  public noData = true;

  public searchQuery = '';
  public queryObject: any = {};

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private applicationService: ApplicationService,
    private colorService: ColorService) { }

  ngOnInit() {
      this.route
        .queryParams.pipe(
        map(params => params || null))
        .subscribe((obj: any) => {
        // This is a hack for cloning object without the getters and setters,
        // here the obj keys are non-writable and non-configurable
        this.queryObject = JSON.parse(JSON.stringify(obj));
        this.populate();
    });
  }

  clearSearch() {
    this.changeSearchQueryParams();
  }

  search(query) {
    this.changeSearchQueryParams(query);
  }

  changeSearchQueryParams(query?) {
    this.queryObject.query = query || '';

    let navigationExtras: NavigationExtras = {
      queryParams: this.queryObject.query ? this.queryObject : {}
    };

    this.router.navigate(['/tasks'], navigationExtras);
  }

  populate() {
    (this.queryObject['query']
      ?  this.applicationService.getApplicationTasks(this.queryObject.query)
      : this.applicationService.getApplicationTasks()
    ).subscribe(
      resp => {
        this.isLoading = false;
        if (resp.data.length) {
          this.applicationsData = resp.data;
          this.noData = false;
        } else {
          this.noData = true;
        }
      },
      err => {
        this.noData = true;
        console.log(err);
      }
    );
  }

  gotoDetails(appId) {
    this.router.navigate([`tasks/${appId}`]);
  }


  getStateColor(id) {
    return this.colorService.getColor(id);
  }
}
