import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CarPositionComponent } from './car-position.component';

describe('CarPositionComponent', () => {
  let component: CarPositionComponent;
  let fixture: ComponentFixture<CarPositionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CarPositionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CarPositionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
