import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-server-stats',
  templateUrl: './server-stats.component.html',
  styleUrls: ['./server-stats.component.css']
})
export class ServerStatsComponent implements OnInit {

  // app user
  user: firebase.default.User;

  constructor() { }

  ngOnInit(): void {
  }

}
