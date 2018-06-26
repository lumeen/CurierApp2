import {Injectable} from '@angular/core';
import 'rxjs/add/operator/map';
import {AppComponent} from "../app.component";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Parcel} from "../model/model.parcel";
import {Observable} from "rxjs/Observable";

import {ParcelReq} from "../model/model.parcelReq";

@Injectable()
export class ParcelService {
  constructor(public http: HttpClient) {
  }

  private readonly PARCEL_URL = "http://localhost:8080/parcel";

  public updateParcels(parcels: Parcel[]) {
    let headers: HttpHeaders = new HttpHeaders();
    headers = headers.append('Access-Control-Allow-Origin', '*');
    headers = headers.append('Authorization', 'Bearer ' + localStorage.getItem('token'));

    return this.http.put<any>(AppComponent.API_URL + '/parcels/update', parcels, {headers:headers}
    );
  }

  public getParcelsByCarId( carId : number): Observable<any>{

    let headers: HttpHeaders = new HttpHeaders();
    headers = headers.append('Access-Control-Allow-Origin', '*');
    headers = headers.append('Authorization', 'Bearer ' + localStorage.getItem('token'));
    return this.http.post<any>(AppComponent.API_URL+ '/parcels/byCarId', carId, {headers: headers})
  }

  public getAll(): Observable<any>{
    let headers: HttpHeaders = new HttpHeaders();
    headers = headers.append('Access-Control-Allow-Origin', '*');
    headers = headers.append('Authorization', 'Bearer ' + localStorage.getItem('token'));
    return this.http.get<any>(AppComponent.API_URL+ '/parcels/getAll', {headers: headers})
  }



  public createParcel(parcel: ParcelReq): Observable<ParcelReq> {
    let headers: HttpHeaders = new HttpHeaders();
    headers = headers.append('Access-Control-Allow-Origin', '*');
    headers = headers.append('Authorization', 'Bearer ' + localStorage.getItem('token'));
    return this.http.put<ParcelReq>(this.PARCEL_URL, parcel, {headers});
  }

  public findParcel(id: number): Observable<Parcel[]> {
    let headers: HttpHeaders = new HttpHeaders();
    headers = headers.append('Access-Control-Allow-Origin', '*');
    headers = headers.append('Authorization', 'Bearer ' + localStorage.getItem('token'));
    return this.http.get<Parcel[]>(this.PARCEL_URL + '/' + id,{headers:headers});
  }

}
