import { Component, OnInit } from '@angular/core';

declare function click(buttonID):any;

@Component({
  selector: 'app-about-menu',
  templateUrl: './about-menu.component.html',
  styleUrls: ['./about-menu.component.css']
})
export class AboutMenuComponent implements OnInit {

  constructor() { }

  ngOnInit() {
    click('#aboutButton');
  }

}
