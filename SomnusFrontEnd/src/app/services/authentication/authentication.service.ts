import { Injectable, OnInit } from '@angular/core';
import { AngularFireAuth } from '@angular/fire/auth';
import * as firebase from 'firebase';
import { AngularFirestore } from '@angular/fire/firestore';
import { Router } from '@angular/router';
import {BehaviorSubject} from 'rxjs';
import { UserModel } from 'src/app/models/user.model';
import { first } from 'rxjs/operators';
import { UserController } from '../controllers/user-controller.service';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService  {

  private static CURRENT_USER_STORAGE_NAME: string = "currentUser";
  private static TOKEN_STORAGE_NAME: string = "token";

  private eventAuthError = new BehaviorSubject<string>("");
  eventAuthError$ = this.eventAuthError.asObservable();
  currentUser: UserModel;

  constructor(
    private angularFireAuth: AngularFireAuth,
    private db : AngularFirestore,
    private router: Router,
    private userService: UserController
  ) {  }

  loginWithGoogle() {
    this.angularFireAuth.signInWithPopup(new firebase.default.auth.GoogleAuthProvider)
      .then ( userCredential => {
        // send user info to backend
        if(userCredential) {
          userCredential.user.getIdTokenResult(false).then(token => {
            this.sendUserInfoToBackend(token.token, token.expirationTime);
          })
        }
      })
      .catch( error => {
        this.eventAuthError.next(error);
      });
  }

  sendUserInfoToBackend(token: string, expirationTime: string) {
    this.userService.authenticateUser(token).subscribe(user => {
      this.currentUser = user;
      this.saveToLocalStorage(token, user);
    },
    error => {
      this.removeFromLocalStorage();
      this.logout();
    });
  }

  loginWithEmail(email: string, password: string) {
    this.angularFireAuth.signInWithEmailAndPassword(email, password)
      .then ( userCredential => {
        if(userCredential) {
          userCredential.user.getIdTokenResult(false).then(token => {
            this.sendUserInfoToBackend(token.token, token.expirationTime);
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

  createUser(username: string, firstName: string, lastName: string, email: string, password: string, 
      confirmPassword: string) {
    this.angularFireAuth.createUserWithEmailAndPassword(email, password)
      .then( userCredential => {
        if(userCredential) {
          // send token and user info to backend
          let user = {'email': email, 'username': username, 'firstName': firstName, 
           'lastName': lastName, 'role': "User"};
          userCredential.user.getIdTokenResult(false).then(token => {
            this.registerUserBackend(token.token, token.expirationTime, user);
          })
          
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

  insertUserData(userCredential: firebase.default.auth.UserCredential) {
    return this.db.doc(`Users/${userCredential.user.uid}`).set({
      email:this.currentUser.email,
      firstname: this.currentUser.username,
      lastname: this.currentUser.lastName,
      role: 'website_user'
    })
  }

  getCurrentUser(): UserModel {
    let user: UserModel = JSON.parse(localStorage.getItem(AuthenticationService.CURRENT_USER_STORAGE_NAME));
    return user;
  }

  logout() {
    this.removeFromLocalStorage();
    this.angularFireAuth.signOut();
  }

  public isLoggedIn() {
    return this.angularFireAuth.authState.pipe(first()).toPromise();
  }

  private registerUserBackend(token: string, expirationTime: string, user: UserModel) {
    this.userService.registerUser(token, user).subscribe(user => {
      this.saveToLocalStorage(token, user);
    },
    error => {
      this.removeFromLocalStorage();
      this.logout();
    });
  }

  private saveToLocalStorage(token: string, user: UserModel) {
    localStorage.setItem(AuthenticationService.TOKEN_STORAGE_NAME, token);
    localStorage.setItem(AuthenticationService.CURRENT_USER_STORAGE_NAME, JSON.stringify(user));
  }

  private removeFromLocalStorage() {
    localStorage.removeItem(AuthenticationService.TOKEN_STORAGE_NAME);
    localStorage.removeItem(AuthenticationService.CURRENT_USER_STORAGE_NAME);
  }

  // checkExpirationTime(): string {
  //   this.getLoggedInUser().toPromise().then(user => {
  //     user.getIdTokenResult().then(token => {
  //       token.expirationTime;
  //       // if(Date.parse(token.expirationTime) < Date.now() ) {
  //       //   this.logout();
  //       //   localStorage.removeItem("token");
  //       // }
  //     });
  //   })
  // }
}
