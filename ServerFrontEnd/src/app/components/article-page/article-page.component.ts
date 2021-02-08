import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ArticlesService } from 'src/app/services/controllers/articles-controller.service';
import { ArticleModel } from 'src/app/models/article.model';

@Component({
  selector: 'app-article-page',
  templateUrl: './article-page.component.html',
  styleUrls: ['./article-page.component.css']
})
export class ArticlePageComponent implements OnInit {

  private urlParams = {};
  article: ArticleModel;

  constructor(
    private route: ActivatedRoute,
    private articleService: ArticlesService
  ) { }

  ngOnInit(): void {
    this.getArticle();
  }

  getArticle(): void {
    const id = this.route.snapshot.paramMap.get('id');
    this.route.queryParams.subscribe(params => {
      this.urlParams = {...params};
    });
    this.articleService.getObject(id).subscribe(article =>{
      if(article) {
        console.log(article);
        this.article = article;
      }
    })
  }

}
