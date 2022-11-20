import { Component, OnInit } from '@angular/core';
import {BusStop} from "../../shared/stops.model";
import {StopsService} from "../shared/stops.service";
import {City} from "../../shared/cities.model";
import {Router} from "@angular/router";

@Component({
  selector: 'app-stop-list',
  templateUrl: './stop-list.component.html',
  styleUrls: ['./stop-list.component.css']
})
export class StopListComponent implements OnInit {
  errorMessage: string = "";
  stops: Array<BusStop> = [];

  constructor(private stopService: StopsService,
              private router: Router) { }

  ngOnInit(): void {
    this.getStops();
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
    (<HTMLInputElement>document.getElementById("busId2")).value = String(stop.bus.id);
    (<HTMLInputElement>document.getElementById("stationId2")).value = String(stop.busStation.id);
    (<HTMLInputElement>document.getElementById("stopTime2")).value = stop.stopTime;

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

  updateStop(uBusId: HTMLInputElement, uStationId: HTMLInputElement, uStopTime: HTMLInputElement) {
    this.stopService.updateStop(+uBusId.value, +uStationId.value, uStopTime.value)
      .subscribe(
        r => {
          console.log(r);
          document.getElementById('update_form')!.style.display = "none";
          this.ngOnInit();
        }
      )
  }

  addStop(newBusId: HTMLInputElement, newStationId: HTMLInputElement, newStopTime: HTMLInputElement) {
    this.stopService.addStop(+newBusId.value, +newStationId.value, newStopTime.value)
      .subscribe(
        r => {
          console.log(r)
          this.ngOnInit();
        }
      );

    (<HTMLInputElement>document.getElementById("busId1")).value = "";
    (<HTMLInputElement>document.getElementById("stationId1")).value = "";
    (<HTMLInputElement>document.getElementById("stopTime1")).value = "";


  }
}
