import { Component, OnInit } from '@angular/core';
import { AppSettings } from 'src/app/appSettings';
import { MilestoneModel } from 'src/app/models/milestone.model';

@Component({
  selector: 'app-about-page',
  templateUrl: './about-page.component.html',
  styleUrls: ['./about-page.component.css']
})
export class AboutPageComponent implements OnInit {

  title = AppSettings.SERVER_NAME;
  milestones: MilestoneModel[];

  constructor() { }

  ngOnInit(): void {
    this.testMilestones();
  }

  testMilestones(): void {
    this.milestones = [];
    this.milestones.push({title: "1st Version of server Website created",
      year: 2019, description: "Simple website focused on working features"},
      {title: "2nd and current Version of Server Website created",
      year: 2020, description: "More comples, focused on improving upon the" +
      "last version. More modern interface"});
  }
}
