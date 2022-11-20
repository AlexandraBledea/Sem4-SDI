import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CitiesComponent } from './cities/cities.component';
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import { CityListComponent } from './cities/city-list/city-list.component';
import {CitiesService} from "./cities/shared/cities.service";
import { StopsComponent } from './stops/stops.component';
import { StopListComponent } from './stops/stop-list/stop-list.component';
import { BusesComponent } from './buses/buses.component';
import { BusListComponent } from './buses/bus-list/bus-list.component';
import { StationsComponent } from './stations/stations.component';
import { StationListComponent } from './stations/station-list/station-list.component';
import { DriversComponent } from './drivers/drivers.component';
import { DriverListComponent } from './drivers/driver-list/driver-list.component';
import {NgxPaginationModule} from 'ngx-pagination';

@NgModule({
  declarations: [
    AppComponent,
    CitiesComponent,
    CityListComponent,
    StopsComponent,
    StopListComponent,
    BusesComponent,
    BusListComponent,
    StationsComponent,
    StationListComponent,
    DriversComponent,
    DriverListComponent
  ],
  imports: [
    NgxPaginationModule,
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [CitiesService],
  bootstrap: [AppComponent]
})
export class AppModule { }
