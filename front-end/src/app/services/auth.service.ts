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
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    };
    return this.http.post<any>(AppComponent.API_URL + '/login', {
      username: user.username,
      password: user.password
    }, httpOptions);
  }
}
