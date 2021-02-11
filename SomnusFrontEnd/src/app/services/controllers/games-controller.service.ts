import { HttpClient } from '@angular/common/http';
import { TemplateControllerService } from './template-controller.service';
import { AppRoutesService } from '../routes/app-routes.service';
import { Injectable, Injector } from '@angular/core';
import { Observable } from 'rxjs';
import { GameModel } from 'src/app/models/games/game-model';
@Injectable({
    providedIn: 'root'
})
export class GamesControllerService extends TemplateControllerService<GameModel>{

    constructor( 
        protected http: HttpClient,
        private appRoutes: AppRoutesService
    ) {
        super(http);
    }

    protected getApiUrlAll() {
        return "";
    }

    protected getApiUrlObject() {
        return "";
    }

    public getMinecraftApi(): Observable<any> {
        return this.http.get(this.appRoutes.apiGamesEndpointMinecraft);
    }

}