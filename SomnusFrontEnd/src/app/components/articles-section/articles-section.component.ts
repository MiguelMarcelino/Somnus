import { Component, OnInit } from '@angular/core';
import { ArticleModel } from 'src/app/models/post/article.model';
import { ArticlesService } from 'src/app/services/controllers/articles-controller.service';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ErrorInterface } from 'src/handlers/error-interface';
import { Role } from 'src/app/models/role.model';

@Component({
  selector: 'app-articles-section',
  templateUrl: './articles-section.component.html',
  styleUrls: ['./articles-section.component.scss']
})
export class ArticlesSectionComponent implements OnInit {

  // app user
  user: firebase.default.User;

  articles: ArticleModel[] = [];
  noArticles: boolean = false;
  loading: boolean = true;
  canCreateArticles = false;

  constructor(
    private articleService: ArticlesService,
    private authenticationService: AuthenticationService,
    private route: ActivatedRoute,
    private errorInterface: ErrorInterface,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.route.queryParamMap.subscribe((params: any) => {
      if(!params.params.value){
        this.getArticles();
      } else {
        this.getSearchArticles(params.params.value);
      }
    });
    this.authenticationService.getLoggedInUser()
      .subscribe (user => {
        this.user = user;
        const currentUser = this.authenticationService.getCurrentUser();
        if(currentUser && currentUser.role) {
          if(currentUser.role === Role.Admin || currentUser.role === Role.Editor || currentUser.role == Role.Manager) {
            this.canCreateArticles = true;
          }
        } 
    });
  }

  getArticles(): void {
    this.articleService.getAll().subscribe(allArticles => {
      this.articles = allArticles;
      this.loading = false;
      if(!allArticles || allArticles.length == 0) {
        this.noArticles = true;
      }
    },
    (error)=>{
      this.loading = false;
    });
  }

  getSearchArticles(value: string) {
    this.articleService.searchArticles(value).subscribe(articles => {
      this.articles = articles;
      this.loading = false;
      if(!articles || articles.length == 0) {
        this.noArticles = true;
      }
    },
    (error)=>{
      this.loading = false;
    });
  }

  getArticleLink(article: ArticleModel) {
    return "/article/" + article.id;
  }

  createArticle() {
    this.router.navigate(["/createPost"], {queryParams: {postType: PostTypes.article}});
  }

}
