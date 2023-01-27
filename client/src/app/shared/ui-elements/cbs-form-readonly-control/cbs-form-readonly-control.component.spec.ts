import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CbsFormReadonlyControlComponent } from './cbs-form-readonly-control.component';

describe('CbsFormReadonlyControlComponent', () => {
  let component: CbsFormReadonlyControlComponent;
  let fixture: ComponentFixture<CbsFormReadonlyControlComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CbsFormReadonlyControlComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CbsFormReadonlyControlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
