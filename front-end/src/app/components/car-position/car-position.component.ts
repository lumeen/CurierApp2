import {Component, Input, OnInit} from '@angular/core';
import {Courier} from "../../model/model.courier";



@Component({
  selector: 'app-car-position',
  templateUrl: './car-position.component.html',
  styleUrls: ['./car-position.component.css']
})
export class CarPositionComponent implements OnInit {
  lat: number ;
  lng: number ;

  @Input() courier: Courier;

  constructor() {

  }

  ngOnInit() {
    console.log(this.courier);
    this.lng = this.courier.car.coordinate.latitude;
    this.lat = this.courier.car.coordinate.longitude;


  }

}
