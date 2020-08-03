import { Component, OnInit } from '@angular/core';
import { GamesService } from 'src/app/services/controllers/games-controller.service';
import { ArticleModel } from 'src/app/models/article.model';

@Component({
  selector: 'app-gaming-section',
  templateUrl: './gaming-section.component.html',
  styleUrls: ['./gaming-section.component.css'],
})
export class GamingSectionComponent implements OnInit {

  gameArticles: ArticleModel[];
  noArticles: boolean = false;

  constructor(
    private gamesApi: GamesService
  ) { }

  ngOnInit(): void {
    this.getGameArticlesList();
  }

  getGameArticlesList(): void {
    this.gamesApi.getAll().subscribe((gamesList) => {
      this.gameArticles = gamesList.article_list;
      if(!gamesList || gamesList.article_list.length == 0) {
        this.noArticles = true;
      }
    })
  }

}
