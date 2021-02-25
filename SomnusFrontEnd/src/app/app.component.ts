import { animate, group, query, style, transition, trigger } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { AppSettings } from './appSettings';
import { routerTransition } from './services/transitions/page-transitions';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  animations: [
    trigger('routerTransition', [
      transition('* <=> *', [    
        query(':enter, :leave', style({ position: 'fixed', opacity: 1 })),
        group([ 
          query(':enter', [
            style({ opacity:0 }),
            animate('1200ms ease-in-out', style({ opacity:1 }))
          ]),
          query(':leave', [
            style({ opacity:1 }),
            animate('1200ms ease-in-out', style({ opacity:0 }))]),
        ])
      ])
    ])
   ]
})

export class AppComponent {
  title = AppSettings.SERVER_NAME;

  constructor() { }

  private previousPath: string = ''

  getPageTransition(routerOutlet: RouterOutlet) {
    if (routerOutlet.isActivated) {
      let transitionName = 'section'

      const { path } = routerOutlet.activatedRoute.routeConfig
      const isSame = this.previousPath === path
      const isBackward = this.previousPath.startsWith(path)
      const isForward = path.startsWith(this.previousPath)

      if (isSame) {
        transitionName = 'none'
      } else if (isBackward && isForward) {
        transitionName = 'initial'
      } else if (isBackward) {
        transitionName = 'backward'
      } else if (isForward) {
        transitionName = 'forward'
      }

      this.previousPath = path

      return transitionName
    }
  }

}
