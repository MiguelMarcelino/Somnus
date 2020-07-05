import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormControl } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-add-game-event',
  templateUrl: './add-game-event.component.html',
  styleUrls: ['./add-game-event.component.css']
})
export class AddGameEventComponent implements OnInit {

  addGameForm: FormGroup;

  constructor(private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.addGameForm = this.formBuilder.group({
      title: new FormControl(''),
      description: new FormControl(''),
      image: new FormControl(''),
    })
  }

  // TODO
  onSubmit() {
    
  }
}
