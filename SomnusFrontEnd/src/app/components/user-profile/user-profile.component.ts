import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ArticleModel } from 'src/app/models/post/article.model';
import { NewsPostModel } from 'src/app/models/post/news-post.model';
import { Role } from 'src/app/models/role.model';
import { UserModel } from 'src/app/models/user.model';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { ArticlesService } from 'src/app/services/controllers/articles-controller.service';
import { NewsPostService } from 'src/app/services/controllers/news-controller.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {

  // app user
  user: firebase.default.User;
  userInfo: UserModel;
  deletedArticles: ArticleModel[];
  deletedNewsPosts: NewsPostModel[];

  constructor(
    private authenticationService: AuthenticationService,
    private articleService: ArticlesService,
    private newsPostService: NewsPostService,
    private router: Router,
  ) { }

  ngOnInit(): void {
    this.authenticationService.getLoggedInUser()
      .subscribe (user => {
        this.user = user;
    });
    this.userInfo = this.authenticationService.getCurrentUser();
    this.getDeletedArticles();
    this.getDeletedNewsPosts();
  }

  isAdmin() {
    if(this.userInfo) {
      return this.userInfo.role === Role.Admin;
    }
    return false;
  }

  getDeletedArticles(): void {
    this.articleService.getDeletedArticles().subscribe(allArticles => {
      this.deletedArticles = allArticles;
    });
  }

  restoreArticle(id: string) {
    this.articleService.restoreArticle(id).subscribe(post => {
      this.router.navigateByUrl("/user-profile");
    });
  }

  getDeletedNewsPosts(): void {
    this.newsPostService.getDeletedNewsPosts().subscribe(allNewsPosts => {
      this.deletedNewsPosts = allNewsPosts;
    });
  }

  restoreNewsPost(id: string) {
    this.newsPostService.restoreNewsPost(id).subscribe(post => {
      this.router.navigateByUrl("/user-profile");
    });
  }

}
