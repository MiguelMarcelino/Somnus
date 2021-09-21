import { Component, HostListener, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ArticlesService } from 'src/app/services/controllers/articles-controller.service';
import { ArticleModel } from 'src/app/models/post/article.model';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { Role } from 'src/app/models/role.model';
import { UserModel } from 'src/app/models/user.model';
import { ErrorInterface } from 'src/handlers/error-interface';
import { animate, style, transition, trigger } from '@angular/animations';
import { PostTypes } from 'src/app/models/post/post-types.enum';

@Component({
  selector: 'app-article-page',
  templateUrl: './article-page.component.html',
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
export class ArticlePageComponent implements OnInit {

  // app user
  user: firebase.default.User;
  private urlParams = {};
  article: ArticleModel;
  showCommentsSectionButton: boolean;

  constructor(
    private route: ActivatedRoute,
    private articleService: ArticlesService,
    private authenticationService: AuthenticationService,
    private router: Router,
    private errorInterface: ErrorInterface,
  ) { }

  ngOnInit(): void {
    this.getArticle();
    this.authenticationService.getLoggedInUser()
      .subscribe (user => {
        this.user = user;
    });
  }

  getArticle(): void {
    const id = this.route.snapshot.paramMap.get('id');
    this.route.queryParamMap.subscribe((params: any) => {
      if(!params.params.isDeleted){
        this.articleService.getObject(id).subscribe((article: ArticleModel) =>{
          if(article) {
            this.article = article;
            this.article.isDeleted = false;
          }
        })
      } else {
        this.articleService.getDeletedArticle(id).subscribe((article: ArticleModel) =>{
          if(article) {
            this.article = article;
            this.article.isDeleted = true;
          }
        })
      }
      this.urlParams = {...params};
    });
  }

  isArticleOwnerOrAdmin() {
    const currentUser: UserModel = this.authenticationService.getCurrentUser();
    return currentUser && (currentUser.role === Role.Admin ||
            currentUser.userId === this.article.userId);
  }

  deleteArticle() {
    const id = this.route.snapshot.paramMap.get('id');
    this.route.queryParams.subscribe(params => {
      this.urlParams = {...params};
    });
    this.articleService.deleteObject(id).subscribe(article => {
      this.router.navigateByUrl("/articles");
      this.errorInterface.setSuccessMessage("Successfully deleted Article!")
    });
  }

  editArticle() {
    const id = this.route.snapshot.paramMap.get('id');
    this.router.navigate(["/createPost"], {queryParams: {id: id, postType: PostTypes.article}});
  }

  restoreArticle() {
    this.articleService.restoreArticle(this.article.id).subscribe(post => {
      this.router.navigateByUrl("/user-profile");
    });
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
