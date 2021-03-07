import { Injectable, OnInit } from '@angular/core';
import { AngularFireAuth } from '@angular/fire/auth';
import * as firebase from 'firebase/app';
import { AngularFirestore } from '@angular/fire/firestore';
import { Router } from '@angular/router';
import {BehaviorSubject, from, Observable, of} from 'rxjs';
import { UserModel } from 'src/app/models/user.model';
import { first, switchMap, take } from 'rxjs/operators';
import { UserController } from '../controllers/user-controller.service';
import 'rxjs/add/observable/fromPromise';
import 'rxjs/add/operator/mergeMap';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService  {

  private static CURRENT_USER_STORAGE_NAME: string = "currentUser";
  
  // For later
  private currentUserInfo: UserModel; 
  private currentUser: BehaviorSubject<UserModel>;

  private eventAuthError = new BehaviorSubject<string>("");
  eventAuthError$ = this.eventAuthError.asObservable();

  constructor(
    private angularFireAuth: AngularFireAuth,
    private db : AngularFirestore,
    private router: Router,
    private userService: UserController
  ) {
    this.currentUser = new BehaviorSubject<UserModel>(this.currentUserInfo);
  }

  loginWithGoogle() {
    this.angularFireAuth.signInWithPopup(new firebase.default.auth.GoogleAuthProvider)
      .then ( userCredential => {
        // send user info to backend
        if(userCredential) {
          userCredential.user.getIdTokenResult(false).then(token => {
            // let user: UserModel = {'email': userCredential.user.email, 'userId': userCredential.user.uid, 
            // 'displayName': userCredential.user.displayName,'firstName': "", 'lastName': "", 'role': "User",
            // 'pictureUrl': userCredential.user.photoURL};
            let user = this.createNewUser(userCredential);
            this.sendUserInfoToBackend(token.token, user);
          })
        }
      })
      .catch( error => {
        this.eventAuthError.next(error);
      });
  }

  loginWithEmail(email: string, password: string) {
    this.angularFireAuth.signInWithEmailAndPassword(email, password)
      .then ( userCredential => {
        if(userCredential) {
          userCredential.user.getIdTokenResult(false).then(token => {
            // let user: UserModel = {'email': userCredential.user.email, 'userId': userCredential.user.uid, 
            // 'displayName': userCredential.user.displayName,'firstName': "", 'lastName': "", 'role': "User"};
            let user = this.createNewUser(userCredential);
            this.sendUserInfoToBackend(token.token, user);
          })
          this.router.navigate(['/']);
        }
      })
      .catch( error => {
        this.eventAuthError.next(error);
      });
  }

  getLoggedInUser() : Observable<firebase.default.User> {
    return this.angularFireAuth.authState;
  }

  createUser(firstName: string, lastName: string, email: string, password: string) {
    this.angularFireAuth.createUserWithEmailAndPassword(email, password)
      .then( userCredential => {
        if(userCredential) {
          // let user: UserModel = {"userId": userCredential.user.uid, 'email': email, 'displayName': userCredential.user.displayName,
          // 'firstName': firstName, 'lastName': lastName, 'role': "User"};

          // send token and user info to backend
          let user = this.createNewUser(userCredential);
          userCredential.user.getIdTokenResult(false).then(token => {
            this.sendUserInfoToBackend(token.token, user);
          })
          
          userCredential.user.updateProfile({
            displayName: firstName + " " +  lastName,
          });
        }
      })
      .catch( error => {
        this.eventAuthError.next(error);
      })
  }

  getCurrentUser(): UserModel {
    let user: UserModel = JSON.parse(localStorage.getItem(AuthenticationService.CURRENT_USER_STORAGE_NAME));
    return user;
  }

  getToken(): Observable<string | null> {
    return this.angularFireAuth.authState.pipe(
      take(1),
      switchMap((user) => {
        if (user) {
          return from(user.getIdToken())
        }
        return of(null);
      })
    )
  }
  
  logout() {
    this.removeFromLocalStorage();
    this.angularFireAuth.signOut();
    this.router.navigateByUrl("/");
  }

  updateCurrentUserInfo(updatedUser: UserModel) {
    let user = this.getCurrentUser();
    if(user) {
      let hasFirstName = updatedUser.firstName && updatedUser.firstName.length != 0;
      let hasLastName = updatedUser.lastName && updatedUser.lastName.length != 0;
      let hasEmail = updatedUser.email && updatedUser.email.length != 0;
      let hasRole = updatedUser.role && updatedUser.role.length != 0;
      let hasDisplayName = updatedUser.displayName && updatedUser.displayName.length != 0;

      if(hasFirstName) {
        user.firstName = updatedUser.firstName;
      }

      if(hasLastName) {
        user.lastName = updatedUser.lastName
      }

      if(hasEmail) {
        user.email = updatedUser.email
        this.getLoggedInUser().toPromise().then(user => {
          user.updateEmail(updatedUser.email);
        })
      }

      if(hasRole) {
        user.role = updatedUser.role
      }

      if(hasDisplayName) {
        this.angularFireAuth.authState.subscribe(user => {
          user.updateProfile({
            displayName: updatedUser.displayName,
          })
        });
        user.displayName = updatedUser.displayName;
      }
      this.saveToLocalStorage(user);
    }
  }

  public isLoggedIn() {
    return this.angularFireAuth.authState.pipe(first()).toPromise();
  }

  private createNewUser(userCredential: firebase.default.auth.UserCredential): UserModel {
    return {'email': userCredential.user.email, 'userId': userCredential.user.uid, 
            'displayName': userCredential.user.displayName,'firstName': "", 'lastName': "", 'role': "User",
            'pictureUrl': userCredential.user.photoURL};
  }

  private sendUserInfoToBackend(token: string, user: UserModel) {
    this.userService.authenticateUser(token, user).subscribe(user => {
      this.saveToLocalStorage(user);
    },
    error => {
      this.removeFromLocalStorage();
      this.logout();
    });
  }

  private saveToLocalStorage(user: UserModel) {
    if(localStorage.getItem(AuthenticationService.CURRENT_USER_STORAGE_NAME)) {
      localStorage.removeItem(AuthenticationService.CURRENT_USER_STORAGE_NAME);
    }
    localStorage.setItem(AuthenticationService.CURRENT_USER_STORAGE_NAME, JSON.stringify(user));
  }

  private removeFromLocalStorage() {
    localStorage.removeItem(AuthenticationService.CURRENT_USER_STORAGE_NAME);
  }
}
