import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {Courier} from "../model/model.courier";


@Injectable()
export class CourierService {

  private readonly Courier_URL = "http://localhost:8080/courier"
  constructor(private http: HttpClient) { }

  saveGroup(courier: Courier): Observable<Courier> {
    return courier.id ?  this.updateCourier(courier) : this.createCourier(courier);
  }


  private createCourier(Courier: Courier): Observable<Courier> {
    let headers: HttpHeaders = new HttpHeaders();
    headers = headers.append('Access-Control-Allow-Origin', '*');
    headers = headers.append('Authorization', 'Bearer ' + localStorage.getItem('token'));
    return this.http.put<Courier>(this.Courier_URL, Courier, {headers});
  }

  private updateCourier(Courier: Courier): Observable<Courier> {
    return this.http.post<Courier>(this.Courier_URL + '/' + Courier.id, Courier);
  }

   getAllCouriers(): Observable<any>{
    let headers: HttpHeaders = new HttpHeaders();
    headers = headers.append('Access-Control-Allow-Origin', '*');
    headers = headers.append('Authorization', 'Bearer ' + localStorage.getItem('token'))
    return this.http.get<any>(this.Courier_URL, {headers: headers});
  }

  findCouriers(Courier: Courier) : Observable<any> {
    let headers: HttpHeaders = new HttpHeaders();
    headers = headers.append('Access-Control-Allow-Origin', '*');
    headers = headers.append('Authorization', 'Bearer ' + localStorage.getItem('token'))
    return this.http.post<any>(this.Courier_URL + "/find", Courier, {headers: headers})
  }

  getCourier(id : number) : Observable<Courier>{
    let headers: HttpHeaders = new HttpHeaders();
    headers = headers.append('Access-Control-Allow-Origin', '*');
    headers = headers.append('Authorization', 'Bearer ' + localStorage.getItem('token'));
    return this.http.get<Courier>(this.Courier_URL+ "/" + id, {headers: headers})
  }

  addParcel(courierId : number, parcelId: number) : Observable<any>{
    let headers: HttpHeaders = new HttpHeaders();
    headers = headers.append('Access-Control-Allow-Origin', '*');
    headers = headers.append('Authorization', 'Bearer ' + localStorage.getItem('token'));
    return this.http.post<any>(this.Courier_URL+ "/addParcel",{courierId: courierId, parcelId:parcelId}, {headers: headers})
  }
}
