import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CitiesComponent} from "./cities/cities.component";
import {StopsComponent} from "./stops/stops.component";
import {BusesComponent} from "./buses/buses.component";
import {StationsComponent} from "./stations/stations.component";
import {DriversComponent} from "./drivers/drivers.component";

const routes: Routes = [
  {path: 'stops', component: StopsComponent},
  {path: 'cities', component: CitiesComponent},
  {path: 'buses', component: BusesComponent},
  {path: 'stations', component: StationsComponent},
  {path: 'drivers', component:DriversComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
