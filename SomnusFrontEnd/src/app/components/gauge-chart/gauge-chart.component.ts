import { Component, Input, OnInit } from '@angular/core';
import * as d3 from "d3";

@Component({
  selector: 'app-gauge-chart',
  templateUrl: './gauge-chart.component.html',
  styleUrls: ['./gauge-chart.component.scss']
})
export class GaugeChartComponent implements OnInit {

  gauge = {
    configure: undefined,
    isRendered: undefined,
    render: undefined,
    update: undefined
  };

  //interval;
  @Input() config;
  @Input() title;
  @Input() id;
  @Input() currentValue;

  constructor() { }

  ngOnInit() {
    // this.gauge = this.createGauge("#"+this.asd, this.config);
    // this.gauge.render(0);

    //this.interval = setInterval(() => { powerGauge.update(Math.random() * 11);}, 10000);
  }

  ngAfterViewInit(): void {
    this.gauge = this.createGauge("#" + this.id, this.config);
    this.gauge.render(0);
  }

  update(value) {
    this.gauge.update(value);
  }

  createGauge(container, configuration) {
    var config = {
      size: 710,
      clipWidth: 350,
      clipHeight: 110,
      ringInset: 20,
      ringWidth: 20,

      pointerWidth: 10,

      pointerTailLength: 5,
      pointerHeadLengthPercent: 0.9,

      minValue: 0,
      maxValue: 10,

      minAngle: -90,
      maxAngle: 90,
      currentAngle: -90,

      transitionMs: 750,

      majorTicks: 5,
      labelFormat: d3.format('d'),
      labelInset: 10,

      arcColorFn: d3.interpolateHsl(d3.rgb('#71FF08'), d3.rgb('#FF0000'))
    };
    var range = undefined;
    var r = undefined;
    var pointerHeadLength = undefined;
    var value = 0;

    var svg = undefined;
    var arc = undefined;
    var scale = undefined;
    var ticks = undefined;
    var tickData = undefined;
    var pointer = undefined;

    var donut = d3.pie();

    function deg2rad(deg) {
      return deg * Math.PI / 180;
    }

    function newAngle(d) {
      var ratio = scale(d);
      var newAngle = config.minAngle + (ratio * range);
      return newAngle;
    }

    function configure(configuration) {
      var prop = undefined;
      for (prop in configuration) {
        config[prop] = configuration[prop];
      }

      range = config.maxAngle - config.minAngle;
      r = config.size / 2;
      pointerHeadLength = Math.round(r * config.pointerHeadLengthPercent);

      // a linear scale this.gaugemap maps domain values to a percent from 0..1
      scale = d3.scaleLinear()
        .range([0, 1])
        .domain([config.minValue, config.maxValue]);

      ticks = scale.ticks(config.majorTicks);
      tickData = d3.range(config.majorTicks).map(function () { return 1 / config.majorTicks; });

      arc = d3.arc()
        .innerRadius(r - config.ringWidth - config.ringInset)
        .outerRadius(r - config.ringInset)
        .startAngle(function (d: any, i) {
          var ratio = d * i;
          return deg2rad(config.minAngle + (ratio * range));
        })
        .endAngle(function (d: any, i) {
          var ratio = d * (i + 1);
          return deg2rad(config.minAngle + (ratio * range));
        });
    }
    this.gauge.configure = configure;

    function centerTranslation() {
      return 'translate(' + r + ',' + r + ')';
    }

    function isRendered() {
      return (svg !== undefined);
    }
    this.gauge.isRendered = isRendered;

    function render(newValue) {
      svg = d3.select(container)
        .append('svg:svg')
        .attr('class', 'gauge')
        .attr('width', config.clipWidth)
        .attr('height', config.clipHeight);

      var centerTx = centerTranslation();

      var arcs = svg.append('g')
        .attr('class', 'arc')
        .attr('transform', centerTx);

      arcs.selectAll('path')
        .data(tickData)
        .enter().append('path')
        .attr('fill', function (d, i) {
          return config.arcColorFn(d * i);
        })
        .attr('d', arc);

      var lg = svg.append('g')
        .attr('class', 'label')
        .attr('transform', centerTx);
      lg.selectAll('text')
        .data(ticks)
        .enter().append('text')
        .attr('transform', function (d) {
          var ratio = scale(d);
          var newAngle = config.minAngle + (ratio * range);
          return 'rotate(' + newAngle + ') translate(0,' + (config.labelInset - r) + ')';
        })
        .text(config.labelFormat)
        .style("fill", "white");

      var lineData = [[config.pointerWidth / 2, 0],
      [0, -pointerHeadLength],
      [-(config.pointerWidth / 2), 0],
      [0, config.pointerTailLength],
      [config.pointerWidth / 2, 0]];
      var pointerLine = d3.line().curve(d3.curveLinear)
      var pg = svg.append('g').data([lineData])
        .attr('class', 'pointer')
        .attr('transform', centerTx);

      pointer = pg.append('path')
        .attr('d', pointerLine/*function(d) { return pointerLine(d) +'Z';}*/)
        .attr('transform', 'rotate(' + config.currentAngle + ')');

      update(newValue === undefined ? 0 : newValue);
    }
    this.gauge.render = render;

    function update(newValue, newConfiguration?) {
      if (newConfiguration !== undefined) {
        configure(newConfiguration);
      }
      var ratio = scale(newValue);

      var newAngle = config.minAngle + (ratio * range);
      config.currentAngle = newAngle;
      pointer.transition()
        .duration(config.transitionMs) //could use .ease(d3.easy*)
        .attr('transform', 'rotate(' + newAngle + ')');
    }
    this.gauge.update = update;

    configure(configuration);

    return this.gauge;
  }
}
