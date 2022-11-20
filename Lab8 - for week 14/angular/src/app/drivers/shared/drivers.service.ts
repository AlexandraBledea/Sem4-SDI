import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {Driver, DriversDTO} from "../../shared/drivers.model";
import {PagingResponse} from "../../shared/pagingResponse.model";
import {Bus} from "../../shared/buses.model";

@Injectable({
  providedIn: 'root'
})
export class DriversService {


  private backendUrl = 'http://localhost:8085/api/drivers';
  constructor(private httpClient: HttpClient) { }

  getPagingResponse(pageNumber: number, pageSize: number): Observable<PagingResponse<Driver>>{
    let url = `${this.backendUrl}/${pageNumber}/${pageSize}`;
    return this.httpClient
      .get<PagingResponse<Driver>>(url);
  }

  getDrivers(): Observable<DriversDTO>{
    return this.httpClient
      .get<DriversDTO>(this.backendUrl);
  }

  getDriver(cnp: string): Observable<Driver>{
    const url = `${this.backendUrl}/getDriver/${cnp}`
    return this.httpClient.get<Driver>(url);
  }

  deleteDriver(id: number): Observable<any>{
    let url = `${this.backendUrl}/${id}`
    return this.httpClient.delete(url);
  }

  updateDriver(id: number, name: string){
    let url = `${this.backendUrl}/${id}`
    return this.httpClient.put(url, {
      name: name
    })
  }

  addDriver(name: string, cnp: string){
    return this.httpClient.post(this.backendUrl, {
      name: name,
      cnp: cnp,
      bus: null
    })
  }
}
