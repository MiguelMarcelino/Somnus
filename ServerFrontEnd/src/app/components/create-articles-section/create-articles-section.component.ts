import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';

@Component({
  selector: 'app-create-articles-section',
  templateUrl: './create-articles-section.component.html',
  styleUrls: ['./create-articles-section.component.css']
})
export class CreateArticlesSectionComponent implements OnInit {

  editorForm: FormGroup;

  constructor(

  ) { }

  ngOnInit(): void {
    this.editorForm = new FormGroup({
      'editor': new FormControl(null)
    })
  }

  onSubmit(){

  }
}
