import { Component, OnInit, Input } from '@angular/core';

@Component({
    selector: 'cbs-loading-indicator',
    template: `
        <div class="" *ngIf="showLoader">
            <div class="slds-spinner_container">
                <div class="slds-spinner--brand slds-spinner slds-spinner--small" role="alert">
                    <span class="slds-assistive-text">Loading</span>
                    <div class="slds-spinner__dot-a"></div>
                    <div class="slds-spinner__dot-b"></div>
                </div>
            </div>
        </div>
    `
})
export class LoadingIndicatorComponent implements OnInit {
    @Input() showLoader = false;
    constructor() { }

    ngOnInit() { }
}