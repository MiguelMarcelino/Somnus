import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { ArticleModel } from 'src/app/models/article.model';
import { ArticlesService } from 'src/app/services/controllers/articles-controller.service';

@Component({
  selector: 'app-quill-editor',
  templateUrl: './quill-editor.component.html',
  styleUrls: ['./quill-editor.component.css']
})
export class QuillEditorComponent implements OnInit {

  @Output() onEditorContentChange: EventEmitter<String> = new EventEmitter<String>();
  quillForm: FormGroup;

  @Input()
  article: ArticleModel;

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
  maxContentLength = 60000;

  constructor(
    private formBuilder: FormBuilder
  ) { }

  ngOnInit(): void {
    this.quillForm = this.formBuilder.group({
      'editor': new FormControl(''),
    });

    if(this.article) {
      this.quillForm.get("editor").setValue(this.article.content);
    }
  }

  checkContent(e: any):void {
    if(e.editor.getLength() > this.maxContentLength) {
      e.editor.deleteText(this.maxContentLength, e.editor.getLength());
    } else {
      this.onEditorContentChange.emit(this.quillForm.get('editor').value);
    }
  }

}
