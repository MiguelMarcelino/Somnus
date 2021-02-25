import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ArticlesService } from 'src/app/services/controllers/articles-controller.service';
import { ArticleModel } from 'src/app/models/article.model';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { Role } from 'src/app/models/role.model';
import { UserModel } from 'src/app/models/user.model';
import { ErrorInterface } from 'src/handlers/error-interface';

@Component({
  selector: 'app-article-page',
  templateUrl: './article-page.component.html',
  styleUrls: ['./article-page.component.css'],
})
export class ArticlePageComponent implements OnInit {

  // app user
  user: firebase.default.User;
  private urlParams = {};
  article: ArticleModel;

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
    this.route.queryParams.subscribe(params => {
      this.urlParams = {...params};
    });
    this.articleService.getObject(id).subscribe((article: ArticleModel) =>{
      if(article) {
        this.article = article;
      }
    })
  }

  isArticleOwnerOrAdmin() {
    const currentUser: UserModel = this.authenticationService.getCurrentUser();
    return currentUser && (currentUser.role === Role.Admin ||
            currentUser.username === this.article.userId);
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
    this.router.navigate(["/createArticle"], {queryParams: {id: id}});
  }
}
