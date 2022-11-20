import {Component, Injectable, OnInit} from '@angular/core';
import {City} from "../../shared/cities.model";

@Component({
  selector: 'app-city-update',
  templateUrl: './city-update.component.html',
  styleUrls: ['./city-update.component.css']
})

@Injectable({
  providedIn: 'root'
})
export class CityUpdateComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  updateCity(uID: HTMLInputElement, uName: HTMLInputElement, uPopulation: HTMLInputElement) {

  }

  onUpdate(city: City){
    (<HTMLInputElement>document.getElementById("id2")).value = String(city.id);
    (<HTMLInputElement>document.getElementById("name2")).value = (city.name);
    (<HTMLInputElement>document.getElementById("population2")).value = String(city.population);
  }

}
