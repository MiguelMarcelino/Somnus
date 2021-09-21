import { Component, Input, OnInit } from '@angular/core';
import { ArticleModel } from 'src/app/models/post/article.model';
import { PostModel } from 'src/app/models/post/post.model';
@Component({
  selector: 'app-post-box-component',
  templateUrl: './post-box.component.html',
  styleUrls: ['./post-box.component.scss']
})
export class PostBoxComponentComponent implements OnInit {

  @Input() imgSource: string;
  @Input() post: PostModel
  topic: String;

  constructor() { }

  ngOnInit(): void {
  }

  hasTopic() {
    var hastopic = 'topic' in this.post;
    if(hastopic) {
      var article: ArticleModel = this.post as ArticleModel;
      this.topic = article.topic;
    }
    return hastopic;
  }

}
