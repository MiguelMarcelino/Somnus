import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';


@Injectable({
    providedIn: 'root'
})
export class ErrorInterface {

    private errorMessage: string;
    private observableErrorMessage;
    private successMessage: string;
    private observableSuccessMessage;

    constructor() {
        this.observableErrorMessage = new BehaviorSubject<string>(this.errorMessage);
        this.observableSuccessMessage = new BehaviorSubject<string>(this.successMessage);
    }

    // Error Message
    private errorMessageChange() {
        this.observableErrorMessage.next(this.errorMessage);
    }

    getObservableErrorMessage(): BehaviorSubject<string> {
        return this.observableErrorMessage;
    }
    
    setErrorMessage(newErrorMessage: string){
        this.errorMessage = newErrorMessage;
        this.errorMessageChange();
    }

    // Success Message
    private successMessageChange() {
        this.observableSuccessMessage.next(this.successMessage)
    }

    getObservableSuccessMessage(): BehaviorSubject<string> {
        return this.observableSuccessMessage;
    }
    
    setSuccessMessage(newSuccessMessage: string){
        this.successMessage = newSuccessMessage;
        this.successMessageChange();
    }
}