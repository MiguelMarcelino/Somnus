import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { AngularFireAuth } from '@angular/fire/auth';
import { Router } from '@angular/router';
import { ErrorInterface } from 'src/handlers/error-interface';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerForm: FormGroup;
  authError: any;
  isLoaded: boolean = false;

  constructor(
    private authservice: AuthenticationService,
    private angularFireAuth: AngularFireAuth,
    private router: Router,
    private errorInterface: ErrorInterface,
    private formBuilder: FormBuilder,
  ) { }

  ngOnInit(): void {
    this.angularFireAuth.authState
    .subscribe (user => {
      // if user is already logged in, it does not need to login again
      if(user) {
        this.router.navigate(['/']);
      } else {
        this.isLoaded = true;
      }
    });

    this.registerForm = this.formBuilder.group({
      'first_name': new FormControl(''),
      'last_name': new FormControl(''),
      'email': new FormControl(''),
      'password': new FormControl(''),
      'confirm_password': new FormControl('')
    });
    this.authservice.eventAuthError$.subscribe( error => {
      this.errorInterface.setErrorMessage(error);
    })
  }

  onSubmitRegister() {
    if(!this.registerForm.valid) {
      return
    }

    if(!this.registerForm.get('first_name').value ||
    this.registerForm.get('last_name').value ||
    !this.registerForm.get('email').value ||
    !this.registerForm.get('password').value ||
    !this.registerForm.get('confirm_password').value) {
      this.errorInterface.setErrorMessage("Please fill in all the fields")
    }

    if(!(this.registerForm.get('password').value === this.registerForm.get('confirm_password').value)) {
      this.errorInterface.setErrorMessage("Please check that both of your passwords match") 
    }

    let firstName = this.registerForm.get('first_name').value;
    let lastName = this.registerForm.get('last_name').value;
    let email = this.registerForm.get('email').value;
    let password = this.registerForm.get('password').value;
    this.authservice.createUser(firstName, lastName, email, password);
  }

}
