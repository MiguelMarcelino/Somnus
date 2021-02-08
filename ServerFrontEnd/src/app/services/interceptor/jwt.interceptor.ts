import { HttpErrorResponse, HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest, HttpResponse } from "@angular/common/http";
import { Injectable, Injector } from "@angular/core";
import { Router } from "@angular/router";
import { Observable, throwError } from "rxjs";
import { catchError, tap } from "rxjs/operators";
import { ErrorInterface } from "src/errors/error-interface";
import { AuthenticationService } from "../authentication/authentication.service";

@Injectable()
export class CustomHttpInterceptor implements HttpInterceptor {
  
  constructor(
    private authService: AuthenticationService, 
    private router: Router,
    private errorInterface: ErrorInterface
  ) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = localStorage.getItem("token");
    if(token) {
      const authReq = req.clone({
        headers: req.headers.set('X-Authorization-Firebase', token)
              .append('Access-Control-Allow-Origin', '*')
      });
      return next
        .handle(authReq)
        .pipe(catchError(
          (err: HttpErrorResponse) => {
            // send error to interface
            this.errorInterface.setErrorMessage(err.message);
            console.log(err);
            if (err.status === 401) {
              this.router.navigate(['/login']);
              localStorage.removeItem("token");
            }

            return throwError(err);
          }
        )) as Observable<HttpEvent<any>>;
    } 

    // if there is no token, don't send it
    const authReq = req.clone({
      headers: req.headers.set('Access-Control-Allow-Origin', '*')
    });
    return next
      .handle(authReq)
      .pipe(catchError(
        (err: HttpErrorResponse) => {
          // throw error to interface
          this.errorInterface.setErrorMessage(err.message);
          console.log(err);
          if (err.status === 401) {
            this.router.navigate(['user']);
          }

          return throwError(err);
        }
      )) as Observable<HttpEvent<any>>;
    
  }

}