import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {NgxDatatableModule} from '@swimlane/ngx-datatable';
import {AppComponent} from './app.component';
import {LoginComponent} from './components/login/login.component';
import {RegisterComponent} from './components/register/register.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AuthService} from "./services/auth.service";
import {AccountService} from "./services/account.service";
import {ProfileComponent} from './components/profile/profile.component';
import {routing} from "./app.routing";
import {FacebookModule} from "ngx-facebook";
import {UrlPermission} from "./urlPermission/url.permission";
import {HttpClientModule} from "@angular/common/http";
import {HeaderComponent} from "./components/header/header.component";
import {CouriersListComponent} from './components/couriers-list/couriers-list.component';
import {CourierService} from "./services/courier.service";
import {DialogModule} from "primeng/dialog";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {GrowlModule} from 'primeng/growl';
import {MessageService} from "primeng/components/common/messageservice";

import {AgmCoreModule} from "@agm/core";
import { CarPositionComponent } from './components/car-position/car-position.component';
import {DragDropDirectiveModule} from "angular4-drag-drop";
import {DragulaModule} from "ng2-dragula";
import {ParcelService} from "./services/parcel.service";
import { ParcelsListComponent } from './components/parcels-list/parcels-list.component';
import {CourierComponent} from "./components/courier/courier.component";


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    ProfileComponent,
    HeaderComponent,
    CouriersListComponent,
    CourierComponent,
    CarPositionComponent,
    ParcelsListComponent,

  ],
  imports: [
    DragulaModule,GrowlModule, DialogModule, BrowserAnimationsModule, BrowserModule, HttpClientModule, FormsModule, routing, FacebookModule.forRoot(), NgxDatatableModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyBMmm_Q-BzFlb5Hx-b74uHOA4f2PY9jxXE'
    }),
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [AuthService, AccountService, UrlPermission, CourierService, MessageService, ParcelService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
