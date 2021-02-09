import { Component, OnInit } from '@angular/core';
import { ArticleModel } from 'src/app/models/article.model';
import { ArticlesService } from 'src/app/services/controllers/articles-controller.service';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-articles-section',
  templateUrl: './articles-section.component.html',
  styleUrls: ['./articles-section.component.css']
})
export class ArticlesSectionComponent implements OnInit {

  articles: ArticleModel[] = [];
  noArticles: boolean = false;

  constructor(
    private articleService: ArticlesService,
    private authenticationService: AuthenticationService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.route.queryParamMap.subscribe((params: any) => {
      if(!params.params.value){
        this.getArticles();
      } else {
        this.getSearchArticles(params.params.value);
      }
    });
  }

  getArticles(): void {
    this.articleService.getAll().subscribe(allArticles => {
      this.articles = allArticles;
      if(!allArticles || allArticles.length == 0) {
        this.noArticles = true;
      }
    })
  }

  getSearchArticles(value: string) {
    this.articleService.searchArticles(value).subscribe(articles => {
      this.articles = articles;
      if(!articles || articles.length == 0) {
        this.noArticles = true;
      }
    });
  }

  getArticleLink(article: ArticleModel) {
    return "/article/" + article.id;
  }  

}
