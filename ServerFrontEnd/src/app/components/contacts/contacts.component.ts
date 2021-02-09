import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import { Router } from '@angular/router';
import * as firebase from 'firebase';
import { AppSettings } from 'src/app/appSettings';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { FeedbackControllerService } from 'src/app/services/controllers/feedback-controller.service';
import { ErrorInterface } from 'src/handlers/error-interface';

@Component({
  selector: 'contacts',
  templateUrl: './contacts.component.html',
  styleUrls: ['./contacts.component.css']
})
export class ContactsComponent implements OnInit {

  // app user
  user: firebase.default.User;
  contactForm: FormGroup;
  inviteLink: string = AppSettings.DISCORD_INVITE;

  constructor(
    private formBuilder: FormBuilder,
    private feedbackController: FeedbackControllerService,
    private errorInterface: ErrorInterface,
    private router: Router,
    private authenticationService: AuthenticationService
  ) { }

  ngOnInit(): void {
    this.contactForm = this.formBuilder.group({
      feedbackTitle: new FormControl(''),
      feedback: new FormControl(''),
    })
    this.authenticationService.getLoggedInUser()
      .subscribe (user => {
        this.user = user;
    });
  }

  onSubmit() {
    if(this.contactForm.invalid) {
      return;
    }

    let feedbackTitle = this.contactForm.get('feedbackTitle').value;
    let feedback = this.contactForm.get('feedback').value;

    let feedbackModel = {"title": feedbackTitle, "content": feedback};

    this.feedbackController.addObject(feedbackModel).subscribe(id => {
      // show success message
      this.errorInterface.setSuccessMessage("Thank you for submitting your feedback!");
      
      // reset form values
      this.contactForm.get('feedbackTitle').setValue("");
      this.contactForm.get('feedback').setValue("");
    });
  }

}
