import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ArticlesSectionComponent } from './articles-section.component';

describe('ArticlesSectionComponent', () => {
  let component: ArticlesSectionComponent;
  let fixture: ComponentFixture<ArticlesSectionComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ArticlesSectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ArticlesSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
