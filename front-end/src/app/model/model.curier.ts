import {Car} from "./model.car";

export class Curier {
  id : number;
  firstName: string;
  secondName: string;
car: Car;

  constructor(firstName: string, secondName: string, car: Car) {
    this.firstName = firstName;
    this.secondName = secondName;
    this.car=car;
  }
}
