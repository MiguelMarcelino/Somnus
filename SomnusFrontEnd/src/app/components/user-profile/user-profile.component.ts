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
  noDeletedArticles: boolean = false;
  noDeletedNewsPosts: boolean = false;

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
      if(!allArticles || (allArticles && allArticles.length == 0)) {
        this.noDeletedArticles = true;
      }
    });
  }

  getDeletedNewsPosts(): void {
    this.newsPostService.getDeletedNewsPosts().subscribe(allNewsPosts => {
      this.deletedNewsPosts = allNewsPosts;
      if(!allNewsPosts || (allNewsPosts && allNewsPosts.length == 0)) {
        this.noDeletedNewsPosts = true;
      }
    });
  }

  navigateNewsPostWithIsDeletedInfo(deletedArticleId, normalizedName) {
    this.router.navigate(["/news-post/" + deletedArticleId + "/" + normalizedName], {queryParams: {isDeleted: "true"}});
  }

  navigateArticlesWithIsDeletedInfo(deletedArticleId, normalizedTopic, normalizedName) {
    this.router.navigate(["/article/" + deletedArticleId + "/" + normalizedTopic + "/" +  normalizedName], {queryParams: {isDeleted: "true"}});
  }

}
