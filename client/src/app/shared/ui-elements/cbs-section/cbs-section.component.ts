import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'cbs-section',
  templateUrl: 'cbs-section.component.html'
})
export class CbsSectionComponent implements OnInit {
  public open = true;
  @Input() title: string;

  constructor() { }

  ngOnInit(): void {
  }
}
