import {Injectable} from '@angular/core';
import {Headers, Http, RequestOptions, Response} from '@angular/http';
import {User} from "../model/model.user";
import 'rxjs/add/operator/map';
import {AppComponent} from "../app.component";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable()
export class AuthService {
  constructor(public http: HttpClient) {
  }

  public logIn(user: User) {

    return this.http.post<any>(AppComponent.API_URL + '/login', {
      username: user.username,
      password: user.password
    });
  }

  public test(){

    let headers: HttpHeaders = new HttpHeaders();
    headers = headers.append('Access-Control-Allow-Origin', '*');
    headers = headers.append('Authorization', 'Bearer ' + localStorage.getItem('token'));


    return this.http.get<any>(AppComponent.API_URL+ '/test' ,{headers: headers});
  }
}
