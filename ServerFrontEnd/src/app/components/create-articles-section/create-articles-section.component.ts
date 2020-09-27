import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormControl, FormBuilder } from '@angular/forms';
import { ArticlesService } from 'src/app/services/controllers/articles-controller.service';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { QuillEditorComponent } from 'ngx-quill';

@Component({
  selector: 'app-create-articles-section',
  templateUrl: './create-articles-section.component.html',
  styleUrls: ['./create-articles-section.component.css']
})
export class CreateArticlesSectionComponent implements OnInit {

  currentUser: firebase.User;
  editorForm: FormGroup;
  publishError: any;
  articleTypes: String[];
  loading = false;
  submitted = false;
  
  // from quill
  editorContent: String;

  constructor(
    private formBuilder: FormBuilder,
    private articlesController: ArticlesService,
    private authenticationService: AuthenticationService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.editorForm = this.formBuilder.group({
      'article_name': new FormControl(''),
      'description': new FormControl(''),
      'type': new FormControl('')
    }),
    this.authenticationService.getLoggedInUser()
      .subscribe (user => {
        this.currentUser = user;
    });
    this.createArticleTypes();
  }

  get form() {
    return this.editorForm.controls;
  }

  // Temporary
  createArticleTypes(): void {
    this.articleTypes = [];
    this.articleTypes.push("Physics", "Chemistry", "Mathematics", "Computer Science"); // fail!
  }

  setEditorContent(editorContent: String) {
    this.editorContent = editorContent;
  }

  checkContent(): boolean {
    return (this.editorContent || this.editorForm.get('article_name').value);
  }

  canPublish() {
    let artName = this.editorForm.get('article_name').value;
    let content = this.editorContent;
    let description = this.editorForm.get('description').value;
    let type = this.editorForm.get('type').value;

    if(!artName || !content || !description || !type ) {
      return false;
    }

    return true;
  }

  sendArticleData(): void {
    this.submitted = true;

    if(this.editorForm.invalid) {
      return;
    }

    this.loading = true;

    let artName = this.editorForm.get('article_name').value;
    let content = this.editorContent;
    let description = this.editorForm.get('description').value;
    let type = this.editorForm.get('type').value;

    if(!artName || !content || !description || !type) {
      this.publishError="Please fill in all the necessary fields"
    }

    let articleModel = {"articleName": artName, "authorUserName": this.currentUser.displayName, "description": description, 
      datePublished: new Date(), 'type': type, 'content': content};
    this.articlesController.addObject(articleModel).subscribe(id => {
      this.router.navigateByUrl("/articles");
    },
    (error) => {
      // Template message
      this.publishError = "Something went wrong!";
    }
    );
  }

}
