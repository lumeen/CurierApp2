import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {User} from "../../model/model.user";
import {Router} from "@angular/router";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ProfileComponent implements OnInit {
  currentUser: User;
  constructor(public authService: AuthService, public router: Router) {
  }

  ngOnInit() {
  }

  logOut() {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);

  }

  getTest(){
    this.authService.test().subscribe(
      date=>{
      console.log(date);
    },
    error =>console.log(error),
    )

  }
}
