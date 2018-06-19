import { Component, OnInit } from '@angular/core';
import {CurierService} from "../../services/curier.service";
import {Curier} from "../../model/model.curier";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-curier',
  templateUrl: './curier.component.html',
  styleUrls: ['./curier.component.css']
})
export class CurierComponent implements OnInit {

  curier : Curier;
  constructor(private curierService : CurierService, private route: ActivatedRoute) { }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');

    this.curierService.getCurier(+id).subscribe(data =>{

      this.curier= data;
      console.log(this.curier);
    })

  }

}
