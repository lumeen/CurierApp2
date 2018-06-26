import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ParcelsListComponent } from './parcels-list.component';

describe('ParcelsListComponent', () => {
  let component: ParcelsListComponent;
  let fixture: ComponentFixture<ParcelsListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ParcelsListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ParcelsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
