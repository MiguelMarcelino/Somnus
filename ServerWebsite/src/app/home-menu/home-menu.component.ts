import { Component, OnInit } from '@angular/core';

declare function ping(port, imgName, textName):any;
declare function click(buttonID):any;

@Component({
  selector: 'app-home-menu',
  templateUrl: './home-menu.component.html',
  styleUrls: ['./home-menu.component.css']
})


export class HomeMenuComponent implements OnInit {

  constructor() { }

  ngOnInit() {
    ping(':5050', '#couchpotato_img', '#couchpotato_text');
    ping(':8989', '#sonarr_img', '#sonarr_text');
    ping(':80', '#transmission_img', '#transmission_text');       //ports show "Not Available"
    ping(':25565', '#minecraft_img', '#minecraft_text');          //ports show "Not Available"
    click('#homeButton');
  }
  
}





