import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TempServerInfoComponent } from './temp-server-info.component';

describe('TempServerInfoComponent', () => {
  let component: TempServerInfoComponent;
  let fixture: ComponentFixture<TempServerInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TempServerInfoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TempServerInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
