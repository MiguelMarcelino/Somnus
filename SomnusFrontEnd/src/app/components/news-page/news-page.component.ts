import { Component, HostListener, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NewsPostModel } from 'src/app/models/post/news-post.model';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { Role } from 'src/app/models/role.model';
import { UserModel } from 'src/app/models/user.model';
import { ErrorInterface } from 'src/handlers/error-interface';
import { animate, style, transition, trigger } from '@angular/animations';
import { NewsPostService } from 'src/app/services/controllers/news-controller.service';
import { PostTypes } from 'src/app/models/post/post-types.enum';

@Component({
  selector: 'app-news-page',
  templateUrl: './news-page.component.html',
  styleUrls: ['../post/post-page.component.scss'],
  animations: [
    trigger(
      'enterAnimation', [
        transition(':enter', [
          style({transform: 'translateX(100%)', opacity: 0}),
          animate('200ms', style({transform: 'translateX(0)', opacity: 1}))
        ]),
        transition(':leave', [
          style({transform: 'translateX(0)', opacity: 1}),
          animate('200ms', style({transform: 'translateX(100%)', opacity: 0}))
        ])
      ]
    )
  ],
})
export class NewsPageComponent implements OnInit {

  // app user
  user: firebase.default.User;
  private urlParams = {};
  newsPost: NewsPostModel;

  constructor(
    private route: ActivatedRoute,
    private newsPostService: NewsPostService,
    private authenticationService: AuthenticationService,
    private router: Router,
    private errorInterface: ErrorInterface,
  ) { }

  ngOnInit(): void {
    this.getNewsPost();
    this.authenticationService.getLoggedInUser()
      .subscribe (user => {
        this.user = user;
    });
  }

  getNewsPost(): void {
    const id = this.route.snapshot.paramMap.get('id');
    this.route.queryParamMap.subscribe((params: any) => {
      if(!params.params.isDeleted){
        this.newsPostService.getObject(id).subscribe((newsPost: NewsPostModel) =>{
          if(newsPost) {
            this.newsPost = newsPost;
            this.newsPost.isDeleted = false;
          }
        })
      } else {
        this.newsPostService.getDeletedNewsPost(id).subscribe((newsPost: NewsPostModel) =>{
          if(newsPost) {
            this.newsPost = newsPost;
            this.newsPost.isDeleted = true;
          }
        })
      }
      this.urlParams = {...params};
    });
  }

  isNewsPostOwnerOrAdmin() {
    const currentUser: UserModel = this.authenticationService.getCurrentUser();
    return currentUser && (currentUser.role === Role.Admin ||
            currentUser.userId === this.newsPost.userId);
  }

  deleteNewsPost() {
    const id = this.route.snapshot.paramMap.get('id');
    this.route.queryParams.subscribe(params => {
      this.urlParams = {...params};
    });
    this.newsPostService.deleteObject(id).subscribe(newsPost => {
      this.router.navigateByUrl("/news-posts");
      this.errorInterface.setSuccessMessage("Successfully deleted News Post!");
    });
  }

  editNewsPost() {
    const id = this.route.snapshot.paramMap.get('id');
    this.router.navigate(["/createPost"], {queryParams: {id: id, postType: PostTypes.newsPost}});
  }

  restoreNewsPost() {
    this.newsPostService.restoreNewsPost(this.newsPost.id).subscribe(post => {
      this.router.navigateByUrl("/user-profile");
    });
  }

}
