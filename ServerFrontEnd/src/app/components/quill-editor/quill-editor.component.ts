import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-quill-editor',
  templateUrl: './quill-editor.component.html',
  styleUrls: ['./quill-editor.component.css']
})
export class QuillEditorComponent implements OnInit {

  @Output() onEditorContentChange: EventEmitter<String> = new EventEmitter<String>();
  quillForm: FormGroup;

  editorStyle = {
    height: '400pt',
    backgroundColor: 'black',
    borderRadius: '4pt',
    color: 'white'
  };
  editorConfig = {
    toolbar: [
      ['bold', 'italic', 'underline', 'strike'],
      ['blockquote','code-block'],
      [{ 'list': 'ordered'}, { 'list': 'bullet' }],
      [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
      ['link', 'image', 'video'],
    ]
  }
  maxContentLength = 40000;

  constructor(
    private formBuilder: FormBuilder,
  ) { }

  ngOnInit(): void {
    this.quillForm = this.formBuilder.group({
      'editor': new FormControl(''),
    });
  }

  checkContent(e: any):void {
    if(e.editor.getLength() > this.maxContentLength) {
      e.editor.deleteText(this.maxContentLength, e.editor.getLength());
    } else {
      this.onEditorContentChange.emit(this.quillForm.get('editor').value);
    }
  }

}
