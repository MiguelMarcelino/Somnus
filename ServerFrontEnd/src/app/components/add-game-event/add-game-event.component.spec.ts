import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddGameEventComponent } from './add-game-event.component';

describe('AddGameEventComponent', () => {
  let component: AddGameEventComponent;
  let fixture: ComponentFixture<AddGameEventComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddGameEventComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddGameEventComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
