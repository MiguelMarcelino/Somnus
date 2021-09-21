import { Component, Input, OnInit } from '@angular/core';
import { PostModel } from 'src/app/models/post/post.model';
@Component({
  selector: 'app-post-box-component',
  templateUrl: './post-box.component.html',
  styleUrls: ['./post-box.component.scss']
})
export class PostBoxComponentComponent implements OnInit {

  @Input() imgSource: string;
  @Input() post: PostModel

  constructor() { }

  ngOnInit(): void {
  }

  hasTopic() {
    return 'topic' in this.post
  }

}
