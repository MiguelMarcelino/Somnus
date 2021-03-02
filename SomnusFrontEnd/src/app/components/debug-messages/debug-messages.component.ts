import { CastExpr } from '@angular/compiler';
import { Component, ErrorHandler, OnInit } from '@angular/core';
import { timeout } from 'rxjs/operators';
import { ErrorInterface } from 'src/handlers/error-interface';
import { GlobalErrorHandler } from 'src/handlers/global-error-handler';

@Component({
  selector: 'app-debug-messages',
  templateUrl: './debug-messages.component.html',
  styleUrls: ['./debug-messages.component.scss']
})
export class DebugMessagesComponent implements OnInit {

  public publishError;
  public successMessage;

  constructor(
    private errorInterface: ErrorInterface
  ) { }

  ngOnInit(): void {
    this.errorInterface.getObservableErrorMessage().subscribe(errorMessage => {
      this.publishError = errorMessage;
      this.scheduleErrorRemoval();
      this.scrollTop();
    });
    this.errorInterface.getObservableSuccessMessage().subscribe(successMessage => {
      this.successMessage = successMessage;
      this.scheduleSuccessRemoval();
      this.scrollTop();
    })
  }

  private scrollTop() {
    document.querySelector('html').scrollIntoView({ behavior: 'smooth' });
  }

  scheduleErrorRemoval() {
    setTimeout(() => {
      this.publishError = null;
    }, 12000);
  }

  scheduleSuccessRemoval() {
    setTimeout(() => {
      this.successMessage = null;
    }, 12000);
  }

}
