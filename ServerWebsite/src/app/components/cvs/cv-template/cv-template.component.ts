import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-cv-template',
  templateUrl: './cv-template.component.html',
  styleUrls: ['./cv-template.component.css']
})
export class CvTemplateComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }


  makeDottedBar(total: number, blackDots: number):string {
    var res = '';
    for (let i = 0; i < total; i++) {
        if(i < blackDots) {
            res+='<div class="dot-black"></div>';
        } else {
            res+='<div class="dot-blank"></div>';
        }
    }
    return res;
  }
}
