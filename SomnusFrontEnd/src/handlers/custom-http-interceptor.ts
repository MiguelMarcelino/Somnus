import { HttpErrorResponse, HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest, HttpResponse } from "@angular/common/http";
import { Injectable, Injector } from "@angular/core";
import { Router } from "@angular/router";
import { Observable, throwError } from "rxjs";
import { catchError, tap } from "rxjs/operators";
import { ErrorInterface } from "src/handlers/error-interface";
import { AuthenticationService } from "../app/services/authentication/authentication.service";

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
            console.log(err);
            if (err.status === 401) {
              this.router.navigate(['/login']);
              localStorage.removeItem("token");
              this.authService.logout();
              this.errorInterface.setErrorMessage("You are not authorized to perform that task!");
            } else {
              // send error to interface
              this.errorInterface.setErrorMessage(err.message);
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
          console.log(err);
          if (err.status === 401) {
            this.router.navigate(['user']);
            this.errorInterface.setErrorMessage("You are not authorized to perform that task!");
          } else {
            this.errorInterface.setErrorMessage(err.message);
          }

          return throwError(err);
        }
      )) as Observable<HttpEvent<any>>;
    
  }

}