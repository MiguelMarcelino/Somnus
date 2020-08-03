import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateGameStoriesSectionComponent } from './create-game-stories-section.component';

describe('CreateGameStoriesSectionComponent', () => {
  let component: CreateGameStoriesSectionComponent;
  let fixture: ComponentFixture<CreateGameStoriesSectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateGameStoriesSectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateGameStoriesSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
