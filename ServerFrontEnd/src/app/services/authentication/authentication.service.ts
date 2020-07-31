import { Injectable, OnInit } from '@angular/core';
import { AngularFireAuth } from '@angular/fire/auth';
import { auth } from 'firebase';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService  {

  constructor(
    private angularFireAuth: AngularFireAuth,
  ) { }

  loginWithGoogle() {
    this.angularFireAuth.signInWithRedirect(new auth.GoogleAuthProvider);
  }

  loginWithEmail(email: String, password: String) {
    this.angularFireAuth.signInWithRedirect(new auth.EmailAuthProvider);
  }

  logout() {
    this.angularFireAuth.signOut();
  }

  getLoggedInUser() {
    return this.angularFireAuth.authState;
  }

}
