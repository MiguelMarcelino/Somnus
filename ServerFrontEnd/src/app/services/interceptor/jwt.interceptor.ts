import { HttpErrorResponse, HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest, HttpResponse } from "@angular/common/http";
import { Injectable, Injector } from "@angular/core";
import { Router } from "@angular/router";
import { Observable } from "rxjs";
import { tap } from "rxjs/operators";
import { AuthenticationService } from "../authentication/authentication.service";

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  constructor(private authService: AuthenticationService, private router: Router) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // not used until we figure out how to make an async interceptor
    // const user = this.authService.isLoggedIn();
    // user.then(user => {
    //   user.getIdToken().then(token => {
    //     // attach token to header
    //     const authReq = req.clone({
    //       headers: req.headers.set('X-Authorization-Firebase', token)
    //         .append('Access-Control-Allow-Origin', '*')
    //     });
    //     console.log(token);
    //     return next
    //       .handle(authReq)
    //       .pipe(tap(
    //         (err: any) => {
    //           if (err instanceof HttpErrorResponse) {
    //             console.log(err);
    //             console.log('req url :: ' + req.url);
    //             if (err.status === 401) {
    //               this.router.navigate(['user']);
    //             }
    //           }
    //         }
    //       ));
    //   })
    // });

    // should not be necessary
    // this.authService.getToken();

    const token = localStorage.getItem("token");
    if(token) {
      const authReq = req.clone({
        headers: req.headers.set('X-Authorization-Firebase', token)
              .append('Access-Control-Allow-Origin', '*')
      });
      return next
        .handle(authReq)
        .pipe(tap(
          (err: any) => {
            if (err instanceof HttpErrorResponse) {
              console.log(err);
              // console.log('req url :: ' + req.url);
              if (err.status === 401) {
                this.router.navigate(['/login']);
                localStorage.removeItem("token");
              }
            }
          }
        ));
    } 

    // if there is no token, don't send it
    const authReq = req.clone({
      headers: req.headers.set('Access-Control-Allow-Origin', '*')
    });
    return next
      .handle(authReq)
      .pipe(tap(
        (err: any) => {
          if (err instanceof HttpErrorResponse) {
            console.log(err);
            console.log('req url :: ' + req.url);
            if (err.status === 401) {
              this.router.navigate(['user']);
            }
          }
        }
      ));
    
  }

}