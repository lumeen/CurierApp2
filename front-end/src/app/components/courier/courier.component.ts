import {Component, OnDestroy, OnInit} from '@angular/core';



import {ActivatedRoute} from "@angular/router";
import {DragulaService} from "ng2-dragula";
import {ParcelService} from "../../services/parcel.service";
import {MessageService} from "primeng/components/common/messageservice";
import {Message} from "primeng/components/common/api";
import {Courier} from "../../model/model.courier";
import {CourierService} from "../../services/courier.service";

@Component({
  selector: 'app-courier',
  templateUrl: './courier.component.html',
  styleUrls: ['./courier.component.css'],

})
export class CourierComponent implements OnInit, OnDestroy {
  ngOnDestroy(): void {
    this.dragulaService.destroy('bag-one')
  }

  courier: Courier;
  list: any[];
  disabled:boolean = true;
  displaySearch: boolean = false;
  options: any = {
    removeOnSpill: true
  };
  msgs: Message[] = [];


  constructor(private messageService: MessageService, private parcelService: ParcelService,private dragulaService: DragulaService, private courierService: CourierService, private route: ActivatedRoute) {
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
    dragulaService.remove.subscribe((value) => {
      this.onRemove(value.slice(1));
    });
  }


  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');

    this.courierService.getCourier(+id).subscribe(data => {

        this.courier = data;
        console.log(data);

  this.parcelService.getParcelsByCarId( +this.courier.car.id).subscribe(r =>{
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

  private onRemove(args) {
    this.disabled=false;
  }
  private onDrop(args) {
    this.disabled=false;
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
     this.parcelService.getParcelsByCarId( +this.courier.car.id).subscribe(r =>{
       this.list = r;
     });
     this.disabled=true;
   })
  }

  addParcel(courierId: number, parcelId: number){
    this.courierService.addParcel(courierId,parcelId).subscribe(data=>{
this.ngOnInit();
      this.messageService.add({severity:'success', summary:'Parcel added!'})
    },
      error => {
        this.displaySearch = false;
        this.messageService.add({severity:'warning', summary:'Parcel does not exists or parcel in transport'})
      },
    () => {this.displaySearch = false;}


    )
}
}


