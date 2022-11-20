import { Component, OnInit } from '@angular/core';
import {BusStation} from "../../shared/stations.model";
import {StationsService} from "../shared/stations.service";
import {Router} from "@angular/router";
import {City} from "../../shared/cities.model";

@Component({
  selector: 'app-station-list',
  templateUrl: './station-list.component.html',
  styleUrls: ['./station-list.component.css']
})
export class StationListComponent implements OnInit {

  errorMessage: string = "";
  stations: Array<BusStation> = [];

  constructor(private stationService: StationsService,
              private router: Router) { }

  ngOnInit(): void {
    this.getStations();
  }

  getStations(){
    this.stationService.getStations()
      .subscribe(
        stations => {
          this.stations = stations.stations
          console.log(stations)
        }
      )
  }

  onUpdate(station: BusStation) {
    (<HTMLInputElement>document.getElementById("id2")).value = String(station.id);
    (<HTMLInputElement>document.getElementById("name2")).value = (station.name);

    let displayVal:string = document.getElementById('update_form')!.style.display;
    if (displayVal === "none")
      document.getElementById('update_form')!.style.display = "inline";
    else document.getElementById('update_form')!.style.display = "none";

  }

  onDelete(id: number) {
    this.stationService.deleteStation(id)
      .subscribe(r => {
        console.log(r);
        this.ngOnInit();
      });
  }

  addStation(newName: HTMLInputElement, newCityId: HTMLInputElement) {
    this.stationService.addStation(newName.value, +newCityId.value).subscribe(
      r => {
        console.log(r);
        this.ngOnInit();
      }
    );

    (<HTMLInputElement>document.getElementById("name1")).value = "";
    (<HTMLInputElement>document.getElementById("cityId1")).value = "";
  }

  updateStation(uID: HTMLInputElement, uName: HTMLInputElement) {
    this.stationService.updateStation(+uID.value, uName.value).subscribe(
      r => {
        console.log(r);
        document.getElementById('update_form')!.style.display = "none";
        this.ngOnInit();

      }
    )
  }
}
