import { Component, OnInit } from '@angular/core';
import { AppSettings } from './../../appSettings';
@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {

  title = AppSettings.SERVER_NAME;

  constructor() { }

  ngOnInit(): void {
  }

}
