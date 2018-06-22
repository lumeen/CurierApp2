import {Car} from "./model.car";

export class Parcel {
  id : number;
  city: string;
  postCode: string;
  buildNumber: string;
  status: string;
  priority : number;
  car : Car;


  constructor(id: number, city: string, postCode: string, buildNumber: string, status: string, priority: number, car: Car) {
    this.id = id;
    this.city = city;
    this.postCode = postCode;
    this.buildNumber = buildNumber;
    this.status = status;
    this.priority = priority;
    this.car = car;
  }
}
