import { Component, OnInit } from '@angular/core';
import { AppSettings } from './../../appSettings';
import Typed from 'typed.js';
@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent implements OnInit {

  title = AppSettings.SERVER_NAME;
  private typed: Typed;

  constructor() { }

  ngOnInit(): void {
    this.createOptions();
  }

  createOptions() {
    let options = {
      strings: ['Projects', 'Research', 'Work'],
      typeSpeed: 120,
      backSpeed: 120,
      backDelay: 2500,
      loop: true,
    };
    this.typed = new Typed('.typed', options);
  }

}
