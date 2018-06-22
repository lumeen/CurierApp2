import {Component, Input, OnInit} from '@angular/core';

import {Curier} from "../../model/model.curier";

@Component({
  selector: 'app-car-position',
  templateUrl: './car-position.component.html',
  styleUrls: ['./car-position.component.css']
})
export class CarPositionComponent implements OnInit {
  lat: number ;
  lng: number ;

  @Input() curier: Curier;

  constructor() {

  }

  ngOnInit() {
    this.lat = this.curier.car.coordinate.latitude;
    this.lng = this.curier.car.coordinate.longitude;

  }

}
