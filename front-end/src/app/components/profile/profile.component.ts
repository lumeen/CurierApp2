import {Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {User} from "../../model/model.user";
import {Router} from "@angular/router";

import {MessageService} from "primeng/components/common/messageservice";
import {Message} from 'primeng/components/common/api';
import {CouriersListComponent} from "../couriers-list/couriers-list.component";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CourierService} from "../../services/courier.service";
import {Courier} from "../../model/model.courier";


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
  formGroup: FormGroup;

  @ViewChild(CouriersListComponent)
  private curierList: CouriersListComponent;


  constructor(private formBuilder: FormBuilder,private messageService: MessageService, public router: Router, public courierService: CourierService) {
  }

  ngOnInit() {

    this.formGroup = this.formBuilder.group({
      firstName: ['', [Validators.required]],
      secondName: ['', [Validators.required]],
      phoneNumber: ['', [Validators.required]]
    });
    this.courierService.getAllCouriers().subscribe(
      date =>{
this.displayAllCuriers=true;
        this.rows = date;

      }

    )
  }


  onSubmit(form){
    var errorCounter = 0;
    console.log(form.value.firstName);

    if (form.value.firstName.length == 0) {
      this.messageService.add({severity: 'error', summary: "First name can't be empty"});
      errorCounter++;
    }
    if (form.value.secondName.length == 0) {
      this.messageService.add({severity: 'error', summary: "Second name can't be empty"});
      errorCounter++;
    }

    if (form.value.phoneNumber.length == 0) {
      this.messageService.add({severity: 'error', summary: "Phone number can't be empty"});
      errorCounter++;
    }

    if (errorCounter > 0) {
      return;
    }

    this.courierService.saveGroup(new Courier(form.value.firstName, form.value.secondName, form.value.phoneNumber, null)).subscribe(
      date=>{
        this.displayAdd = false;
        this.messageService.add({severity:'success', summary:'Success!', detail:'Currier added'});
         this.courierService.getAllCouriers().subscribe(date =>{
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
    this.courierService.findCouriers(new Courier(firstName, secondName, null, null)).subscribe(date=>{

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
