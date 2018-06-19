import {Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {User} from "../../model/model.user";
import {Router} from "@angular/router";
import {CurierService} from "../../services/curier.service";
import {Curier} from "../../model/model.curier";
import {MessageService} from "primeng/components/common/messageservice";
import {Message} from 'primeng/components/common/api';
import {CouriersListComponent} from "../couriers-list/couriers-list.component";


@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ProfileComponent implements OnInit {
  currentUser: User;
  rows = [];
  displayAdd: boolean = false;
  displaySearch: boolean = false;
  displayAllCuriers: boolean = true;
  msgs: Message[] = [];

  @ViewChild(CouriersListComponent)
  private curierList: CouriersListComponent;


  constructor(private messageService: MessageService, public router: Router, public curierService: CurierService) {
  }

  ngOnInit() {
    this.curierService.getAllCuriers().subscribe(
      date =>{
this.displayAllCuriers=true;
        this.rows = date;

      }

    )
  }


  addCurier(firstName: string, secondName: string){

    this.curierService.saveGroup(new Curier(firstName, secondName)).subscribe(
      date=>{
        this.displayAdd = false;
        this.messageService.add({severity:'success', summary:'Success!', detail:'Currier added'});
         this.curierService.getAllCuriers().subscribe(date =>{
           this.rows = date;

         })
    },

    error =>{
      localStorage.removeItem('token');
      this.router.navigate(['/login']);

    }
    )

  }

  findCuriers(firstName:string, secondName:string){
    this.curierService.findCuriers(new Curier(firstName, secondName)).subscribe(date=>{

      if(date.length == 0)
      {
        this.messageService.add({severity:'error', summary:'No search results'});

      }
      else{
        this.displayAllCuriers = false;
        this.rows = date;
      }
      this.displaySearch=false;
    })
  }

}
