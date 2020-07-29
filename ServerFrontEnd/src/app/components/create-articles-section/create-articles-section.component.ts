import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-create-articles-section',
  templateUrl: './create-articles-section.component.html',
  styleUrls: ['./create-articles-section.component.css']
})
export class CreateArticlesSectionComponent implements OnInit {

  editorForm: FormGroup;
  editorStyle = {
    height: '400pt',
    backgroundColor: 'white',

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
  editorContent: string;

  constructor(
    private formBuilder: FormBuilder
  ) { }

  ngOnInit(): void {
    this.editorForm = this.formBuilder.group({
      'editor': new FormControl('')
    })
  }

  onSubmitPreview(): void {
    this.editorContent = this.editorForm.get('editor').value;
  }

  maxLength(e: any):void {
    if(e.editor.getLength() > this.maxContentLength) {
      e.editor.deleteText(this.maxContentLength, e.editor.getLength());
    }
  }
}
