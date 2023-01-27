/**
 * Created by Chyngyz on 3/9/2017.
 */
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { DatePipe } from '@angular/common';


@Component({
    selector: 'cbs-datepicker',
    templateUrl: 'datepicker.component.html',
    styleUrls: ['datepicker.component.scss'],
    providers: [DatePipe]
})
export class DatepickerComponent implements OnInit {
    @Input() value: string;
    @Input() dateFormat = 'yyyy-MM-dd';
    @Input() enableDropdownPicker = true;
    @Input() showIcon = true;
    @Output() valueChange = new EventEmitter();
    @Output() valueInvalid = new EventEmitter();
    public openNglDatepicker = false;
    public DATE_MODEL: Date;


    constructor(private datePipe: DatePipe) {
    }

    ngOnInit() {
        if (this.value) {
            this.DATE_MODEL = new Date(this.value);
        } else {
            this.DATE_MODEL = new Date();
        }
    }


    emitValueChange(date) {
        if (date) {
            let dateVal = date.split('-');
            let year = +dateVal[0];
            let month = +dateVal[1];
            let day = +dateVal[2];
            if (year > 1900 && year < 2100 && month <= 12 && month > 0 && day <= 31 && day > 0) {
                this.assignDate(date);
            } else {
                this.valueInvalid.emit();
            }
        }
    }

    resetValue() {
        this.value = '';
        this.DATE_MODEL = new Date();
    }

    assignDate(date) {
        this.value = this.datePipe.transform(new Date(date), this.dateFormat);
        this.DATE_MODEL = new Date(date);
        this.valueChange.emit(this.value);
    }
}
