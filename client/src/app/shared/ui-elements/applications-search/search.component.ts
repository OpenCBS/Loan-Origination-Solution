import { Component, OnInit, OnChanges, Input, Output, EventEmitter, SimpleChanges } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';

import { SearchQuery } from '../../models';

import { UserService } from '../../services';

@Component({
    selector: 'cbs-application-search',
    templateUrl: 'search.component.html',
    styleUrls: ['./search.component.scss']
})
export class ApplicationsSearchComponent implements OnInit, OnChanges {
    @Input() searchQuery: any = {};
    @Output() fireSearch = new EventEmitter();

    public profiles: Object[] = [];
    public users: Object[] = [];
    public searchForm: FormGroup;
    public startCreatedDate: string;
    public endCreatedDate: string;

    constructor(
        private userService: UserService
    ) { }

    ngOnInit() {

        this.userService.getUsers().subscribe(
            resp => {
                this.users = resp.content;
            },
            err => {
                console.log(err);
            }
        );

        this.buildForm(this.searchQuery);
    }

    ngOnChanges(changes: SimpleChanges) {
        if (changes['searchQuery']) {
            this.buildForm(changes['searchQuery']['currentValue']);
        }
    }


    buildForm(queryObj: SearchQuery) {
        this.searchForm = new FormGroup({
            name: new FormControl(queryObj.name ? queryObj.name : ''),
            stateName: new FormControl(queryObj.stateName ? queryObj.stateName : ''),
            completed: new FormControl(queryObj.completed ? queryObj.completed : ''),
            profileName: new FormControl(queryObj.profileName ? queryObj.profileName : ''),
            createdUserId: new FormControl(queryObj.createdUserId ? queryObj.createdUserId : ''),
            responsibleUserId: new FormControl(queryObj.responsibleUserId ? queryObj.responsibleUserId : ''),
            stateResponsibleUserId: new FormControl(queryObj.stateResponsibleUserId ? queryObj.stateResponsibleUserId : ''),
            startCreatedDate: new FormControl(queryObj.startCreatedDate ? queryObj.startCreatedDate : ''),
            endCreatedDate: new FormControl(queryObj.endCreatedDate ? queryObj.endCreatedDate : '')
        });

        this.startCreatedDate = queryObj.startCreatedDate ? queryObj.startCreatedDate : '';
        this.endCreatedDate = queryObj.endCreatedDate ? queryObj.endCreatedDate : '';
    }

    submit(form) {
        this.fireSearch.emit(form.value);
    }

    reset(e) {
        e.preventDefault();
        e.stopPropagation();
        this.buildForm(new SearchQuery());
        this.fireSearch.emit(null);
    }

    dateInvalid(name) {
        this.searchForm.controls[name].setErrors({
            'invalid-date': true
        });
    }

    setDate(date, name) {
        if (date) {
            this.searchForm.controls[name].setValue(date);
        } else {
            this.searchForm.controls[name].setValue('');
        }
    }

}

