import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { NewsPostModel } from "src/app/models/post/news-post.model";
import { AppRoutesService } from "../routes/app-routes.service";
import { TemplateControllerService } from "./template-controller.service";

@Injectable({
    providedIn: 'root'
})
export class NewsPostService extends TemplateControllerService<NewsPostModel> {

    constructor( 
        protected http: HttpClient,
        private appRoutes: AppRoutesService,
    ) {
      super(http);
    }

    protected getApiUrlAll() {
        return this.appRoutes.apiNewsPostsEndPoint;
    }

    protected getApiUrlObject() {
        return this.appRoutes.apiNewsPostEndPoint;
    }
    
}