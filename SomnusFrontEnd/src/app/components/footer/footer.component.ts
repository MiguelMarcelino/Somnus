import { Component, OnInit } from '@angular/core';
import { AppSettings } from 'src/app/appSettings';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit {

  title = AppSettings.SERVER_NAME;
  
  constructor() { }

  ngOnInit(): void {
  }

}
