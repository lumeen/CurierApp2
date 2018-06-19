import {Component, Input, OnInit} from '@angular/core';
import {CurierService} from "../../services/curier.service";
import {Router} from "@angular/router";
import {Curier} from "../../model/model.curier";
import {Message} from "primeng/components/common/api";
import {MessageService} from "primeng/components/common/messageservice";

@Component({
  selector: 'app-couriers-list',
  templateUrl: './couriers-list.component.html',
  styleUrls: ['./couriers-list.component.less']
})
export class CouriersListComponent implements OnInit {
 @Input() inputRows ;
  msgs: Message[] = [];
  columns = [
    { prop: 'firstName' , name: "First Name"},
    { name: 'Second name' , prop: "secondName"},
  ];
  constructor(private messageService: MessageService,private curierService: CurierService, private router: Router) { }

  ngOnInit() {

  }
  activate(event) {

    if (event.type == "click" ) {
      console.log(event.row.id);
      this.router.navigateByUrl(`profile/curier/${event.row.id}`);
    }
  }



}
