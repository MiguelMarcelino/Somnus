import { FeedbackModel } from 'src/app/models/feedback.model';
import { HttpClient } from '@angular/common/http';
import { TemplateControllerService } from './template-controller.service';
import { AppRoutesService } from '../routes/app-routes.service';
import { Injectable, Injector } from '@angular/core';
@Injectable({
    providedIn: 'root'
})
export class FeedbackControllerService extends TemplateControllerService<FeedbackModel>{

    constructor( 
        protected http: HttpClient,
        private appRoutes: AppRoutesService
    ) {
        super(http);
    }

    // TODO
    // For reading feedbacks later (admin use only)
    protected getApiUrlAll() {
        return "";
    }

    protected getApiUrlObject() {
        return this.appRoutes.apiFeedbackEndpoint;
    }

}