import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ServerStatsComponent } from './server-stats.component';

describe('ServerStatsComponent', () => {
  let component: ServerStatsComponent;
  let fixture: ComponentFixture<ServerStatsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ServerStatsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServerStatsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
