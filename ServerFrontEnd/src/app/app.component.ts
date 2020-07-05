import { Component } from '@angular/core';
import { AppSettings } from './appSettings';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  title = AppSettings.SERVER_NAME;
}
