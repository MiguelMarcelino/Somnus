import { Component, OnInit } from '@angular/core';
import { TeamMemberModel } from 'src/app/models/teamMember.model';
import { AppSettings } from 'src/app/appSettings';
import { TeamMemberControllerService } from 'src/app/services/controllers/team-member-controller.service';

@Component({
  selector: 'app-team-member-page',
  templateUrl: './team-member-page.component.html',
  styleUrls: ['./team-member-page.component.scss']
})
export class TeamMemberPageComponent implements OnInit {

  title = AppSettings.SERVER_NAME;
  teamMembers: TeamMemberModel[];
  loading: boolean = true;

  constructor(
    private teamMemberService: TeamMemberControllerService
  ) { }

  ngOnInit(): void {
    this.getTeamMembers();
    // this.populateTeamMembers();
  }

  getTeamMembers(): void {
    this.teamMemberService.getAll().subscribe( teamMemberList => {
      this.loading = false;
      this.teamMembers = teamMemberList;
    },
    (error)=>{
      this.loading = false;
    });
  }

  // populateTeamMembers() {
  //   this.loading = false;
  //   let teamMember1 = {"teamMemberName": "Test1", "photoPath": "../../../assets/icons/git-img.png", 
  //   "dateJoined": new Date(), "contributions": 6};
  //   let teamMember2 = {"memberName": "Test2", "photoPath": "../../../assets/icons/git-img.png", 
  //   "dateJoined": new Date(), "contributions": 10};
  //   let teamMember3 = {"memberName": "Test3", "photoPath": "../../../assets/icons/git-img.png", 
  //   "dateJoined": new Date(), "contributions": 33};
  //   let teamMember4 = {"memberName": "Test4", "photoPath": "../../../assets/icons/git-img.png", 
  //   "dateJoined": new Date(), "contributions": 4};
  //   this.teamMembers = [];
  //   this.teamMembers.push(teamMember1);
  //   this.teamMembers.push(teamMember2);
  //   this.teamMembers.push(teamMember3);
  //   this.teamMembers.push(teamMember4);
  // }

}
