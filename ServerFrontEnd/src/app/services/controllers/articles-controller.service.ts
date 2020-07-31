import { TemplateControllerService } from './template-controller.service';
import { ArticleModel } from 'src/app/models/article.model';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AppRoutesService } from '../routes/app-routes.service';

@Injectable({
    providedIn: 'root'
})
export class ArticlesService extends TemplateControllerService<ArticleModel> {

    constructor( 
        protected http: HttpClient,
        private appRoutes: AppRoutesService
    ) {
      super(http);
    }

    protected getApiUrlAll() {
        return this.appRoutes.apiArticlesEndPoint;
    }

    protected getApiUrlObject() {
        return this.appRoutes.apiArticleEndPoint;
    }
    
}