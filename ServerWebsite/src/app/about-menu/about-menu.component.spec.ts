import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AboutMenuComponent } from './about-menu.component';

describe('AboutMenuComponent', () => {
  let component: AboutMenuComponent;
  let fixture: ComponentFixture<AboutMenuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AboutMenuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AboutMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
