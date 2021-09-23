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

  isSmall:boolean = false;

  constructor() { }

  ngOnInit(): void {
    var component = this;
    window.addEventListener('resize', function(event){
      component.responsive();
    });
  }

  ngAfterContentInit(){
    this.responsive();
  }

  responsive(){
    if(this.isSmall){
      var container = document.getElementsByClassName('article-container-resp')[0] as HTMLElement;
      var img = document.getElementsByClassName('left-post-img-resp')[0];
      var data = document.getElementsByClassName('right-post-data-resp')[0];
      var width = container.offsetWidth;

      if(width > 700 ){
        container.classList.remove("article-container-resp");
        img.classList.remove("left-post-img-resp");
        data.classList.remove("right-post-data-resp");

        container.classList.add("article-container");
        img.classList.add("left-post-img");
        data.classList.add("right-post-data");

        this.isSmall = false;
      }

    } else{
      var container = document.getElementsByClassName('article-container')[0] as HTMLElement;
      var img = document.getElementsByClassName('left-post-img')[0];
      var data = document.getElementsByClassName('right-post-data')[0];
      var width = container.offsetWidth;

      if(width < 700 ){
        container.classList.remove("article-container");
        img.classList.remove("left-post-img");
        data.classList.remove("right-post-data");
  
        container.classList.add("article-container-resp");
        img.classList.add("left-post-img-resp");
        data.classList.add("right-post-data-resp");
        this.isSmall = true;
      }
    }
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
