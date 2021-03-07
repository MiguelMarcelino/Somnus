import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { UserComment } from 'src/app/models/user-comment.model';
import { ErrorInterface } from 'src/handlers/error-interface';
import { CommentService } from 'src/app/services/controllers/comment-controller.service';
import { ArticleModel } from 'src/app/models/article.model';
import { UserModel } from 'src/app/models/user.model';

@Component({
  selector: 'app-comments-section',
  templateUrl: './comments-section.component.html',
  styleUrls: ['./comments-section.component.scss']
})
export class CommentsSectionComponent implements OnInit {

  @Input()
  articleId: string;

  commentForm: FormGroup;
  userComments: UserComment[];

  constructor(
    private formBuilder: FormBuilder,
    private errorInterface: ErrorInterface,
    private commenService: CommentService
  ) { }

  ngOnInit(): void {
    this.commentForm = this.formBuilder.group({
      comment: new FormControl(''),
      parentId: new FormControl(''),
    });
  }

  submitComment() {
    let content: string = this.commentForm.get('comment').value;
    let parentId: string = this.commentForm.get('parentId').value;
    if(!content || content.length == 0) {
      this.errorInterface.setErrorMessage("You cannot submit an empty comment!");
    }

    let comment: UserComment = {"articleId": this.articleId, 
      "content": content}

    if(parentId) {
      comment.parentId = parentId;
    }
    this.commenService.addObject(comment);
  }

}
