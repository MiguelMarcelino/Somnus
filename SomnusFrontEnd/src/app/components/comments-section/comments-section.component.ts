import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { UserComment } from 'src/app/models/user-comment.model';
import { ErrorInterface } from 'src/handlers/error-interface';
import { CommentService } from 'src/app/services/controllers/comment-controller.service';
import { ArticleModel } from 'src/app/models/article.model';
import { UserModel } from 'src/app/models/user.model';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { UserController } from 'src/app/services/controllers/user-controller.service';

@Component({
  selector: 'app-comments-section',
  templateUrl: './comments-section.component.html',
  styleUrls: ['./comments-section.component.scss']
})
export class CommentsSectionComponent implements OnInit {

  user: firebase.default.User;
  currentUser: UserModel;

  @Input()
  articleId: string;
  commentForm: FormGroup;
  userComments: UserComment[];
  currentSelectedComment: UserComment;


  constructor(
    private formBuilder: FormBuilder,
    private errorInterface: ErrorInterface,
    private commentService: CommentService,
    private authenticationService: AuthenticationService
  ) { }

  ngOnInit(): void {
    this.commentForm = this.formBuilder.group({
      comment: new FormControl(''),
      parentId: new FormControl('')
    });
    // this.getDummyComments();
    this.getUser();
    this.getComments();
  }

  getUser() {
    this.authenticationService.getLoggedInUser()
      .subscribe (user => {
        this.user = user;
        this.currentUser = this.authenticationService.getCurrentUser();
    });
  }

  getComments() {
    this.commentService.getCommentsFromArticle(this.articleId).subscribe(comments => {
      this.buildCommentArray(comments);
      console.log(comments);
    })
  }

  buildCommentArray(comments: UserComment[]) {
    this.userComments = comments;

    this.userComments.forEach(comment => {
      if(comment.parentId) {
        let tempComment = comment;
        this.userComments.splice(this.userComments.indexOf(comment), 1);
        this.insertChild(tempComment.parentId, tempComment);
      }
    })
  }

  submitComment() {
    let content: string = this.commentForm.get('comment').value;
    let parentId: string = this.commentForm.get('parentId').value;
    if(!content || content.length == 0) {
      this.errorInterface.setErrorMessage("You cannot submit an empty comment!");
    }

    let comment: UserComment = {"articleId": this.articleId, 
      "content": content};

    console.log(parentId);

    if(parentId) {
      comment.parentId = parentId;
    }
    this.commentService.addObject(comment).subscribe(comment => {
      this.errorInterface.setSuccessMessage("Successfully added your new comment!");
      if(parentId) {
        this.insertChild(parentId, comment);
      } else {
        this.userComments.push(comment);
      }
    }) ;
  }

  insertChild(parentId: string, newComment: UserComment) {
    this.userComments.forEach(comment => {
      if(comment.id === parentId) {
        if(!comment.responseComments) {
          comment.responseComments = [];
        } 
        comment.responseComments.push(newComment);
        return;
      }
      if(comment.responseComments) {
        this.insertChild(parentId, newComment);
      }
    });
  }

  leaveResponse(comment: UserComment) {
    this.commentForm.get("parentId").setValue(comment.id);
    this.currentSelectedComment = comment;
    this.navigateToCommentSection();
  }

  likeComment(comment: UserComment) {
    this.commentService.addLike(comment.id).subscribe(x => {
      comment.numLikes++;
      comment.isUserLikedComment=true;
    })
  }

  navigateToCommentSection() {
    document.querySelector('html').scrollTo({ top: document.getElementById("section").scrollHeight, behavior: 'smooth' });
  }

  isUserLikedComment(comment: UserComment) {
    if(this.currentUser) {
      return comment.isUserLikedComment;
    }
  }

}
