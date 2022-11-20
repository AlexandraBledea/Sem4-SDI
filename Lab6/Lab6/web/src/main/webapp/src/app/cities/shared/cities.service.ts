import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {CitiesDto, City} from "../../shared/cities.model";

@Injectable({
  providedIn: 'root'
})
export class CitiesService {
  // httpOptions = {
  //   headers: new HttpHeaders({
  //     'Content-Type': 'application/json'
  //   })
  // };

  private backendUrl = 'http://localhost:8085/api/cities';

  constructor(private httpClient: HttpClient) { }


  getCities(): Observable<CitiesDto>{
    return this.httpClient
      .get<CitiesDto>(this.backendUrl);
  }

  getCity(id: number): Observable<City>{
    // @ts-ignore
    return this.getCities()
      .pipe(
        map(cities => cities.cities.find(city => city.id === id))
      );
  }

  deleteCity(id: number): Observable<any> {
    let url = `${this.backendUrl}/${id}`
    return this.httpClient.delete(url)
  }

  updateCity(city: City){
    let url = `${this.backendUrl}/${city.id}`
    return this.httpClient.put(url, {
      name: city.name,
      population: city.population
    })
  }

  addCity(name: string, population: number){
    return this.httpClient.post(this.backendUrl, new City(name, population));
  }

}
