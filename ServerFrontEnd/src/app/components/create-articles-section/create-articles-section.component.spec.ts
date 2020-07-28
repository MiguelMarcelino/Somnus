import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateArticlesSectionComponent } from './create-articles-section.component';

describe('CreateArticlesSectionComponent', () => {
  let component: CreateArticlesSectionComponent;
  let fixture: ComponentFixture<CreateArticlesSectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateArticlesSectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateArticlesSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
