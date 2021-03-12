import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { UserComment } from 'src/app/models/user-comment.model';
import { ErrorInterface } from 'src/handlers/error-interface';
import { CommentService } from 'src/app/services/controllers/comment-controller.service';
import { ArticleModel } from 'src/app/models/article.model';
import { UserModel } from 'src/app/models/user.model';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { UserController } from 'src/app/services/controllers/user-controller.service';
import { Role } from 'src/app/models/role.model';

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
  currentlyEditingComment: UserComment;

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
    this.getUser();
  }

  getUser() {
    this.authenticationService.getLoggedInUser()
      .subscribe (user => {
        this.user = user;
        this.currentUser = this.authenticationService.getCurrentUser();

        // if there is a user, get the comments for the article
        if(user) {
          this.getComments();
        }
    });
  }

  getComments() {
    this.commentService.getCommentsFromArticle(this.articleId).subscribe(comments => {
      this.buildCommentArray(comments);
    })
  }

  buildCommentArray(comments: UserComment[]) {
    this.userComments = comments;

    let toDelete: UserComment[] = []; 
    this.userComments.forEach(comment => {
      if(comment.parentId) {
        toDelete.push(comment);
        this.insertChild(comment.parentId, comment, this.userComments);
      }
    });
    toDelete.forEach(comment => {
      this.removeComment(comment.id, this.userComments);
    });
  }

  submitComment() {
    let content: string = this.commentForm.get('comment').value;
    let parentId: string = this.commentForm.get('parentId').value;
    if(!content || content.length == 0) {
      this.errorInterface.setErrorMessage("You cannot submit an empty comment!");
    }

    let comment: UserComment = {"articleId": this.articleId, 
      "content": content};

    if(parentId) {
      comment.parentId = parentId;
    }

    if(this.currentlyEditingComment) {
      this.currentlyEditingComment.parentId = comment.parentId;
      this.currentlyEditingComment.content = comment.content;
      this.updateComment(this.currentlyEditingComment);
    } else {
      this.commentService.addObject(comment).subscribe(comment => {
        this.errorInterface.setSuccessMessage("Successfully added your new comment!");
        if(parentId) {
          this.insertChild(parentId, comment, this.userComments);
        } else {
          this.userComments.push(comment);
        }
      }) ;
    }    
  }

  deleteComment(comment: UserComment) {
    if(this.isCommentOwner(comment)) {
      this.commentService.deleteObject(comment.id).subscribe(x => {
        this.removeComment(comment.id, this.userComments);
        this.errorInterface.setSuccessMessage("Successfully deleted your comment!");
      })
    }
  }

  removeComment(commentId: string, comments: UserComment[]) {
    comments.forEach(comment => {
      if(comment.id === commentId) {
        comments.splice(this.userComments.indexOf(comment), 1);
        return;
      }
      if(comment.responseComments) {
        this.removeComment(commentId, comment.responseComments);
      }
    });
  }

  insertChild(parentId: string, newComment: UserComment, comments: UserComment[]) {
    comments.forEach(comment => {
      if(comment.id === parentId) {
        if(!comment.responseComments) {
          comment.responseComments = [];
        } 
        comment.responseComments.push(newComment);
        return;
      }
      if(comment.responseComments) {
        this.insertChild(parentId, newComment, comment.responseComments);
      }
    });
  }

  leaveResponse(comment: UserComment) {
    // reset edit comment and form fields if any
    this.resetEditComment();

    this.commentForm.get("parentId").setValue(comment.id);
    this.currentSelectedComment = comment;
    this.navigateToCommentSection();
  }

  removeReply() {
    this.commentForm.get("parentId").setValue(null);
    this.currentSelectedComment = null;
  }

  isCommentOwner(comment: UserComment) {
    return this.user ? comment.username === this.currentUser.userId : false;
  }

  isCommentOwnerOrAdmin(comment: UserComment) {
    return this.user ? (comment.username === this.currentUser.userId || 
      this.currentUser.role === Role.Admin) : false;
  }

  setEditComment(comment: UserComment) {
    // reset comment response if any
    this.resetRespondComment()

    this.commentForm.get("parentId").setValue(comment.parentId);
    this.commentForm.get("comment").setValue(comment.content);
    this.currentlyEditingComment = comment;
    this.navigateToCommentSection();
  }

  updateComment(comment: UserComment) {
    this.commentService.updateComment(comment).subscribe((updatedComment: UserComment) => {
      comment.content = updatedComment.content;
      comment.editedAt = updatedComment.editedAt;

      // reset edit comment and form fields
      this.resetEditComment();

      // set success message
      this.errorInterface.setSuccessMessage("Successfully updated your comment!")
    })
  }

  resetRespondComment() {
    this.currentSelectedComment = null;

    // clean forms
    this.commentForm.get("parentId").setValue(null);
    this.commentForm.get("comment").setValue(null);
  }

  resetEditComment() {
    //reset current edit Comment
    this.currentlyEditingComment = null;

    // clean forms
    this.commentForm.get("parentId").setValue(null);
    this.commentForm.get("comment").setValue(null);
  }

  addOrRemoveCommentLike(comment: UserComment) {
    if(!comment.isUserLikedComment) {
      this.commentService.addLike(comment.id).subscribe(x => {
        comment.numLikes++;
        comment.isUserLikedComment=true;
      })
    } else {
      this.commentService.removeLike(comment.id).subscribe(x => {
        comment.numLikes--;
        comment.isUserLikedComment=false;
      })
    }
  }

  navigateToCommentSection() {
    document.getElementById("section").scrollIntoView({ behavior: 'smooth' });
  }


  isUserLikedComment(comment: UserComment) {
    if(this.currentUser) {
      return comment.isUserLikedComment;
    }
  }

}
