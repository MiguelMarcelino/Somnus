import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { UserComment } from "src/app/models/user-comment.model";
import { AppRoutesService } from "../routes/app-routes.service";
import { TemplateControllerService } from "./template-controller.service";

@Injectable({
    providedIn: 'root'
})
export class CommentService extends TemplateControllerService<UserComment> {
    constructor( 
        protected http: HttpClient,
        private appRoutes: AppRoutesService,
    ) {
      super(http);
    }

    protected getApiUrlAll() {
        return this.appRoutes.apiCommentsEndPoint;
    }

    protected getApiUrlObject() {
        return this.appRoutes.apiCommentEndPoint;
    }

    public getCommentsFromArticle(articleId: string): Observable<any> {
        let url = `${this.getApiUrlAll()}/${articleId}`;
        return this.http.get(url);
    }

    public updateComment(comment: UserComment): Observable<any> {
        let url = `${this.getApiUrlObject()}/update`;
        return this.http.post(url, comment);
    }

    public addLike(commentId: string): Observable<any> {
        let url = `${this.getApiUrlObject()}/add-like/${commentId}`;
        return this.http.get(url);
    }

    public removeLike(commentId: string): Observable<any> {
        let url = `${this.getApiUrlObject()}/remove-like/${commentId}`;
        return this.http.get(url);
    }
}