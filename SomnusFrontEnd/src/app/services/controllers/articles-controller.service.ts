import { TemplateControllerService } from './template-controller.service';
import { ArticleModel } from 'src/app/models/post/article.model';
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

    // for debug only without server

    public getDebugArticle(): Observable<any> {
        let url = `https://somnus.ddns.net:3000/articles-api/article/5`;
        return this.http.get(url);
    }

    public getDebugNews(): Observable<any> {
        let url = `https://somnus.ddns.net:3000/news-api/news-post/4`;
        return this.http.get(url);
    }
    //

    searchArticles(articleName: string): Observable<any> {
        return this.http.get(`${this.appRoutes.apiArticleEndPointSearch}/${articleName}`);
    }

    getDeletedArticles(): Observable<any> {
        return this.http.get(`${this.appRoutes.apiArticles}/deleted-articles`);
    }

    restoreArticle(id: string): Observable<any> {
        return this.http.get(`${this.appRoutes.apiArticles}/restore-article/${id}`);
    }

    getDeletedArticle(id: string): Observable<any> {
        return this.http.get(`${this.appRoutes.apiArticles}/deleted-article/${id}`);
    }
    
}