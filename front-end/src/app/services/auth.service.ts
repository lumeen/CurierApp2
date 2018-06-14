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
    console.log(httpOptions.headers)
    return this.http.post<any>(AppComponent.API_URL + '/login', {
      username: user.username,
      password: user.password
    }, httpOptions);
  }

  public test(){
    console.log('fsdfs');
    let headers = new HttpHeaders();
headers.append("Authorizatdsadasdasion", 'Bearer ' + "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtYXRldXN6Lm5pZXNjaW9yQGdtYWlsLmNvbSIsImNyZWF0ZWQiOjE1Mjg5ODcwODAyNTAsImV4cCI6MTUyODk4NzIwMH0.YfbzzrEDhJUnK32ciuTJexYHnUX0l-3GoaGzDxITSpx4mzRIrigOfhpOxqWiFxKS55XmDbI3I0OAGa_8snb0OA");
 console.log(headers);
    return this.http.post<any>(AppComponent.API_URL+ '/test',{}, {headers: headers});
  }
}
