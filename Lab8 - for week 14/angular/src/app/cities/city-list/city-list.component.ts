import {Component, Input, OnInit} from '@angular/core';
import {City} from "../../shared/cities.model";
import {CitiesService} from "../shared/cities.service";
import {Router} from "@angular/router";
import {NgxPaginationModule} from 'ngx-pagination';
import {Observable} from "rxjs";
import {PagingResponse} from "../../shared/pagingResponse.model";
import {BusStation} from "../../shared/stations.model";
import {BusStop} from "../../shared/stops.model";

@Component({
  selector: 'app-city-list',
  templateUrl: './city-list.component.html',
  styleUrls: ['./city-list.component.css']
})
export class CityListComponent implements OnInit {

  errorMessage: string = "";
  cities: Array<City> = [];
  stations: Array<BusStation> = [];
  stops: Array<BusStop> = [];
  id: number = 0;

  currentCityId: number = -1;

  constructor(private cityService: CitiesService,
              private router: Router) { }

  ngOnInit(): void {
    this.getCities();
  }

  displayStations(id: number){
    this.currentCityId = id;
    if(this.currentCityId == -1){
      return;
    }
    this.stops = []
    this.cityService.findAllStationsForCity(id)
      .subscribe(
        stations => {
          this.stations = stations.stations
          console.log(stations)
        }
      )
  }

  displayStops(id: number){
    this.cityService.findAllStopsForStationAndCity(this.currentCityId, id)
      .subscribe(
        stops => {
          this.stops = stops.stops
          console.log(stops)
        }
      )
  }

  getCities(){
    this.cityService.getCities()
      .subscribe(
        cities => {
          this.cities = cities.cities
          console.log(cities)
        },
        error => this.errorMessage = <any>error
      );
  }

  onUpdate(city: City) {
    (<HTMLInputElement>document.getElementById("name2")).value = (city.name);
    (<HTMLInputElement>document.getElementById("population2")).value = String(city.population);
    this.id = city.id

    let displayVal:string = document.getElementById('update_form')!.style.display;
    if (displayVal === "none")
      document.getElementById('update_form')!.style.display = "inline";
    else document.getElementById('update_form')!.style.display = "none";
  }

  onDelete(id: number) {
    this.cityService.deleteCity(id)
      .subscribe(r => {
        console.log(r);
        this.ngOnInit();
      });
  }

  updateCity( uName: HTMLInputElement, uPopulation: HTMLInputElement) {
    let updatedCity: City = {
      id: this.id,
      name: uName.value,
      population: +uPopulation.value
    }
    this.cityService.updateCity(updatedCity).subscribe(
      r => {
        console.log(r);
        document.getElementById('update_form')!.style.display = "none";
        this.ngOnInit();

      }
    )
  }

  addCity(newName: HTMLInputElement, newPopulation: HTMLInputElement) {
    this.cityService.addCity(newName.value, +newPopulation.value).subscribe(
      r => {
        console.log(r);
        this.ngOnInit();
      }
    );

    (<HTMLInputElement>document.getElementById("name1")).value = "";
    (<HTMLInputElement>document.getElementById("population1")).value = "";

  }

}
