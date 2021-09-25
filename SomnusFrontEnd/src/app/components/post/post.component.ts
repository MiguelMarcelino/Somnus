import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { ErrorInterface } from 'src/handlers/error-interface';
import { PostModel } from 'src/app/models/post/post.model';

@Component({
  template: ''
})
export abstract class PostComponent implements OnInit {

  // app user
  user: firebase.default.User;
  newsPost: PostModel;
  showCommentsSectionButton: boolean;

  constructor(
    authenticationService: AuthenticationService,
    route: ActivatedRoute,
    errorInterface: ErrorInterface,
    router: Router
  ) { }


  ngOnInit(): void {
    
  }

  compareDate(date1:Date, date2:Date): number {
    if(date1.getTime == date2.getTime) {
      return 0;
    } 
    if(date1.getTime > date2.getTime) {
      return 1;
    }
    if(date1.getTime < date2.getTime) {
      return -1;
    }
  }
}
