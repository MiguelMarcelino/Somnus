import { Component, OnInit } from '@angular/core';
import { ArticleModel } from 'src/app/models/article.model';
import { ActivatedRoute } from '@angular/router';
import { GamesService } from 'src/app/services/controllers/games-controller.service';

@Component({
  selector: 'app-game-page',
  templateUrl: './game-page.component.html',
  styleUrls: ['./game-page.component.css']
})
export class GamePageComponent implements OnInit {

  private urlParams = {};
  gameArticle: ArticleModel;

  constructor(
    private route: ActivatedRoute,
    private gameService: GamesService
  ) { }

  ngOnInit(): void {
    this.getArticle();
  }

  getArticle(): void {
    const id = this.route.snapshot.paramMap.get('id');
    this.route.queryParams.subscribe(params => {
      this.urlParams = {...params};
    });
    this.gameService.getObject(id).subscribe(article =>{
      if(article) {
        this.gameArticle = article.article;
      }
    })
  }

}
