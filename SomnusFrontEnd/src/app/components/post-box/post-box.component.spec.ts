import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostBoxComponentComponent } from './post-box.component';

describe('PostBoxComponentComponent', () => {
  let component: PostBoxComponentComponent;
  let fixture: ComponentFixture<PostBoxComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PostBoxComponentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PostBoxComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
