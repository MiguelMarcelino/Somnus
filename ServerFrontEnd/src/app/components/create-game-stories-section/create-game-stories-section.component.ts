import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { GamesService } from 'src/app/services/controllers/games-controller.service';

@Component({
  selector: 'app-create-game-stories-section',
  templateUrl: './create-game-stories-section.component.html',
  styleUrls: ['./create-game-stories-section.component.css']
})
export class CreateGameStoriesSectionComponent implements OnInit {

  currentUser: firebase.User;
  editorForm: FormGroup;
  publishError: any;
  loading = false;
  submitted = false;

  // from quill-editor
  editorContent: String;

  constructor(
    private formBuilder: FormBuilder,
    private gamesController: GamesService,
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

  get form() {
    return this.editorForm.controls;
  }

  checkContent(): boolean {
    return (this.editorContent || this.editorForm.get('article_name').value);
  }

  setEditorContent(editorContent: String) {
    this.editorContent = editorContent;
  }

  canPublish() {
    let artName = this.editorForm.get('article_name').value;
    let content = this.editorContent;
    let description = this.editorForm.get('description').value;

    if(!artName || !content || !description ) {
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
    let type = "Gaming";

    if(!artName || !content || !description) {
      this.publishError="Please fill in all the necessary fields"
    }

    let articleModel = {"articleName": artName, "authorUserName": this.currentUser.displayName, "description": description, 
      'datePublished': new Date(), 'type': type,'content': content};
    this.gamesController.addObject(articleModel).subscribe(id => {
      this.router.navigateByUrl("/gaming");
    },
    (error) => {
      // Template message
      this.publishError = "Something went wrong!";
    }
    );
  }
  
}
