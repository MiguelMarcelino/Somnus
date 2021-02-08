import { Injectable, OnInit } from '@angular/core';
import { AngularFireAuth } from '@angular/fire/auth';
import { auth } from 'firebase';
import { AngularFirestore } from '@angular/fire/firestore';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';
import { UserModel } from 'src/app/models/user.model';
import { first } from 'rxjs/operators';
import { UserController } from '../controllers/user-controller.service';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService  {

  private eventAuthError = new BehaviorSubject<string>("");
  eventAuthError$ = this.eventAuthError.asObservable();
  newUser: UserModel;

  constructor(
    private angularFireAuth: AngularFireAuth,
    private db : AngularFirestore,
    private router: Router,
    private userService: UserController
  ) {

  }

  loginWithGoogle() {
    this.angularFireAuth.signInWithPopup(new auth.GoogleAuthProvider)
      .then ( userCredential => {
        // send user info to backend
        if(userCredential) {
          userCredential.user.getIdToken(false).then(token => {
            this.sendUserInfoToBackend(token);
          })
        }
      }
        
      )
      .catch( error => {
        this.eventAuthError.next(error);
      });
  }

  sendUserInfoToBackend(token: string) {
    this.userService.authenticateUser(token).subscribe(user => {
      this.newUser = user;
      localStorage.setItem("token", token);
    })
  }

  getToken() {
    if(!localStorage.getItem("token")) {
      this.getLoggedInUser().toPromise().then(user => {
        user.getIdToken().then(token => {
          localStorage.setItem("token", token);
        })
      })
    }
  }

  loginWithEmail(email: string, password: string) {
    this.angularFireAuth.signInWithEmailAndPassword(email, password)
      .then ( userCredential => {
        if(userCredential) {
          userCredential.user.getIdToken(false).then(token => {
            this.sendUserInfoToBackend(token);
          })
          this.router.navigate(['/']);
        }
      })
      .catch( error => {
        this.eventAuthError.next(error);
      });
  }

  getLoggedInUser() {
    return this.angularFireAuth.authState;
  }

  // Register new User
  createUser(username: string, firstName: string, lastName: string, email: string, password: string, confirmPassword: string) {
    this.angularFireAuth.createUserWithEmailAndPassword(email, password)
      .then( userCredential => {
        if(userCredential) {
          // send token info to backend
          userCredential.user.getIdToken(false).then(token => {
            this.sendUserInfoToBackend(token);
          })

          // save new user info
          // this.newUser = {'email': email, 'username': username, 'firstName': firstName, 'lastName': lastName, 'role': "User"};
          userCredential.user.updateProfile({
            displayName: firstName + " " +  lastName,
          })
          this.insertUserData(userCredential)
            .then(() => {
              this.router.navigate(['/']);
          })
        }
      })
      .catch( error => {
        this.eventAuthError.next(error);
      })
  }

  insertUserData(userCredential: firebase.auth.UserCredential) {
    return this.db.doc(`Users/${userCredential.user.uid}`).set({
      email:this.newUser.email,
      firstname: this.newUser.username,
      lastname: this.newUser.lastName,
      role: 'website_user'
    })
  }

  logout() {
    localStorage.removeItem("token");
    this.angularFireAuth.signOut();
  }

  
  public isLoggedIn() {
    return this.angularFireAuth.authState.pipe(first()).toPromise();
  }
}
