
import {distinctUntilChanged, debounceTime} from 'rxjs/operators';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Subject } from 'rxjs';

@Component({
    selector: 'cbs-search-input',
    templateUrl: 'search-input.component.html',
    styleUrls: ['search-input.component.scss']
})
export class SearchInputComponent implements OnInit {
    @Input() searchQuery = '';
    @Input() placeholder = '';
    @Output() onSearch = new EventEmitter();
    @Output() onClear = new EventEmitter();

    private searchQueryChanged: Subject<string> = new Subject<string>();


    constructor() {
        this.searchQueryChanged.pipe(
            debounceTime(300),
            distinctUntilChanged(),)
            .subscribe(model => {
                this.searchQuery = model;
                this.search(model);
            });
    }

    ngOnInit() {}

    changed(searchQuery: string) {
        this.searchQueryChanged.next(searchQuery);
    }

    clear() {
        this.searchQuery = '';
        this.onClear.emit();
    }

    search(searchQuery: string) {
        this.onSearch.emit(searchQuery);
    }
}
