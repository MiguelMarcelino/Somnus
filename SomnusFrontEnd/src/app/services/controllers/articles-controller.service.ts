import { TemplateControllerService } from './template-controller.service';
import { ArticleModel } from 'src/app/models/article.model';
import { HttpClient } from '@angular/common/http';
import { Injectable, Injector } from '@angular/core';
import { AppRoutesService } from '../routes/app-routes.service';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class ArticlesService extends TemplateControllerService<ArticleModel> {

    constructor( 
        protected http: HttpClient,
        private appRoutes: AppRoutesService,
    ) {
      super(http);
    }

    protected getApiUrlAll() {
        return this.appRoutes.apiArticlesEndPoint;
    }

    protected getApiUrlObject() {
        return this.appRoutes.apiArticleEndPoint;
    }

    searchArticles(articleName: string): Observable<any> {
        return this.http.get(`${this.appRoutes.apiArticleEndPointSearch}/${articleName}`);
    }

    getDeletedArticles(): Observable<any> {
        return this.http.get(`${this.appRoutes.apiArticleEndPointSearch}/deleted-articles`);
    }

    restoreArticle(id: string): Observable<any> {
        return this.http.get(`${this.appRoutes.apiArticleEndPointSearch}/restore-article/${id}`);
    }
    
}