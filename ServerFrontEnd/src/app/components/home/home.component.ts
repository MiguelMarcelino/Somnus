import { Component, OnInit } from '@angular/core';
import { AppSettings } from './../../appSettings';
import { AngularFireAuth } from '@angular/fire/auth';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';

@Component({
  selector: 'home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  // app user
  user: firebase.User;
  // app title
  title = AppSettings.SERVER_NAME;

  constructor(
    private authenticationService: AuthenticationService,
    private angularFireAuth: AngularFireAuth,
  ) { }

  ngOnInit(): void {
    this.authenticationService.getLoggedInUser()
      .subscribe (user => {
        this.user = user;
    })
  }

  logout(): void {
    this.authenticationService.logout();
  }

}
