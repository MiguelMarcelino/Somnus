import { Component, OnInit } from '@angular/core';
import { ArticleModel } from 'src/app/models/article.model';
import { ArticlesService } from 'src/app/services/controllers/articles-controller.service';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';

@Component({
  selector: 'app-articles-section',
  templateUrl: './articles-section.component.html',
  styleUrls: ['./articles-section.component.css']
})
export class ArticlesSectionComponent implements OnInit {

  articles: ArticleModel[];
  noArticles: boolean = false;

  constructor(
    private articleService: ArticlesService,
    private authenticationService: AuthenticationService
  ) { }

  ngOnInit(): void {
    this.getArticles();
  }

  getArticles(): void {
    this.articleService.getAll().subscribe(allArticles => {
      this.articles = allArticles.article_list;
      if(!allArticles || allArticles.article_list.length == 0) {
        this.noArticles = true;
      }
    })
  }

  getArticleLink(article: ArticleModel) {
    return "/article/" + article._id;
  }

}
