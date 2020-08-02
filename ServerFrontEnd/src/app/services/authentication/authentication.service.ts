import { Injectable, OnInit } from '@angular/core';
import { AngularFireAuth } from '@angular/fire/auth';
import { auth } from 'firebase';
import { AngularFirestore } from '@angular/fire/firestore';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';
import { UserModel } from 'src/app/models/user.model';
import { first } from 'rxjs/operators';

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
    private router: Router
  ) {

  }

  loginWithGoogle() {
    this.angularFireAuth.signInWithRedirect(new auth.GoogleAuthProvider)
      .then ( res => {
        
      })
      .catch( error => {
        this.eventAuthError.next(error);
      });
  }

  loginWithEmail(email: string, password: string) {
    this.angularFireAuth.signInWithEmailAndPassword(email, password)
      .then ( userCredential => {
        if(userCredential) {
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
  createUser(firstName: string, lastName: string, email: string, password: string, confirmPassword: string) {
    this.angularFireAuth.createUserWithEmailAndPassword(email, password)
      .then( userCredential => {
        this.newUser = {'email': email, 'firstName': firstName, 'lastName': lastName, 'role': ""};
        userCredential.user.updateProfile({
          displayName: firstName + " " +  lastName,
        })
        this.insertUserData(userCredential)
          .then(() => {
            this.router.navigate(['/']);
        })
      })
      .catch( error => {
        this.eventAuthError.next(error);
      })
  }

  insertUserData(userCredential: firebase.auth.UserCredential) {
    return this.db.doc(`Users/${userCredential.user.uid}`).set({
      email:this.newUser.email,
      firstname: this.newUser.firstName,
      lastname: this.newUser.lastName,
      role: 'website_user'
    })
  }

  logout() {
    this.angularFireAuth.signOut();
  }

  
  public isLoggedIn() {
    return this.angularFireAuth.authState.pipe(first()).toPromise();
  }
}
