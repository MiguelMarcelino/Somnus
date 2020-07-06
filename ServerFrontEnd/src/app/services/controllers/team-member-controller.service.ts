import { TeamMemberModel } from 'src/app/models/teamMember.model';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { TemplateControllerService } from './template-controller.service';

@Injectable({
    providedIn: 'root'
})
export class TeamMemberControllerService extends TemplateControllerService<TeamMemberModel>{

    private teamMembersUrl = '/api/catalog/teamMembers';
    private teamMemberUrl = '/api/catalog/teamMember';
  
    constructor( 
      protected http: HttpClient
    ) {
      super(http);
    }

    protected getApiUrlAll() {
        return this.teamMembersUrl;
    }
    
    protected getApiUrlObject() {
        return this.teamMemberUrl;
    }

}