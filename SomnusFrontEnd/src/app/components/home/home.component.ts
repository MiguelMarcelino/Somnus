import { Component, OnInit } from '@angular/core';
import { AppSettings } from './../../appSettings';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { Router, ROUTER_CONFIGURATION } from '@angular/router';
import * as firebase from 'firebase/app';
import { UserModel } from 'src/app/models/user.model';
import { Role } from 'src/app/models/role.model';

@Component({
  selector: 'home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  // app user
  user: firebase.default.User;
  currentUser: UserModel;
  // app title
  title = AppSettings.SERVER_NAME;
  navbarCollapsed = false;

  constructor(
    private authenticationService: AuthenticationService,
    private router: Router
  ) { 
  }

  ngOnInit(): void {
    this.authenticationService.getLoggedInUser()
      .subscribe (user => {
        this.user = user;
        this.currentUser = this.authenticationService.getCurrentUser();
    });
  }

  logout(): void {
    this.authenticationService.logout();
  }

  isActive(button: String): boolean {
    return (this.router.url == button);
  }

  onEnter(value) {
    this.router.navigate(["/articles"], {queryParams: {value: value}});
  }

  isManagerOrAdmin() {
    return this.user ? (this.currentUser.role == Role.Admin || 
      this.currentUser.role === Role.Manager) : false;
  }
}
