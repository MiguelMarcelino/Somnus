import { Component, Renderer2, ElementRef } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(
    private el: ElementRef
  ) { }

  title = 'ServerWebsite';

  ngAfterViewInit(){
    this.el.nativeElement.ownerDocument.body.style.backgroundColor = 'rgb(53, 53, 53)';
  }


}


