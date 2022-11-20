import {Component, Input, OnInit} from '@angular/core';
import {City} from "../../shared/cities.model";
import {CitiesService} from "../shared/cities.service";
import {Router} from "@angular/router";
import {CityUpdateComponent} from "../city-update/city-update.component";

@Component({
  selector: 'app-city-list',
  templateUrl: './city-list.component.html',
  styleUrls: ['./city-list.component.css']
})
export class CityListComponent implements OnInit {

  errorMessage: string = "";
  cities: Array<City> = [];

  constructor(private cityService: CitiesService,
              private router: Router,  private cityUpdateComponent: CityUpdateComponent) { }

  ngOnInit(): void {
    this.getCities();
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

  @Input() onUpdate(city: City) {
    // this.cityUpdateComponent.onUpdate(city)
    (<HTMLInputElement>document.getElementById("id2")).value = String(city.id);
    (<HTMLInputElement>document.getElementById("name2")).value = (city.name);
    (<HTMLInputElement>document.getElementById("population2")).value = String(city.population);

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

  updateCity(uID: HTMLInputElement, uName: HTMLInputElement, uPopulation: HTMLInputElement) {
    let updatedCity: City = {
      id: +uID.value,
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

    (<HTMLInputElement>document.getElementById("id1")).value = "";
    (<HTMLInputElement>document.getElementById("name1")).value = "";
    (<HTMLInputElement>document.getElementById("population1")).value = "";

  }
}
