import { Identifiable } from 'src/app/models/identifiable';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';

// Shamelessly copied from Samuel Ferreira's Work. Nice Job Sam!
@Injectable({
    providedIn: "root"
})
export abstract class TemplateControllerService<T extends Identifiable> {

    private httpOptions = {
        headers: new HttpHeaders({ "Content-Type": "application/json" })
    }

    constructor(
        protected http: HttpClient
    ) {
    }

    protected abstract getApiUrlAll();
    protected abstract getApiUrlObject();

    getAll(): Observable<any> {
        return this.http.get(this.getApiUrlAll());
    }

    getObject(id: string): Observable<any> {
        let url = `${this.getApiUrlObject()}/${id}`;
        return this.http.get(url);
    }

    addObject(object: T): Observable<any> {
        return this.http.post(`${this.getApiUrlObject()}/create`, object);
    }

    deleteObject(id: string): Observable<any> {
        let url = `${this.getApiUrlObject()}/${id}/delete`;
        return this.http.post(url, { "id": id });
    }
}