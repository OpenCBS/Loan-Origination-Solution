
import {distinctUntilChanged, debounceTime, map} from 'rxjs/operators';
/**
 * Created by Chyngyz on 2/24/2017.
 */
import {Component, OnInit, Input, Output, EventEmitter, ViewChild, ElementRef, Renderer2} from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

interface LookupResponse {
    content: any[];
    first: boolean;
    last: boolean;
    number: number;
    numberOfElements: number;
    size: number;
    sort: any;
    totalElements: number;
    totalPages: number;
}

@Component({
    selector: 'cbs-picklist',
    templateUrl: 'picklist.component.html',
    styleUrls: ['picklist.component.scss']
})
export class PicklistComponent implements OnInit {
    @Input() config: { url, scope };
    @Input() value: string;
    @Input() filterType: string;
    @Input() searchPlaceholder = 'Search';
    @Input() selectPlaceholder = 'Select';
    @Output() onSelect = new EventEmitter();
    @ViewChild('searchInput', {static: false}) searchInput: ElementRef;
    @ViewChild('scrollBlock', {static: false}) scrollBlock: ElementRef;
    public noData = false;
    public open = false;
    public lookupList: any[] = [];
    public searchQuery = '';
    public loading = true;

    private currentPage;
    private searchQueryChanged: Subject<string> = new Subject<string>();

    private picklistData: LookupResponse;

    constructor(
        private http: HttpClient,
        private renderer: Renderer2
    ) {
    }

    ngOnInit() {
        if (this.config && this.config.url) {
            this.getData(this.config.url);
        }

        this.searchQueryChanged.pipe(
            debounceTime(300),
            distinctUntilChanged(),)
            .subscribe(searchString => {
                this.searchQuery = searchString;
                this.lookupList = [];
                this.getData(this.config.url, 0, searchString);
            });
    }

    getData(url: string, page = 0, searchString?: string) {
        this.loading = true;
        let lookupUrl;
        if (searchString) {
            lookupUrl = `${url}?search=${searchString}&page=${page}`;
        } else {
            lookupUrl = `${url}?page=${page}`;
        }

        this.getLookupData(lookupUrl).subscribe(
            (resp: LookupResponse) => {
                this.loading = false;
                if (this.config.scope) {
                    resp = resp[this.config.scope];
                }
                if (resp && resp.content) {
                    this.picklistData = Object.assign({}, resp);
                    this.currentPage = resp.number;
                    this.lookupList = [...this.lookupList, ...resp.content];

                    if (this.value && this.value.length) {
                        this.assignSelected(this.value);
                    } else {
                        this.assignSelected();
                    }
                } else {
                    this.noData = true;
                }
            },
            err => {
                console.warn( 'Error getting lookup data: ', err);
                this.noData = true;
            }
        );
    }

    assignSelected(value?) {
        this.lookupList.map(item => {
            if (item.name === value) {
                item['selected'] = true;
            } else {
                item['selected'] = false;
            }
        });
    }

    getLookupData(url): Observable<any> {
        return this.http.get(`${url}`,
            {headers: this.getHeaders()});
    }

    remove() {
        this.value = '';
        this.lookupList.map(item => {
            item.selected = false;
        });
        this.onSelect.emit();
        this.clear();
    }

    removeWithoutEmit() {
        this.value = '';
        this.lookupList.map(item => {
            item.selected = false;
        });
        this.clear();
    }

    clear() {
        this.searchQuery = '';
        this.lookupList = [];
        this.getData(this.config.url, 0);
    }

    select(item) {
        this.assignSelected(item.name);
        this.value = item.name;
        this.onSelect.emit(item);
        this.open = false;
    }

    focusInput(dropdownOpenState) {
        if (dropdownOpenState) {
            setTimeout(() => {
                // this.renderer.invokeElementMethod(this.searchInput.nativeElement, 'focus');
                this.scrollBlock.nativeElement.scrollTop = 0;
            });
        }
    }

    search(value) {
        this.searchQueryChanged.next(value);
    }

    private setHeaders(token?): HttpHeaders {
        let headersConfig = {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        };
        if (token) {
            headersConfig['Authorization'] = `Bearer ${token}`;
        }
        return new HttpHeaders(headersConfig);
    }

    public getHeaders() {
        const token = window.localStorage.getItem('token');
        return this.setHeaders(token);
    }

    onScroll() {
        const page = this.currentPage + 1;
        if (page < this.picklistData.totalPages) {
            if (this.searchQuery) {
                this.getData(this.config.url, page, this.searchQuery);
            } else {
                this.getData(this.config.url, page);
            }

        }
    }

}
