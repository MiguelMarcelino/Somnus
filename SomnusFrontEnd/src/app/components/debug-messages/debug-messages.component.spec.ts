import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { DebugMessagesComponent } from './debug-messages.component';

describe('DebugMessagesComponent', () => {
  let component: DebugMessagesComponent;
  let fixture: ComponentFixture<DebugMessagesComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ DebugMessagesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DebugMessagesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
