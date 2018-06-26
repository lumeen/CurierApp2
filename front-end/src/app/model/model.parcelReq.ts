
export class ParcelReq {

  city: string;
  postCode: string;
  streetName: string;

  buildNumber: string;


  constructor(city: string, postCode: string, streetName: string, buildNumber: string) {
    this.city = city;
    this.postCode = postCode;
    this.streetName = streetName;
    this.buildNumber = buildNumber;
  }
}
