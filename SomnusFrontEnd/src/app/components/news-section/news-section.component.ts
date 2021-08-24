import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NewsPostModel } from 'src/app/models/post/news-post.model';
import { PostTypes } from 'src/app/models/post/post-types.enum';
import { Role } from 'src/app/models/role.model';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { NewsPostService } from 'src/app/services/controllers/news-controller.service';
import { ErrorInterface } from 'src/handlers/error-interface';

@Component({
  selector: 'app-news-section',
  templateUrl: './news-section.component.html',
  styleUrls: ['../post/post-section.component.scss']
})
export class NewsSectionComponent implements OnInit {

  // app user
  user: firebase.default.User;

  newsPosts: NewsPostModel[] = [];
  noNewsPosts: boolean = false;
  loading: boolean = true;
  canCreateNewsPosts = false;

  constructor(
    private newsPostService: NewsPostService,
    private authenticationService: AuthenticationService,
    private route: ActivatedRoute,
    private errorInterface: ErrorInterface,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.route.queryParamMap.subscribe((params: any) => {
      if(!params.params.value){
        this.getNewsPosts();
      } else {
        // this.getSearchArticles(params.params.value);
      }
    });
    this.authenticationService.getLoggedInUser()
      .subscribe (user => {
        this.user = user;
        const currentUser = this.authenticationService.getCurrentUser();
        if(currentUser && currentUser.role) {
          if(currentUser.role === Role.Admin || currentUser.role === Role.Editor || currentUser.role == Role.Manager) {
            this.canCreateNewsPosts = true;
          }
        } 
    });
  }

  getNewsPosts(): void {
    this.newsPostService.getAll().subscribe(allNewsPosts => {
      this.newsPosts = allNewsPosts;
      this.loading = false;
      if(!allNewsPosts || allNewsPosts.length == 0) {
        this.noNewsPosts = true;
      }
    },
    (error)=>{
      this.loading = false;
    });
  }

  // getSearchArticles(value: string) {
  //   this.newsPostService.searchArticles(value).subscribe(articles => {
  //     this.articles = articles;
  //     this.loading = false;
  //     if(!articles || articles.length == 0) {
  //       this.noArticles = true;
  //     }
  //   },
  //   (error)=>{
  //     this.loading = false;
  //   });
  // }

  getNewsPostLink(newsPost: NewsPostModel) {
    return "/news-post/" + newsPost.id;
  }

  createNewsPost() {
    this.router.navigate(["/createPost"], {queryParams: {postType: PostTypes.newsPost}});
  }

}
