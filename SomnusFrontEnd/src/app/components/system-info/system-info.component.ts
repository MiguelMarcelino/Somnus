import { Component, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';
import * as d3 from 'd3';
import { SystemInfoModel } from 'src/app/models/system_monitor/systemInfo.model';
import { SystemInfoControllerService } from 'src/app/services/controllers/system-info-controller.service';
import { GaugeChartComponent } from '../gauge-chart/gauge-chart.component';

@Component({
  selector: 'app-system-info',
  templateUrl: './system-info.component.html',
  styleUrls: ['./system-info.component.css']
})
export class SystemInfoComponent implements OnInit {

  systemInfo: SystemInfoModel;
  loading: boolean = true;

  memoryGaugeTitle;
  memoryGaugeid;
  memoryGaugeConfig;
  memoryGaugeValue;

  cpuGaugeTitle;
  cpuGaugeid;
  cpuGaugeConfig;
  cpuGaugeValue;
  
  swapGaugeTitle;
  swapGaugeid;
  swapGaugeConfig;
  swapGaugeValue;

  @ViewChildren(GaugeChartComponent) gauges: QueryList<GaugeChartComponent>;

  constructor(
    private systemInfoService: SystemInfoControllerService
  ) { }

  ngOnInit(): void {
    //this.getSystemInfoData();
    this.getDummySystemInfo();

    this.memoryGaugeTitle = "Memory Usage";
    this.memoryGaugeid = "memGauge";
    this.memoryGaugeValue = this.systemInfo.memoryUsage;
    this.memoryGaugeConfig = {
      size: 350,
      clipWidth: 400,
      clipHeight: 400,
      ringWidth: 30,
      minValue: 0,
      maxValue: this.systemInfo.totalMemory,
      majorTicks: 10,
      transitionMs: 10000
    }

    this.cpuGaugeTitle = "CPU Usage";
    this.cpuGaugeid = "cpuGauge";
    this.cpuGaugeValue = this.systemInfo.cpuUsage;
    this.cpuGaugeConfig = {
      size: 350,
      clipWidth: 400,
      clipHeight: 400,
      ringWidth: 30,
      minValue: 0,
      maxValue: this.systemInfo.numCpuThreads,
      majorTicks: this.systemInfo.numCpuThreads,
      transitionMs: 10000
    }

    this.swapGaugeTitle = "Swap Usage";
    this.swapGaugeid = "swapGauge";
    this.swapGaugeValue = this.systemInfo.swapUsage;
    this.swapGaugeConfig = {
      size: 350,
      clipWidth: 400,
      clipHeight: 400,
      ringWidth: 30,
      minValue: 0,
      maxValue: this.systemInfo.swapSize,
      majorTicks: 10,
      transitionMs: 10000
    }
  }

  ngAfterViewInit():void{
    setInterval(()=> { this.updateGauges();  }, 5 * 1000);
  }

  updateGauges():void{
    this.getDummySystemInfo();
    this.gauges.toArray();
    this.gauges.toArray()[0].update(this.systemInfo.memoryUsage);
    this.gauges.toArray()[1].update(this.systemInfo.cpuUsage);
    this.gauges.toArray()[2].update(this.systemInfo.swapUsage);
  }

  getRandomInt(max):number {
    return Math.floor(Math.random() * Math.floor(max));
  }
  
  getDummySystemInfo(){
    this.systemInfo = {
      numCpuThreads : 8,
      totalMemory: 100000,
      cpuUsage: this.getRandomInt(8),
      memoryUsage: this.getRandomInt(100000),
      swapSize: 1000,
      swapUsage: this.getRandomInt(1000)
    }
  }

  getSystemInfoData() {
    this.systemInfoService.getSystemInfoApi().subscribe(systemInfo => {
      this.loading = false;
      this.systemInfo = systemInfo;
    },
    (error)=>{
      this.loading = false;
    });
  }

}
