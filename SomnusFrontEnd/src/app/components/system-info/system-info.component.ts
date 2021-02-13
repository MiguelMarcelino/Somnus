import { Component, OnInit } from '@angular/core';
import * as d3 from 'd3';
import { SystemInfoModel } from 'src/app/models/system_monitor/systemInfo.model';
import { SystemInfoControllerService } from 'src/app/services/controllers/system-info-controller.service';

@Component({
  selector: 'app-system-info',
  templateUrl: './system-info.component.html',
  styleUrls: ['./system-info.component.css']
})
export class SystemInfoComponent implements OnInit {

  systemInfo: SystemInfoModel;
  loading: boolean = true;

  ///////////////////
  private data = [
    {"Framework": "Vue", "Stars": "166443", "Released": "2014"},
    {"Framework": "React", "Stars": "150793", "Released": "2013"},
    {"Framework": "Angular", "Stars": "62342", "Released": "2016"},
    {"Framework": "Backbone", "Stars": "27647", "Released": "2010"},
    {"Framework": "Ember", "Stars": "21471", "Released": "2011"},
  ];
  private svg;
  private margin = 10;
  private width = 750;
  private height = 600;
  // The radius of the pie chart is half the smallest side
  private radius = Math.min(this.width, this.height) / 2 - this.margin;
  private colors;

  private createSvg(): void {
    this.svg = d3.select("figure#pie")
    .append("svg")
    .attr("width", this.width)
    .attr("height", this.height)
    .append("g")
    .attr(
      "transform",
      "translate(" + this.width / 2 + "," + this.height / 2 + ")"
    );
}

private createColors(): void {
  this.colors = d3.scaleOrdinal()
  .domain(this.data.map(d => d.Stars.toString()))
  .range(["#c7d3ec", "#a5b8db", "#879cc4", "#677795", "#5a6782"]);
}

private drawArc(){
  const pie = d3.pie<any>().value((d: any) => Number(d.Stars));
  this.svg = d3.select("figure#pie").append("svg").attr("width", 1000).attr("height", 400);
  this.svg
  .append("path")
  .data(pie(this.data))
  .attr("transform", "translate(400,200)")
  .attr("d", d3.arc()
    .innerRadius( 100 )
    .outerRadius( 150 )
    .startAngle( 3.14 - 3.14/2  )     // It's in radian, so Pi = 3.14 = bottom.
    .endAngle( 6.28 + 3.14/2 )       // 2*Pi = 6.28 = top
    )
  .attr('stroke', 'black')
  .attr('fill', (d, i) => (this.colors(i)));
}

private drawChart(): void {
  // Compute the position of each group on the pie:
  const pie = d3.pie<any>().value((d: any) => Number(d.Stars));

  // Build the pie chart
  this.svg
  .selectAll('pieces')
  .data(pie(this.data))
  .enter()
  .append('path')
  .attr('d', d3.arc()
    .innerRadius(0)
    .outerRadius(this.radius)
  )
  .attr('fill', (d, i) => (this.colors(i)))
  .attr("stroke", "#121926")
  .style("stroke-width", "1px");

  // Add labels
  const labelLocation = d3.arc()
  .innerRadius(100)
  .outerRadius(this.radius);

  this.svg
  .selectAll('pieces')
  .data(pie(this.data))
  .enter()
  .append('text')
  .text(d => d.data.Framework)
  .attr("transform", d => "translate(" + labelLocation.centroid(d) + ")")
  .style("text-anchor", "middle")
  .style("font-size", 15);
}


  //////////////////



  constructor(
    private systemInfoService: SystemInfoControllerService
  ) { }

  ngOnInit(): void {
    //this.getSystemInfoData();
    //this.createSvg();
    //this.createColors();
    //this.drawArc();
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
