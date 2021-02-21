import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AuthenticationService } from './authentication.service'
import { tap } from 'rxjs/operators';
import { ErrorInterface } from 'src/handlers/error-interface';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
    constructor(
        private router: Router,
        private authenticationService: AuthenticationService,
        private errorInterface: ErrorInterface,
    ) {}

    async canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const user = await this.authenticationService.isLoggedIn();
        const currentUser = this.authenticationService.getCurrentUser();
        if (user) {
            if(route.data.roles && route.data.roles.indexOf(currentUser.role) === -1) {
                // role not authorized, so redirect to home page
                this.router.navigate(['/']);
                this.errorInterface.setErrorMessage("You don't have enough permission to perform this operation");
                return false;
            }
            // authorised so return true
            return true;
        }

        // not logged in so redirect to login page with the return url
        this.router.navigate(['/login'], { queryParams: { returnUrl: state.url }});
        return false;
    }
}