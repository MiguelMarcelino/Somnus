import { Component, OnInit, Output } from '@angular/core';
import { LoginService } from 'src/app/services/authentication/login.service';
import { AngularFireAuth } from '@angular/fire/auth';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  // app user
  user: firebase.User;
  //google Icon
  googlePhoto = "https://cdn.freebiesupply.com/logos/large/2x/google-icon-logo-png-transparent.png"
  //login form
  loginForm: FormGroup;

  constructor(
    private service: LoginService,
    private angularFireAuth: AngularFireAuth,
    private formBuilder: FormBuilder
  ) { }

  loginGoogle(): void {
    this.service.loginWithGoogle();
  }

  logout(): void {
    this.service.logout();
  }

  ngOnInit() {
    this.angularFireAuth.authState
      .subscribe (user => {
        this.user = user;
      });
    this.loginForm = this.formBuilder.group({
      email: new FormControl(''),
      password: new FormControl(''),
    });
  }

  onSubmit() {
    this.service.loginWithEmail(this.loginForm.get('email').value,
      this.loginForm.get('password').value)
  }

}
