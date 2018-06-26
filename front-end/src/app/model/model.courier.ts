import {Car} from "./model.car";

export class Courier {
  id : number;
  firstName: string;
  secondName: string;
  phoneNumber: string;
car: Car;

  constructor(firstName: string, secondName: string,  phoneNumber: string, car: Car) {
    this.firstName = firstName;
    this.secondName = secondName;
    this.phoneNumber= phoneNumber;
    this.car=car;
  }
}
