import { FeedbackModel } from 'src/app/models/feedback.model';
import { HttpClient } from '@angular/common/http';
import { TemplateControllerService } from './template-controller.service';

export class FeedbackControllerService extends TemplateControllerService<FeedbackModel>{

    private allFeedbacks = '/api/catalog/feedbacks';
    private feedbackUrl = '/api/catalog/feedback';

    constructor( 
        protected http: HttpClient
    ) {
        super(http);
    }

    //not really usefull right now
    protected getApiUrlAll() {
        return this.allFeedbacks;
    }

    //Feedback needs to be sent to node.js to use nodemailer 
    //and only then can we send email to server email(gmail)
    protected getApiUrlObject() {
        return this.feedbackUrl;
    }
}