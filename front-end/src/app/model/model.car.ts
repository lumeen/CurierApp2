export class Car {
  id : number;
  brand: string;
  model: string;
  coordinate : Coordinates;
  registrationNumber: string;
  parcels :any[];


  constructor(brand: string, model: string, coordinate: Coordinates, registrationNumber: string, parcels: any[]) {
    this.brand = brand;
    this.model = model;
    this.coordinate = coordinate;
    this.registrationNumber = registrationNumber;
    this.parcels = parcels;
  }

}
