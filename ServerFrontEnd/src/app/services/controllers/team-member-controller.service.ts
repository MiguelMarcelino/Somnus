import { TeamMemberModel } from 'src/app/models/teamMember.model';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { TemplateControllerService } from './template-controller.service';
import { AppRoutesService } from '../routes/app-routes.service';

@Injectable({
    providedIn: 'root'
})
export class TeamMemberControllerService extends TemplateControllerService<TeamMemberModel>{

    private teamMemberUrl = 'not_implemented';
  
    constructor( 
      protected http: HttpClient,
      private appRoutes: AppRoutesService
    ) {
      super(http);
    }

    protected getApiUrlAll() {
      return this.appRoutes.apiTeamMembersEndPoint;
    }
    
    protected getApiUrlObject() {
        return this.teamMemberUrl;
    }

}