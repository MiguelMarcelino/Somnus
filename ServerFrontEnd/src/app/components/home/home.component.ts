import { Component, OnInit } from '@angular/core';
import { AppSettings } from './../../appSettings';

@Component({
  selector: 'home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  title = AppSettings.SERVER_NAME;

  constructor() { }

  ngOnInit(): void {
  }

}
