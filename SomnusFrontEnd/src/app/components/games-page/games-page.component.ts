import { Component, OnInit } from '@angular/core';
import { MinecraftInfoModel } from 'src/app/models/games/minecraft.model';
import { GamesControllerService } from 'src/app/services/controllers/games-controller.service';

@Component({
  selector: 'app-games-page',
  templateUrl: './games-page.component.html',
  styleUrls: ['./games-page.component.css']
})
export class GamesPageComponent implements OnInit {

  minecraftData: MinecraftInfoModel;
  players: boolean;
  loading: boolean = true;

  constructor(
    private gamesService: GamesControllerService
  ) { }

  ngOnInit(): void {
    this.getMinecraftData();
  }

  getMinecraftData() {
    this.gamesService.getMinecraftApi().subscribe(minecraftInfo => {
      this.loading = false;
      this.minecraftData = minecraftInfo;
      this.players = minecraftInfo.numOnlinePlayers > 0;
    },
    (error)=>{
      this.loading = false;
    });
  }

}
