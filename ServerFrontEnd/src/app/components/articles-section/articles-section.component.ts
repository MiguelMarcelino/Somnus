import { Component, OnInit } from '@angular/core';
import { ArticleModel } from 'src/app/models/article.model';
import { ArticlesService } from 'src/app/services/controllers/articles-controller.service';

@Component({
  selector: 'app-articles-section',
  templateUrl: './articles-section.component.html',
  styleUrls: ['./articles-section.component.css']
})
export class ArticlesSectionComponent implements OnInit {

  articles: ArticleModel[];

  constructor(
    private articleService: ArticlesService
  ) { }

  ngOnInit(): void {
    this.getArticles();
  }

  getArticles(): void {
    this.articleService.getAll().subscribe(allArticles => {
      this.articles = allArticles.article_list;
    })
  }

}
