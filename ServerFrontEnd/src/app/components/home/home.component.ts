import { Component, OnInit } from '@angular/core';
import { AppSettings } from './../../appSettings';
import { AngularFireAuth } from '@angular/fire/auth';
import { LoginService } from 'src/app/services/authentication/login.service';

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
    private service: LoginService,
    private angularFireAuth: AngularFireAuth,
  ) { }

  ngOnInit(): void {
    this.service.getLoggedInUser()
      .subscribe (user => {
        this.user = user;
      })
  }

  logout(): void {
    this.service.logout();
  }

}
