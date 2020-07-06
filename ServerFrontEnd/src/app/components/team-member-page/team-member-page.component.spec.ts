import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamMemberPageComponent } from './team-member-page.component';

describe('TeamMemberPageComponent', () => {
  let component: TeamMemberPageComponent;
  let fixture: ComponentFixture<TeamMemberPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TeamMemberPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TeamMemberPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
