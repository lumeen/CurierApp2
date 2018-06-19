import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-car-position',
  templateUrl: './car-position.component.html',
  styleUrls: ['./car-position.component.css']
})
export class CarPositionComponent implements OnInit {
  title: string = 'My first AGM project';
  lat: number = 51.678418;
  lng: number = 7.809007;

  constructor() { }

  ngOnInit() {
  }

}
