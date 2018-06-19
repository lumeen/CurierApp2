import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {Curier} from "../model/model.curier";

@Injectable()
export class CurierService {

  private readonly CURIER_URL = "http://localhost:8080/curier"
  constructor(private http: HttpClient) { }

  saveGroup(curier: Curier): Observable<Curier> {
    return curier.id ?  this.updateCurier(curier) : this.createCurier(curier);
  }


  private createCurier(curier: Curier): Observable<Curier> {
    let headers: HttpHeaders = new HttpHeaders();
    headers = headers.append('Access-Control-Allow-Origin', '*');
    headers = headers.append('Authorization', 'Bearer ' + localStorage.getItem('token'))
    return this.http.put<Curier>(this.CURIER_URL, curier, {headers});
  }

  private updateCurier(curier: Curier): Observable<Curier> {
    return this.http.post<Curier>(this.CURIER_URL + '/' + curier.id, curier);
  }

   getAllCuriers(): Observable<any>{
    let headers: HttpHeaders = new HttpHeaders();
    headers = headers.append('Access-Control-Allow-Origin', '*');
    headers = headers.append('Authorization', 'Bearer ' + localStorage.getItem('token'))
    return this.http.get<any>(this.CURIER_URL, {headers: headers});
  }

  findCuriers(curier: Curier) : Observable<any> {
    let headers: HttpHeaders = new HttpHeaders();
    headers = headers.append('Access-Control-Allow-Origin', '*');
    headers = headers.append('Authorization', 'Bearer ' + localStorage.getItem('token'))
    return this.http.post<any>(this.CURIER_URL + "/find", curier, {headers: headers})
  }

  getCurier(id : number) : Observable<Curier>{
    let headers: HttpHeaders = new HttpHeaders();
    headers = headers.append('Access-Control-Allow-Origin', '*');
    headers = headers.append('Authorization', 'Bearer ' + localStorage.getItem('token'));
    return this.http.get<Curier>(this.CURIER_URL+ "/" + id, {headers: headers})
  }
}
