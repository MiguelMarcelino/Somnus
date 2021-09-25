import { Component, HostListener, Input, OnInit } from '@angular/core';
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
    // For some reason this results in "offsetWidth" being undefined
    // var component = this;
    // window.addEventListener('resize', function(event){
    //   component.responsive();
    // });
  }

  @HostListener('window:resize', ['$event'])
  onResize(event) {
    this.responsive();
  }

  ngAfterContentInit(){
    this.responsive();
  }

  responsive(){
    if(this.isSmall){
      var container = document.getElementsByClassName('article-container-resp')[0] as HTMLElement;
      var img_cont = document.getElementsByClassName('post-img-cont-resp')[0];
      var data = document.getElementsByClassName('post-data-cont-resp')[0];
      var width = container.offsetWidth;

      if(width > 700 ){
        container.classList.remove("article-container-resp");
        img_cont.classList.remove("post-img-cont-resp");
        data.classList.remove("post-data-cont-resp");

        container.classList.add("article-container");
        img_cont.classList.add("post-img-cont");
        data.classList.add("post-data-cont");

        this.isSmall = false;
      }

    } else {
      var container = document.getElementsByClassName('article-container')[0] as HTMLElement;
      var img_cont = document.getElementsByClassName('post-img-cont')[0];
      var data = document.getElementsByClassName('post-data-cont')[0];
      var width = container.offsetWidth;

      if(width < 700 ){
        container.classList.remove("article-container");
        img_cont.classList.remove("post-img-cont");
        data.classList.remove("post-data-cont");
  
        container.classList.add("article-container-resp");
        img_cont.classList.add("post-img-cont-resp");
        data.classList.add("post-data-cont-resp");
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
