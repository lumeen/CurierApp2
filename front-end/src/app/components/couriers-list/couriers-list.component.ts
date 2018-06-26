import {Component, Input, OnInit} from '@angular/core';
;
import {Router} from "@angular/router";
import {Message} from "primeng/components/common/api";
import {MessageService} from "primeng/components/common/messageservice";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {CourierService} from "../../services/courier.service";

@Component({
  selector: 'app-couriers-list',
  templateUrl: './couriers-list.component.html',
  styleUrls: ['./couriers-list.component.less']
})
export class CouriersListComponent implements OnInit {
 @Input() inputRows ;
  @Input() group: FormGroup;

  msgs: Message[] = [];
  columns = [
    { prop: 'id' , name: "ID"},
    { prop: 'firstName' , name: "First Name"},
    { name: 'Second name' , prop: "secondName"},
    { name: 'Phone' , prop: "phoneNumber"},
  ];
  constructor(private messageService: MessageService,private courierService: CourierService, private router: Router) { }

  ngOnInit() {


  }
  activate(event) {

    if (event.type == "click" ) {
      console.log(event.row.id);
      this.router.navigateByUrl(`/courier/${event.row.id}`);
    }
  }



}
