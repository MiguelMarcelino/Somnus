import { Component, OnInit } from '@angular/core';
import { UserModel } from 'src/app/models/user.model';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  // app user
  user: firebase.default.User;
  userInfo: UserModel;

  constructor(
    private authenticationService: AuthenticationService
  ) { }

  ngOnInit(): void {
    this.authenticationService.getLoggedInUser()
      .subscribe (user => {
        this.user = user;
    });
    this.userInfo = this.authenticationService.getCurrentUser();
  }

}
