
export class ParcelReq {

  city: string;
  postCode: string;
  streetName: string;

  buildNumber: string;
  lat: string;
  lng: string;
  placeId: string;

  constructor(city: string, postCode: string, streetName: string, buildNumber: string, lat?:string, lng?:string, placeId?:string) {
    this.city = city;
    this.postCode = postCode;
    this.streetName = streetName;
    this.buildNumber = buildNumber;
    this.lat=lat;
    this.lng=lng;
    this.placeId = placeId;
  }
}
