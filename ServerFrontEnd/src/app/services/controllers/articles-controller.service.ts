import { TemplateControllerService } from './template-controller.service';
import { ArticleModel } from 'src/app/models/article.model';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class ArticlesService extends TemplateControllerService<ArticleModel> {

    private articlesURL = '/api/catalog/articles';
    private articleURL = '/api/catalog/article';

    constructor( 
        protected http: HttpClient
    ) {
      super(http);
    }

    protected getApiUrlAll() {
        return this.articlesURL;
    }

    protected getApiUrlObject() {
        return this.articleURL;
    }
    
}