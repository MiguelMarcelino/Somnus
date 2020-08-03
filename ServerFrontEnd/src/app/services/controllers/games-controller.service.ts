import { Injectable } from '@angular/core';
import { TemplateControllerService } from './template-controller.service';
import { HttpClient } from '@angular/common/http';
import { AppRoutesService } from '../routes/app-routes.service';
import { ArticleModel } from 'src/app/models/article.model';

@Injectable({
  providedIn: 'root'
})
export class GamesService extends TemplateControllerService<ArticleModel>{

  constructor( 
    protected http: HttpClient,
    private appRoutes: AppRoutesService
  ) {
    super(http);
  }

  protected getApiUrlAll() {
    return this.appRoutes.apiGamesEndPoint;
  }

  protected getApiUrlObject() {
    return this.appRoutes.apiGameEndPoint;
  }

}


