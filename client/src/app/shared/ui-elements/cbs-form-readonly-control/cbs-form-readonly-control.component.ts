import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'cbs-form-readonly-control',
  templateUrl: './cbs-form-readonly-control.component.html',
  styleUrls: ['./cbs-form-readonly-control.component.scss']
})
export class CbsFormReadonlyControlComponent implements OnInit {

  @Input() fieldLabel: string;
  @Input() value: any;
  @Input() public fieldData: any;
  @Input() disabled = true;
  @Input() styleClass: any;
  @Input() style: any;
  @Input() showLink: boolean;
  @Input() isTextarea = false;
  @Input() isGrid = false;
  @Input() isMultiSelect = false;
  @Output() fieldClicked = new EventEmitter();
  public gridHeaders = [];
  public gridValues = [];
  public multiSelectValues = [];
  public gridTotal: number;

  ngOnInit() {
    if (this.isGrid) {
      this.gridValues = this.fieldData.value ? JSON.parse(this.fieldData.value).data : JSON.parse(this.fieldData.field.extra).data;

      for (let i in this.gridValues[0]) {
        this.gridHeaders.push({
          value: i
        });
      }
      this.calculateTotal();
    }

    if (this.isMultiSelect && this.fieldData.value) {
      let value = JSON.parse(this.fieldData.value);
      value.map(val => {
        this.multiSelectValues.push(val.value);
      })
    }
  }

  calculateTotal() {
    let total = 0;
    for(let val of this.gridValues) {
      if (val.value) {
        total += parseInt(val.value);
      }
    }
    this.gridTotal = total;
  }
}
