import { Injectable } from "@angular/core";

@Injectable({
    providedIn: 'root'
})
export class NameNormalizer {
    normalize_name(name: string) {
        return name.replace(/ /g, "_");
    }
}