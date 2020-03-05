import { Component, OnInit } from '@angular/core';

declare function click(buttonID):any;

@Component({
  selector: 'app-contact-menu',
  templateUrl: './contact-menu.component.html',
  styleUrls: ['./contact-menu.component.css']
})
export class ContactMenuComponent implements OnInit {

  constructor() { }

  ngOnInit() {
    click('#contactButton');
  }

}
