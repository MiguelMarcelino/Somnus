import { Injectable } from '@angular/core';
import { GameAchievementModel } from 'src/app/models/gameAchievement.model';
import { TemplateControllerService } from './template-controller.service';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class GamesService extends TemplateControllerService<GameAchievementModel>{

  private gameAchievementsURL = '/api/catalog/gameAchievements';
  private gameAchievementURL = '/api/catalog/gameAchievement';

  constructor( 
    protected http: HttpClient
  ) {
    super(http);
  }

  protected getApiUrlAll() {
    return this.gameAchievementsURL;
  }

  protected getApiUrlObject() {
    return this.gameAchievementURL;
  }

}


