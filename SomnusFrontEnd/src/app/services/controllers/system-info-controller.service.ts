import { Injectable } from '@angular/core';
import { TemplateControllerService } from './template-controller.service';
import { Observable } from 'rxjs';
import { AppRoutesService } from '../routes/app-routes.service';
import { HttpClient } from '@angular/common/http';
import { SystemInfoModel } from 'src/app/models/system_monitor/systemInfo.model';

@Injectable({
  providedIn: 'root'
})
export class SystemInfoControllerService extends TemplateControllerService<SystemInfoModel>{
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
  
  public getSystemInfoApi(): Observable<any> {
    return this.http.get(this.appRoutes.apiSystemMonitorEndpointSystemInfo);
  }
}
