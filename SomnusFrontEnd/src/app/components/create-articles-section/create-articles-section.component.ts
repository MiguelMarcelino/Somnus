import { Component, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { FormGroup, FormControl, FormBuilder } from '@angular/forms';
import { ArticlesService } from 'src/app/services/controllers/articles-controller.service';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { QuillEditorComponent } from 'ngx-quill';
import { ErrorInterface } from 'src/handlers/error-interface';

@Component({
  selector: 'app-create-articles-section',
  templateUrl: './create-articles-section.component.html',
  styleUrls: ['./create-articles-section.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class CreateArticlesSectionComponent implements OnInit {

  currentUser: firebase.default.User;
  editorForm: FormGroup;
  articleTypes: String[];
  loading = false;
  submitted = false;
  
  // from quill
  editorContent: String;

  constructor(
    private formBuilder: FormBuilder,
    private articlesController: ArticlesService,
    private authenticationService: AuthenticationService,
    private router: Router,
    private errorInterface: ErrorInterface
  ) { }

  ngOnInit(): void {
    this.editorForm = this.formBuilder.group({
      'article_name': new FormControl(''),
      'description': new FormControl(''),
      'type': new FormControl('')
    }),
    this.authenticationService.getLoggedInUser()
      .subscribe (user => {
        if(user) {
          this.currentUser = user;
        } else {
          this.router.navigateByUrl["/articles"];
        }
    });
    this.createArticleTypes();
  }

  get form() {
    return this.editorForm.controls;
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
    return (this.editorContent || this.editorForm.get('article_name').value);
  }

  canPublish() {
    let artName = this.editorForm.get('article_name').value;
    let content = this.editorContent;
    let description = this.editorForm.get('description').value;
    let type = this.editorForm.get('type').value;

    if(!artName || !content || !description || !type ) {
      //this.errorInterface.setErrorMessage("Please check if you have entered all fields");
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
    let topic = this.editorForm.get('type').value;

    if(!artName || !description || !topic) {
      this.errorInterface.setErrorMessage("Please fill in all the fields");
      this.loading = false;
      return;
    }

    if(!content){
      this.errorInterface.setErrorMessage("You cannot publish an article with no content");
      this.loading = false;
      return;
    }

    let articleModel = {"articleName": artName, "authorUserName": this.currentUser.displayName, "userId": this.currentUser.uid, "description": description, 
      datePublished: new Date(), 'topic': topic, 'content': content};
    this.articlesController.addObject(articleModel).subscribe(id => {
      this.router.navigateByUrl("/articles");
    },
    (error)=>{
      this.loading = false;
    });
  }

}
