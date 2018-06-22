import {Injectable} from '@angular/core';
import 'rxjs/add/operator/map';
import {AppComponent} from "../app.component";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Parcel} from "../model/model.parcel";
import {Observable} from "rxjs/Observable";

@Injectable()
export class ParcelService {
  constructor(public http: HttpClient) {
  }

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
}
