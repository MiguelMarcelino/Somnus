import { Component, HostListener, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NewsPostModel } from 'src/app/models/post/news-post.model';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { Role } from 'src/app/models/role.model';
import { UserModel } from 'src/app/models/user.model';
import { ErrorInterface } from 'src/handlers/error-interface';
import { animate, style, transition, trigger } from '@angular/animations';
import { NewsPostService } from 'src/app/services/controllers/news-controller.service';

@Component({
  selector: 'app-news-page',
  templateUrl: './news-page.component.html',
  styleUrls: ['./news-page.component.scss'],
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
  showCommentsSectionButton: boolean;

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
    this.route.queryParams.subscribe(params => {
      this.urlParams = {...params};
    });
    this.newsPostService.getObject(id).subscribe((article: NewsPostModel) =>{
      if(article) {
        this.newsPost = article;
      }
    })
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
    // this.articleService.deleteObject(id).subscribe(article => {
    //   this.router.navigateByUrl("/articles");
    //   this.errorInterface.setSuccessMessage("Successfully deleted Article!")
    // });
  }

  editNewsPost() {
    const id = this.route.snapshot.paramMap.get('id');
    this.router.navigate(["/createArticle"], {queryParams: {id: id}});
  }

  navigateToCommentSection() {
    document.getElementById("c-section").scrollIntoView({ behavior: 'smooth' });
  }

  @HostListener("window:scroll", ["$event"])
  showCommentsButton(event?) {
    if(document.getElementById("c-section")) {
      this.showCommentsSectionButton = this.user && (document.getElementById("c-section").offsetTop - event.srcElement.children[0].scrollTop) > 900 && 
        (document.getElementById("c-section").offsetTop - event.srcElement.children[0].scrollTop) > 200;
    }
  }

}
