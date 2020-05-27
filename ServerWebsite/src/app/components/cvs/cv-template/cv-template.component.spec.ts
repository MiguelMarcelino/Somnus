import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CvTemplateComponent } from './cv-template.component';

describe('CvTemplateComponent', () => {
  let component: CvTemplateComponent;
  let fixture: ComponentFixture<CvTemplateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CvTemplateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CvTemplateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
