import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, FormBuilder } from '@angular/forms';
import { ArticlesService } from 'src/app/services/controllers/articles-controller.service';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';

@Component({
  selector: 'app-create-articles-section',
  templateUrl: './create-articles-section.component.html',
  styleUrls: ['./create-articles-section.component.css']
})
export class CreateArticlesSectionComponent implements OnInit {

  currentUser: firebase.User;
  editorForm: FormGroup;
  editorStyle = {
    height: '400pt',
    backgroundColor: 'white',
    borderRadius: '4pt',
  };
  editorConfig = {
    toolbar: [
      ['bold', 'italic', 'underline', 'strike'],
      ['blockquote','code-block'],
      [{ 'list': 'ordered'}, { 'list': 'bullet' }],
      [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
      ['link'],
    ]
  }
  maxContentLength = 4000;

  // for preview section
  articleName: string;
  editorContent: string;

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
      'editor': new FormControl(''),
    }),
    this.authenticationService.getLoggedInUser()
      .subscribe (user => {
        this.currentUser = user;
    })
  }

  onSubmitPreview(): void {
    this.articleName = this.editorForm.get('article_name').value;
    this.editorContent = this.editorForm.get('editor').value;
  }

  maxLength(e: any):void {
    if(e.editor.getLength() > this.maxContentLength) {
      e.editor.deleteText(this.maxContentLength, e.editor.getLength());
    }
  }

  sendArticleData(): void {
    let artName = this.editorForm.get('article_name').value;
    let content = this.editorForm.get('editor').value;
    let description = this.editorForm.get('description').value;
    let articleModel = {"articleName": artName, "authorUserName": this.currentUser.displayName, "description": description, "content": content};
    this.articlesController.addObject(articleModel).subscribe(id => {
      this.router.navigateByUrl("/articles");
    });
  }
}
