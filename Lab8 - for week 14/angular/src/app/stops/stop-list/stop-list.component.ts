import { Component, OnInit } from '@angular/core';
import {BusStop} from "../../shared/stops.model";
import {StopsService} from "../shared/stops.service";
import {City} from "../../shared/cities.model";
import {Router} from "@angular/router";
import {BusStation} from "../../shared/stations.model";
import {Bus} from "../../shared/buses.model";
import {Driver} from "../../shared/drivers.model";
import {StationsService} from "../../stations/shared/stations.service";
import {BusesService} from "../../buses/shared/buses.service";
import {PagingResponse} from "../../shared/pagingResponse.model";

@Component({
  selector: 'app-stop-list',
  templateUrl: './stop-list.component.html',
  styleUrls: ['./stop-list.component.css']
})
export class StopListComponent implements OnInit {
  errorMessage: string = "";
  stops: Array<BusStop> = [];
  stations: Array<BusStation> = [];
  buses: Array<Bus> = [];
  stationId: number = 0;
  busId: number = 0;

  bus: Bus = new Bus("", "", 0, new Driver("", ""));
  station: BusStation = new BusStation("", new City());

  selectedBus: string = ""
  selectedStation: string = ""

  constructor(private stopService: StopsService, private stationService: StationsService, private busService: BusesService,
              private router: Router) { }

  ngOnInit(): void {
    this.getStops();
    this.getBuses();
    this.getStations();
  }

  getBuses(){
    this.busService.getBuses()
      .subscribe(
        buses => {
          this.buses = buses.buses
          console.log(buses)
        },
        error => this.errorMessage = <any> error
      )
  }


  getStations(){
    this.stationService.getStations()
      .subscribe(
        stations => {
          this.stations = stations.stations
          console.log(stations)
        },
        error => this.errorMessage = <any> error
      )
  }

  getStops(){
    this.stopService.getStops()
      .subscribe(
        stops => {
          this.stops = stops.stops
          console.log(stops)
        },
        error => this.errorMessage =<any> error
      )
  }

  onUpdate(stop: BusStop) {
    (<HTMLInputElement>document.getElementById("stopTime2")).value = stop.stopTime;
    this.stationId = stop.busStation.id;
    this.busId = stop.bus.id;

    let displayVal:string = document.getElementById('update_form')!.style.display;
    if (displayVal === "none")
      document.getElementById('update_form')!.style.display = "inline";
    else document.getElementById('update_form')!.style.display = "none";
  }

  onDelete(busId: number, stationId: number){
    this.stopService.deleteStop(busId, stationId)
      .subscribe(r => {
        console.log(r)
        this.ngOnInit();
      })
  }

  updateStop(uStopTime: HTMLInputElement) {
    this.stopService.updateStop(this.busId, this.stationId, uStopTime.value)
      .subscribe(
        r => {
          console.log(r);
          document.getElementById('update_form')!.style.display = "none";
          this.ngOnInit();
        }
      )
  }

  addStop(newStopTime: HTMLInputElement) {
    if(this.selectedStation == "")
      return;
    if(this.selectedStation == "choose station") {
      return;
    }
    if(this.selectedBus == "")
      return;
    if(this.selectedBus == "choose bus") {
      return;
    }
    this.stopService.addStop(this.bus.id, this.station.id, newStopTime.value)
      .subscribe(
        r => {
          console.log(r)
          this.ngOnInit();
        }
      );

    (<HTMLInputElement>document.getElementById("stopTime1")).value = "";
  }

  setBus(){
    if(this.selectedBus == "")
      return;
    if(this.selectedBus == "choose bus") {
      return;
    }
    this.busService.getBus(this.selectedBus).subscribe(b => {
      this.bus = b
      console.log(b.id + " " + b.modelName + " " + b.fuel + " " + b.capacity + " " + b.driver.id + " " + b.driver.name + " " + b.driver.cnp)
    })
  }

  setStation(){
    if(this.selectedStation == "")
      return;
    if(this.selectedStation == "choose station") {
      return;
    }
    this.stationService.getStation(this.selectedStation).subscribe(s => {
      this.station = s
      console.log(s.id + " " + s.name + " " + s.city.id + " " + s.city.name + " " + s.city.population)
    })
  }
}
