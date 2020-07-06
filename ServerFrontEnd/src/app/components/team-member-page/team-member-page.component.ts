import { Component, OnInit } from '@angular/core';
import { TeamMemberModel } from 'src/app/models/teamMember.model';
import { AppSettings } from 'src/app/appSettings';

@Component({
  selector: 'app-team-member-page',
  templateUrl: './team-member-page.component.html',
  styleUrls: ['./team-member-page.component.css']
})
export class TeamMemberPageComponent implements OnInit {

  title = AppSettings.SERVER_NAME;
  teamMembers: TeamMemberModel[];

  constructor() { }

  ngOnInit(): void {
    this.populateTeamMembers();
  }

  populateTeamMembers(): void {
    this.teamMembers = [];
    this.teamMembers.push({memberName: "David Binaire", photoPath: "../../assets/testImgs/mine.png",
      dateJoined: new Date(), contributions: 100}, 
      {memberName: "David Binaire 2", photoPath: "../../assets/testImgs/mine.png",
      dateJoined: new Date(), contributions: 110},
      {memberName: "David Binaire 3", photoPath: "../../assets/testImgs/mine.png",
      dateJoined: new Date(), contributions: 110},
      {memberName: "David Binaire 4", photoPath: "../../assets/testImgs/mine.png",
      dateJoined: new Date(), contributions: 110},
      {memberName: "David Binaire 5", photoPath: "../../assets/testImgs/mine.png",
      dateJoined: new Date(), contributions: 110});
  }

}
