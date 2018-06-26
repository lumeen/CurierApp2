import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from "./components/login/login.component";
import {RegisterComponent} from "./components/register/register.component";
import {ProfileComponent} from "./components/profile/profile.component";
import {UrlPermission} from "./urlPermission/url.permission";
import {CourierComponent} from "./components/courier/courier.component";
import {ParcelService} from "./services/parcel.service";
import {ParcelsListComponent} from "./components/parcels-list/parcels-list.component";


const appRoutes: Routes = [
  { path: 'profile', component: ProfileComponent ,canActivate: [UrlPermission] },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'courier/:id', component: CourierComponent },
  { path: 'parcels', component: ParcelsListComponent },
  // otherwise redirect to profile
  { path: '**', redirectTo: '/login' }
];

export const routing = RouterModule.forRoot(appRoutes);
