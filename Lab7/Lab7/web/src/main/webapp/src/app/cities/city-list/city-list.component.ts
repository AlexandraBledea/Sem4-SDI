import {Component, Input, OnInit} from '@angular/core';
import {City} from "../../shared/cities.model";
import {CitiesService} from "../shared/cities.service";
import {Router} from "@angular/router";
import {CityUpdateComponent} from "../city-update/city-update.component";
import {NgxPaginationModule} from 'ngx-pagination';
import {Observable} from "rxjs";
import {PagingResponse} from "../../shared/pagingResponse.model";

@Component({
  selector: 'app-city-list',
  templateUrl: './city-list.component.html',
  styleUrls: ['./city-list.component.css']
})
export class CityListComponent implements OnInit {

  errorMessage: string = "";
  cities: Array<City> = [];
  id: number = 0;

  pageSize: number = 4;
  currentPageNumber: number = 0;

  pagingResponse: PagingResponse<City> = new PagingResponse<City>()

  // count: number = 0;
  // pageOffset: number = 0;
  // pageTotal: number = 0;

  constructor(private cityService: CitiesService,
              private router: Router,  private cityUpdateComponent: CityUpdateComponent) { }

  ngOnInit(): void {
    this.getPagingResponse();
  }

  getPagingResponse(){
    this.cityService.getPagingResponse(this.currentPageNumber, this.pageSize)
      .subscribe(
        response => {
          this.cities = response.elements
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
