import { Component, OnInit } from '@angular/core';
import {BusStation} from "../../shared/stations.model";
import {StationsService} from "../shared/stations.service";
import {Router} from "@angular/router";
import {City} from "../../shared/cities.model";
import {CitiesService} from "../../cities/shared/cities.service";
import {PagingResponse} from "../../shared/pagingResponse.model";

@Component({
  selector: 'app-station-list',
  templateUrl: './station-list.component.html',
  styleUrls: ['./station-list.component.css']
})
export class StationListComponent implements OnInit {

  errorMessage: string = "";
  stations: Array<BusStation> = [];
  cities: Array<City> = [];
  id: number = 0;
  city: City = new City();

  selectedOption: string = '';

  pageSize: number = 2;
  currentPageNumber: number = 0;

  pagingResponse: PagingResponse<BusStation> = new PagingResponse<BusStation>()

  constructor(private stationService: StationsService,
              private router: Router,
              private cityService: CitiesService) { }

  ngOnInit(): void {
    this.getPagingResponse()
    this.getCities();
  }

  getPagingResponse(){
    this.stationService.getPagingResponse(this.currentPageNumber, this.pageSize)
      .subscribe(
        response => {
          this.stations = response.elements
          this.currentPageNumber = response.pageNumber
          this.pagingResponse = response
          console.log(response)
        }
      )
  }

  getCities(){
    this.cityService.getCities()
      .subscribe(
        cities => {
          this.cities = cities.cities
          console.log(cities)
        }
      )
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
    (<HTMLInputElement>document.getElementById("name2")).value = (station.name);
    this.id = station.id;

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

  addStation(newName: HTMLInputElement) {
    if(this.selectedOption == "")
      return;
    if(this.selectedOption == "choose city") {
      return;
    }
    this.stationService.addStation(newName.value, this.city.id).subscribe(
      r => {
        console.log(r);
        this.ngOnInit();
      }
    );

    (<HTMLInputElement>document.getElementById("name1")).value = "";
    // (<HTMLInputElement>document.getElementById("cityId1")).value = "";
  }

  updateStation(uName: HTMLInputElement) {

    this.stationService.updateStation(this.id, uName.value).subscribe(
      r => {
        console.log(r);
        document.getElementById('update_form')!.style.display = "none";
        this.ngOnInit();

      }
    )
  }

  setCity() {
    if(this.selectedOption == "")
      return;
    if(this.selectedOption == "choose city") {
      return;
    }
    this.cityService.getCity(this.selectedOption).subscribe(c => {
      this.city = c
      console.log(c.id + " " + c.name + " " + c.population)
    })
  }

  previous() {
    console.log(this.currentPageNumber)
    if(this.currentPageNumber - 1 > -1){
      this.currentPageNumber -= 1
      this.getPagingResponse()
    }
  }

  next() {
    console.log(this.currentPageNumber)
    if(this.currentPageNumber + 1 < this.pagingResponse.pageTotal){
      this.currentPageNumber += 1
      this.getPagingResponse()
    }
  }
}
