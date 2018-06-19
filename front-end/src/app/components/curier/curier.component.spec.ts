import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CurierComponent } from './curier.component';

describe('CurierComponent', () => {
  let component: CurierComponent;
  let fixture: ComponentFixture<CurierComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CurierComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CurierComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
