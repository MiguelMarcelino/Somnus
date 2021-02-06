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
    let token = "";
    this.getAuthToken().then(x => {
        token = x;
    })
    const authReq = req.clone({
      headers: req.headers.set('X-Authorization-Firebase', token)
        .append('Access-Control-Allow-Origin', '*')
    }); 
    return next.handle(authReq).pipe(tap(
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

  // This is stupid
  async getAuthToken() {
    let user = await this.authService.isLoggedIn();
    let token = await user.getIdToken();
    return token;
  }
}