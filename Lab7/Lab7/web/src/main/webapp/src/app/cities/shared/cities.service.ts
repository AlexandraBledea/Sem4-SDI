import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {CitiesDto, City} from "../../shared/cities.model";
import {PagingResponse} from "../../shared/pagingResponse.model";

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


  getPagingResponse(pageNumber: number, pageSize: number): Observable<PagingResponse<City>>{
    let url = `${this.backendUrl}/${pageNumber}/${pageSize}`;
    return this.httpClient
      .get<PagingResponse<City>>(url);
  }

  getCities(): Observable<CitiesDto>{
    return this.httpClient
      .get<CitiesDto>(this.backendUrl);
  }

  getCity(name: string): Observable<City>{
    const url = `${this.backendUrl}/getCity/${name}`;
    return this.httpClient.get<City>(url);
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
