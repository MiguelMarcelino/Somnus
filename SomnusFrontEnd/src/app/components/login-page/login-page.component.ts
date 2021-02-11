import { Component, OnInit, Output } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { AngularFireAuth } from '@angular/fire/auth';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { ErrorInterface } from 'src/handlers/error-interface';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  //google Icon
  googlePhoto = "https://img.icons8.com/ios/50/000000/google-logo--v1.png";
  //login form
  loginForm: FormGroup;
  isLoaded: boolean = false;

  constructor(
    private authService: AuthenticationService,
    private angularFireAuth: AngularFireAuth,
    private formBuilder: FormBuilder,
    private errorInterface: ErrorInterface,
    private router: Router
  ) { }

  loginGoogle(): void {
    this.authService.loginWithGoogle();
  }

  logout(): void {
    this.authService.logout();
  }

  ngOnInit() {
    this.angularFireAuth.authState
    .subscribe (user => {
      // if user is already logged in, it does not need to login again
      if(user) {
        this.router.navigate(['/']);
      } else {
        this.isLoaded = true;
      }
    });

    this.loginForm = this.formBuilder.group({
      email: new FormControl(''),
      password: new FormControl(''),
    });

    // Check if there are any errors in inserted data
    this.authService.eventAuthError$.subscribe( error => {
      this.errorInterface.setErrorMessage(error);
    })
  }

  onSubmit() {
    if(!this.loginForm.valid) {
      return;
    }

    if(!this.loginForm.get('email').value || !this.loginForm.get('password').value){
      this.errorInterface.setErrorMessage("Please fill in all the fields")
    }

    this.authService.loginWithEmail(this.loginForm.get('email').value,
      this.loginForm.get('password').value);
  }

}
