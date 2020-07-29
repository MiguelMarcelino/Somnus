import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'contacts',
  templateUrl: './contacts.component.html',
  styleUrls: ['./contacts.component.css']
})
export class ContactsComponent implements OnInit {

  contactForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder
  ) { }

  ngOnInit(): void {
    this.contactForm = this.formBuilder.group({
      first_name: new FormControl(''),
      email: new FormControl(''),
      feedback: new FormControl(''),
    })
  }

  // TODO
  onSubmit() {
    //send feedback to backend
  }

}
