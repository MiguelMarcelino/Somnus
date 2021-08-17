import { Component, HostListener, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NewsPostModel } from 'src/app/models/post/news-post.model';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { Role } from 'src/app/models/role.model';
import { UserModel } from 'src/app/models/user.model';
import { ErrorInterface } from 'src/handlers/error-interface';
import { animate, style, transition, trigger } from '@angular/animations';
import { NewsPostService } from 'src/app/services/controllers/news-controller.service';
import { PostModel } from 'src/app/models/post/post.model';
import { TemplateControllerService } from 'src/app/services/controllers/template-controller.service';

// TODO: Make generic post component class
export class PostComponent implements OnInit {

  // app user
  user: firebase.default.User;
  private urlParams = {};
  newsPost: PostModel;
  showCommentsSectionButton: boolean;

  constructor(
    private route: ActivatedRoute,
    private postService: NewsPostService,
    private authenticationService: AuthenticationService,
    private router: Router,
    private errorInterface: ErrorInterface,
  ) { }

  ngOnInit(): void {
    this.getPost();
    this.authenticationService.getLoggedInUser()
      .subscribe (user => {
        this.user = user;
    });
  }

  getPost(): void {
    const id = this.route.snapshot.paramMap.get('id');
    this.route.queryParams.subscribe(params => {
      this.urlParams = {...params};
    });
    this.postService.getObject(id).subscribe((article: NewsPostModel) =>{
      if(article) {
        this.newsPost = article;
      }
    })
  }

  isPostOwnerOrAdmin() {
    const currentUser: UserModel = this.authenticationService.getCurrentUser();
    return currentUser && (currentUser.role === Role.Admin ||
            currentUser.userId === this.newsPost.userId);
  }

  // deletePost() {
  //   const id = this.route.snapshot.paramMap.get('id');
  //   this.route.queryParams.subscribe(params => {
  //     this.urlParams = {...params};
  //   });
  //   this.articleService.deleteObject(id).subscribe(article => {
  //     this.router.navigateByUrl("/articles");
  //     this.errorInterface.setSuccessMessage("Successfully deleted Article!")
  //   });
  // }

  // editnewsPost() {
  //   const id = this.route.snapshot.paramMap.get('id');
  //   this.router.navigate(["/createArticle"], {queryParams: {id: id}});
  // }

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
