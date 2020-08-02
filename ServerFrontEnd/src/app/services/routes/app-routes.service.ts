import { HttpClient } from '@angular/common/http';
import { OnInit, Injectable } from '@angular/core';
import { MatGridTileHeaderCssMatStyler } from '@angular/material/grid-list';

@Injectable({
    providedIn: 'root'
})
export class AppRoutesService {
    private appRoutes: any;
    private ROUTES_FILE_PATH: string = "/assets/routes/appRoutes.json";

    constructor(
        private http: HttpClient
    ) { }

    loadAppConfig(): Promise<void> {
        return this.http.get(this.ROUTES_FILE_PATH)
            .toPromise()
            .then(data => {
                this.appRoutes = data;
            }
        );
    }

    get apiLocalEndPoint(): string {
        this.isLoaded();
        return this.appRoutes.apiEndPoint;
    }

    // Articles
    get apiArticlesEndPoint(): string {
        this.isLoaded();
        return this.appRoutes.apiArticlesEndPoint;
    }

    get apiArticleEndPoint(): string {
        this.isLoaded();
        return this.appRoutes.apiArticleEndPoint;
    }

    get apiTeamMembersEndPoint(): string {
        this.isLoaded();
        return this.appRoutes.apiTeamMembersEndPoint;
    }

    // check if routes file was loaded
    private isLoaded() {
        if (!this.appRoutes) {
            throw Error('No routes where loaded.');
        }
    }
}