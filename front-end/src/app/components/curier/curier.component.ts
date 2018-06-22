import { Component, OnInit } from '@angular/core';
import {CurierService} from "../../services/curier.service";
import {Curier} from "../../model/model.curier";
import {ActivatedRoute} from "@angular/router";
import {DragulaService} from "ng2-dragula";
import {ParcelService} from "../../services/parcel.service";
import {MessageService} from "primeng/components/common/messageservice";
import {Message} from "primeng/components/common/api";

@Component({
  selector: 'app-curier',
  templateUrl: './curier.component.html',
  styleUrls: ['./curier.component.css']
})
export class CurierComponent implements OnInit {

  curier: Curier;
  list: any[];
  startList: any[];
  options: any = {
    removeOnSpill: true
  };
  msgs: Message[] = [];


  constructor(private messageService: MessageService, private parcelService: ParcelService,private dragulaService: DragulaService, private curierService: CurierService, private route: ActivatedRoute) {
    dragulaService.setOptions('bag-one', {
      removeOnSpill: true
    });

    dragulaService.drag.subscribe((value) => {
      this.onDrag(value.slice(1));
    });
    dragulaService.drop.subscribe((value) => {
      this.onDrop(value.slice(1));
    });
    dragulaService.over.subscribe((value) => {
      this.onOver(value.slice(1));
    });
    dragulaService.out.subscribe((value) => {
      this.onOut(value.slice(1));
    });
  }


  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');

    this.curierService.getCurier(+id).subscribe(data => {

        this.curier = data;
        console.log(data);

  this.parcelService.getParcelsByCarId( +this.curier.car.id).subscribe(r =>{
    this.list = r;
  })

      },
      error2 => console.log(error2.header.status))

  }

  private hasClass(el: any, name: string) {
    return new RegExp('(?:^|\\s+)' + name + '(?:\\s+|$)').test(el.className);
  }

  private addClass(el: any, name: string) {
    if (!this.hasClass(el, name)) {
      el.className = el.className ? [el.className, name].join(' ') : name;
    }
  }

  private removeClass(el: any, name: string) {
    if (this.hasClass(el, name)) {
      el.className = el.className.replace(new RegExp('(?:^|\\s+)' + name + '(?:\\s+|$)', 'g'), '');
    }
  }

  private onDrag(args) {

    let [e, el] = args;
    this.removeClass(e, 'ex-moved');
  }

  private onDrop(args) {
    let [e, el] = args;
    this.addClass(e, 'ex-moved');

  }

  private onOver(args) {
    let [e, el, container] = args;
    this.addClass(el, 'ex-over');
  }

  private onOut(args) {
    let [e, el, container] = args;
    this.removeClass(el, 'ex-over');
  }

  updateParcels(){
   this.parcelService.updateParcels(this.list).subscribe(data =>{


     this.messageService.add({severity:'success', summary:'Changes saved!'});
   })
  }
}


