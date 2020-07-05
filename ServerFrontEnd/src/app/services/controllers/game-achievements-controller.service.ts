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

  // TODO review --> Check if abstract class is correct
  // getAll(): Observable<any> {
  //   return this.http.get(this.gameAchievementsURL);
  // }

  // getGameAchievement(id: string): Observable<any> {
  //   let url = `${this.gameAchievementURL}/${id}`;
  //   return this.http.get(url);
  // }

  // addGameAchievement(gameAchievement: GameAchievementModel): Observable<any> {
  //   return this.http.post(this.gameAchievementCreateURL, gameAchievement);
  // }

  // deleteGameAchievement(id: string): Observable<any> {
  //   let url = `${this.gameAchievementURL}/${id}/delete`;
  //   return this.http.post(url, {"id": id});
  // }

  protected getApiUrlAll() {
    return this.gameAchievementsURL;
  }

  protected getApiUrlObject() {
    return this.gameAchievementURL;
  }

}


