import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CreatePostSectionComponent } from './create-post-section.component';

describe('CreatePostSectionComponent', () => {
  let component: CreatePostSectionComponent;
  let fixture: ComponentFixture<CreatePostSectionComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CreatePostSectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreatePostSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
