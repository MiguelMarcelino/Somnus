import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { UserComment } from 'src/app/models/user-comment.model';
import { ErrorInterface } from 'src/handlers/error-interface';

@Component({
  selector: 'app-comments-section',
  templateUrl: './comments-section.component.html',
  styleUrls: ['./comments-section.component.scss']
})
export class CommentsSectionComponent implements OnInit {

  commentForm: FormGroup;
  userComments: UserComment[];

  constructor(
    private formBuilder: FormBuilder,
    private errorInterface: ErrorInterface
  ) { }

  ngOnInit(): void {
    this.commentForm = this.formBuilder.group({
      comment: new FormControl('')
    });
  }

  submitComment() {
    let comment: string = this.commentForm.get('comment').value;
    if(!comment || comment.length == 0) {
      this.errorInterface.setErrorMessage("You cannot submit an empty comment!");
    }

  }

}
