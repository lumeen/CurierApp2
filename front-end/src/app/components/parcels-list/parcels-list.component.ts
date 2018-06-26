import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ParcelService} from "../../services/parcel.service";
import {ParcelReq} from "../../model/model.parcelReq";
import {MessageService} from "primeng/components/common/messageservice";
import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-parcels-list',
  templateUrl: './parcels-list.component.html',
  styleUrls: ['./parcels-list.component.css'],
})
export class ParcelsListComponent implements OnInit {
parcels :any[];
  columns = [
    { name: 'Number' , prop: "id" },
    { name: 'City' , prop: "city"},
    { name: 'Post Code' , prop: "postCode"},
    { name: 'Street' , prop: "streetName"},
    { name: 'Build Number' , prop: "buildNumber"},
    { name: 'Status' , prop: "status"},
  ];
  formGroup: FormGroup;
  displayAllParcels = true;
  displayAdd: boolean = false;
  displaySearch: boolean = false;
  constructor(private formBuilder: FormBuilder,private router: Router,private parcelService: ParcelService, private messageService: MessageService) { }

  ngOnInit() {
    this.formGroup = this.formBuilder.group({
      city: ['', [Validators.required]],
      postCode: ['', [Validators.required]],
      streetName: ['', [Validators.required]],
      buildNumber: ['', [Validators.required]]
    });


    this.parcelService.getAll().subscribe(data=>{

    this.parcels = data;

  })

  }

  onSubmit(form ){




      var errorCounter = 0;
console.log(form.value.city);

      if (form.value.city.length == 0) {
        this.messageService.add({severity: 'error', summary: "City can't be empty"});
        errorCounter++;
      }
      if (form.value.postCode.length == 0) {
        this.messageService.add({severity: 'error', summary: "Post code can't be empty"});
        errorCounter++;
      }
      if (form.value.streetName.length == 0) {
        this.messageService.add({severity: 'error', summary: "Street name can't be empty"});
        errorCounter++;
      }
      if (form.value.buildNumber.length == 0) {
        this.messageService.add({severity: 'error', summary: "Build number can't be empty"});
        errorCounter++;
      }
      if (errorCounter > 0) {
        return;
      }


    this.parcelService.createParcel(new ParcelReq(form.value.city, form.value.postCode, form.value.streetName, form.value.buildNumber)).subscribe(
      date=>{
        this.displayAdd = false;
        this.messageService.add({severity:'success', summary:'Success!', detail:'Parcel added'});
        this.parcelService.getAll().subscribe(date =>{
          this.parcels = date;

        })
      },

      error =>{
        localStorage.removeItem('token');
        this.router.navigate(['/login']);

      }
    )

  }

  findParcel(id: number,){
    this.parcelService.findParcel(id).subscribe(date=>{

      if(date.length == 0)
      {
        this.messageService.add({severity:'error', summary:'No search results'});

      }
      else{
        this.displayAllParcels = false;
        this.parcels = date;
      }
      this.displaySearch=false;
    })
  }

}
