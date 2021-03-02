import { Component, HostListener, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { SystemInfoModel } from 'src/app/models/system_monitor/systemInfo.model';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { SystemInfoControllerService } from 'src/app/services/controllers/system-info-controller.service';

@Component({
  selector: 'app-temp-server-info',
  templateUrl: './temp-server-info.component.html',
  styleUrls: ['./temp-server-info.component.scss']
})
export class TempServerInfoComponent implements OnInit {

  // app user
  user: firebase.default.User;

  gaugeType = "arch";
  foregroundColor = "rgba(255, 255, 255, 1)";
  backgroundColor = "rgba(34, 34, 34, 1)";
  size: number;
  thickness = 20;
  duration = 700;

  memoryGaugeTitle = "Memory Usage";
  cpuGaugeTitle = "CPU Usage";
  swapGaugeTitle = "Swap Usage";
  temperatureGaugeTitle = "CPU Temperature"

  systemInfo: SystemInfoModel;
  loading: boolean = true;

  screenHeight: number;
  screenWidth: number;

  intervalId;

  constructor(
    private systemInfoService: SystemInfoControllerService,
    private authenticationService: AuthenticationService,
  ) {
    this.size = 370;
  }

  ngOnInit(): void {
    this.authenticationService.getLoggedInUser()
      .subscribe(user => {
        this.user = user;
      });
    this.getSystemInfoData();
    this.getScreenSize();
  }

  @HostListener('window:resize', ['$event'])
  getScreenSize(event?) {
    this.screenHeight = window.innerHeight;
    this.screenWidth = window.innerWidth;
    if (this.screenWidth < 500) {
      this.size = 250;
    } else if (this.screenWidth >= 800 && this.screenWidth < 1600) {
      this.size = 430;
    } else if (this.screenWidth >= 1600 && this.screenWidth < 1800) {
      this.size = 350;
    } else {
      this.size = 400;
    }
  }

  ngAfterViewInit(): void {
    this.intervalId = setInterval(() => { this.updateGauges(); }, 5 * 1000);
  }

  ngOnDestroy() {
    clearInterval(this.intervalId);
  }
  

  updateGauges(): void {
    if (this.user) {
      this.getSystemInfoData();
    }
  }

  getSystemInfoData() {
    this.systemInfoService.getSystemInfoApi().subscribe((systemInfo: SystemInfoModel) => {
      this.loading = false;
      this.systemInfo = systemInfo;
      this.systemInfo.numCpuThreads = systemInfo.numCpuThreads;
      this.systemInfo.memoryUsage = Math.round(systemInfo.memoryUsage / 1000000);
      this.systemInfo.totalMemory = systemInfo.totalMemory / 1000000;
      this.systemInfo.cpuUsage = Math.round(systemInfo.cpuUsage * 100);
      this.systemInfo.swapUsage = Math.round(systemInfo.swapUsage / 1000000);
      this.systemInfo.swapSize = Math.round(systemInfo.swapSize / 1000000);
      this.systemInfo.temperature = systemInfo.temperature;
      this.systemInfo.maxTemp = 100;
    },
      (error) => {
        this.loading = false;
      });
  }
}
