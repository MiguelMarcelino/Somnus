import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { ArticlesService } from 'src/app/services/controllers/articles-controller.service';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { ErrorInterface } from 'src/handlers/error-interface';
import { ArticleModel } from 'src/app/models/post/article.model';
import { PostModel } from 'src/app/models/post/post.model';
import { NewsPostModel } from 'src/app/models/post/news-post.model';
import { NewsPostService } from 'src/app/services/controllers/news-controller.service';
import { PostTypes } from 'src/app/models/post/post-types.enum';

@Component({
  selector: 'app-create-post-section',
  templateUrl: './create-post-section.component.html',
  styleUrls: ['./create-post-section.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class CreatePostSectionComponent implements OnInit {

  currentUser: firebase.default.User;
  editorForm: FormGroup;
  articleTypes: String[];
  loading = false;
  submitted = false;

  postType: PostTypes;
  post: ArticleModel | NewsPostModel;

  // from quill
  editorContent: String;

  constructor(
    private route: ActivatedRoute,
    private newsPostService: NewsPostService,
    private formBuilder: FormBuilder,
    private articleService: ArticlesService,
    private authenticationService: AuthenticationService,
    private router: Router,
    private errorInterface: ErrorInterface
  ) { }

  ngOnInit(): void {
    this.editorForm = this.formBuilder.group({
      'post_name': new FormControl(''),
      'description': new FormControl(''),
      'type': new FormControl('', Validators.minLength(0))
    });

    this.createArticleTypes();

    this.authenticationService.getLoggedInUser()
      .subscribe(user => {
        if (user) {
          this.currentUser = user;
        } else {
          this.router.navigateByUrl["/"];
        }
      });

    this.setupPost();
  }

  // Gets the post id from the params
  setupPost() {
    this.route.queryParamMap.subscribe((params: any) => {
      if(params.params.postType) {
        this.postType = params.params.postType;
      }
      if (params.params.id) {
        if(this.postType === PostTypes.article) {
          this.getArticle(params.params.id);
        } else if(this.postType === PostTypes.newsPost) {
          this.getNewsPost(params.params.id);
        }
      }
    });
  }

  // gets the article with a given id
  getArticle(id: string): void {
    this.articleService.getObject(id).subscribe((article: ArticleModel) => {
      if (article) {
        this.post = article;
        this.editorForm.get('post_name').setValue(article.postName);
        this.editorForm.get('description').setValue(article.description);
        this.editorForm.get('type').setValue(article.topic);
        this.setEditorContent(article.content);
      }
    })
  }

  getNewsPost(id: string): void {
    this.newsPostService.getObject(id).subscribe((newsPost: NewsPostModel) => {
      if(newsPost) {
        this.post = newsPost;
        this.editorForm.get('post_name').setValue(newsPost.postName);
        this.editorForm.get('description').setValue(newsPost.description);
        this.setEditorContent(newsPost.content);
      }
    })
  }

  get form() {
    return this.editorForm.controls;
  }
  
  isArticle(): boolean {
    return this.postType === PostTypes.article;
  }

  isNewsPost(): boolean {
    return this.postType === PostTypes.newsPost;
  }

  // Temporary
  createArticleTypes(): void {
    this.articleTypes = [];
    this.articleTypes.push("Physics", "Mathematics", "Computer Science", "Gaming"); // improve method
  }

  setEditorContent(editorContent: String) {
    this.editorContent = editorContent;
  }

  checkContent(): boolean {
    return (this.editorContent || this.editorForm.get('post_name').value);
  }

  canPublish() {
    let postName = this.editorForm.get('post_name').value;
    let content = this.editorContent;
    let description = this.editorForm.get('description').value;
    let topic = this.editorForm.get('type').value;

    if (!postName || !description || !content || (this.postType === PostTypes.article && !topic)) {
      return false;
    }

    return true;
  }

  sendArticleData(): void {
    this.submitted = true;

    // if (this.editorForm.invalid) {
    //   return;
    // }

    this.loading = true;

    let postName = this.editorForm.get('post_name').value;
    let content = this.editorContent;
    let description = this.editorForm.get('description').value;
    let topic = this.editorForm.get('type').value;

    if (!postName || !description || (this.postType === PostTypes.article && !topic)) {
      this.errorInterface.setErrorMessage("Please fill in all the fields");
      this.loading = false;
      return;
    }

    if (!content) {
      this.errorInterface.setErrorMessage("You cannot publish an article with no content");
      this.loading = false;
      return;
    }

    let postModel: PostModel = {
      "postName": postName, "authorUserName": this.currentUser.displayName, "userId": this.currentUser.uid, "description": description,
      "datePublished": new Date(), "lastUpdate": new Date(), 'content': content
    };

    // if it is an update, add the id
    if (this.post) {
      postModel.id = this.post.id;
    }

    if(this.postType === PostTypes.article) {
      let articleModel = postModel as ArticleModel;
      articleModel.topic = topic;
      this.articleService.addObject(articleModel).subscribe(id => {
        this.router.navigateByUrl("/articles");
        this.errorInterface.setSuccessMessage("Your Article has been successfully published!")
      },
        (error) => {
          this.loading = false;
          this.errorInterface.setErrorMessage("There was an error publishing your article!");
        });
    } else if(this.postType === PostTypes.newsPost) {
      let newsPostModel = postModel as NewsPostModel;
      
      this.newsPostService.addObject(newsPostModel).subscribe(id => {
        this.router.navigateByUrl("/news-posts");
        this.errorInterface.setSuccessMessage("Your News Post has been successfully published!")
      },
        (error) => {
          this.loading = false;
          this.errorInterface.setErrorMessage("There was an error publishing your News Post!");
        });
    }
    
  }

  navigateUp() {
    document.querySelector('html').scrollIntoView({ behavior: 'smooth' });
  }

  navigateDown() {
    document.querySelector('html').scrollTo({ top: document.documentElement.scrollHeight, behavior: 'smooth' });
  }

}
