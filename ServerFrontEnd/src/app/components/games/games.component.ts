import { Component, OnInit } from '@angular/core';
import { GameAchievementModel } from 'src/app/models/gameAchievement.model';
import { MatButtonModule } from '@angular/material/button'; //this import should not be necessary
import { GamesService } from 'src/app/services/controllers/game-achievements-controller.service';
import { transition, animate, trigger, state, style } from '@angular/animations';

// import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-games',
  templateUrl: './games.component.html',
  styleUrls: ['./games.component.css'],
  animations: [
    trigger(
      'inOutAnimation',
      [
        transition(
          ':enter',
          [
            style({ height: 0, opacity: 0 }),
            animate('0.5s ease',
              style({ height: 440, opacity: 1 }))
          ]
        ),
        transition(
          ':leave',
          [
            style({ height: 440 }),
            animate('0.5s ease',
              style({ height: 0, opacity: 0 }))
          ]
        )
      ]
    )

  ]


})
export class GamesComponent implements OnInit {

  listAchievements: GameAchievementModel[];
  showAddGames = false;

  constructor(private gamesApi: GamesService) { }

  ngOnInit(): void {
    this.testAchievements();
  }

  // create service to get achievements
  // but first create dummy game achievement list
  testAchievements(): void {
    let testAchievement = {
      title: "Biggest mining craze", author: "Davidian",
      description: "John Black mined away into the night. It was incredible to" +
        "see the construction from above. It almost crashed the server. ",
      photoPath: "../../assets/testImgs/mine.png", dateAdded: new Date()
    };

    this.listAchievements = [];
    this.listAchievements.push(testAchievement);
  }

  showAddGameEvent(): void {
    this.showAddGames = !this.showAddGames;
  }

  getGameAchievements(): void {
    this.gamesApi.getAll().subscribe((achievementList) =>
      this.listAchievements = achievementList)
  }
}
