import { Component, OnInit } from '@angular/core';
import { TeamMemberModel } from 'src/app/models/teamMember.model';
import { AppSettings } from 'src/app/appSettings';
import { TeamMemberControllerService } from 'src/app/services/controllers/team-member-controller.service';

@Component({
  selector: 'app-team-member-page',
  templateUrl: './team-member-page.component.html',
  styleUrls: ['./team-member-page.component.css']
})
export class TeamMemberPageComponent implements OnInit {

  title = AppSettings.SERVER_NAME;
  teamMembers: TeamMemberModel[];

  constructor(
    private teamMemberService: TeamMemberControllerService
  ) { }

  ngOnInit(): void {
    this.getTeamMembers();
    // this.populateTeamMembers();
  }

  getTeamMembers(): void {
    this.teamMemberService.getAll().subscribe( teamMembers => {
      this.teamMembers = teamMembers.team_member_list;
    })
  }

}
