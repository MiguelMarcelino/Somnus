import { Component, OnInit } from '@angular/core';
import { AppSettings } from './../../appSettings';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { Router } from '@angular/router';
import { ArticlesService } from 'src/app/services/controllers/articles-controller.service';

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
}
