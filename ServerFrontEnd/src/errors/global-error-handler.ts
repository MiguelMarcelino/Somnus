import { ErrorHandler, Injectable } from '@angular/core';
import { ErrorInterface } from './error-interface';

@Injectable({
    providedIn: 'root'
})
export class GlobalErrorHandler extends ErrorHandler {

    public errorContext: any;
    private errorInterface: ErrorInterface;

    constructor(
        errorInterface: ErrorInterface
    ) {
        super();
        this.errorInterface = errorInterface;
    }

    handleError( error:Error ) {
        const err = {
            message: error.message ? error.message : error.toString(),
            stack: error.stack ? error.stack : ''
        };

        this.errorInterface.setErrorMessage(err.message);

        // Log  the error
        console.log(err);

        super.handleError( error );
    }
}