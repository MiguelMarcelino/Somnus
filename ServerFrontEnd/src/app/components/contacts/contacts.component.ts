import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import { Router } from '@angular/router';
import { FeedbackControllerService } from 'src/app/services/controllers/feedback-controller.service';

@Component({
  selector: 'contacts',
  templateUrl: './contacts.component.html',
  styleUrls: ['./contacts.component.css']
})
export class ContactsComponent implements OnInit {

  contactForm: FormGroup;
  publishError: any;
  successMessage: any;

  constructor(
    private formBuilder: FormBuilder,
    private feedbackController: FeedbackControllerService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.contactForm = this.formBuilder.group({
      feedbackTitle: new FormControl(''),
      feedback: new FormControl(''),
    })
  }

  onSubmit() {
    if(this.contactForm.invalid) {
      return;
    }

    let feedbackTitle = this.contactForm.get('feedbackTitle').value;
    let feedback = this.contactForm.get('feedback').value;

    let feedbackModel = {"feedbackTitle": feedbackTitle, "feedback": feedback};

    this.feedbackController.addObject(feedbackModel).subscribe(id => {
      // show success message
      this.successMessage = "Thank you for submitting your feedback!";

      // reset form values
      this.contactForm.get('feedbackTitle').setValue("");
      this.contactForm.get('feedback').setValue("");
    },
    (error) => {
      // Template message
      this.publishError = "Something went wrong! We could not submit your feedback";
    }
    );
  }

}
