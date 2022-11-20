import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {Bus, BusesDTO} from "../../shared/buses.model";
import {PagingResponse} from "../../shared/pagingResponse.model";
import {BusStopsDTO} from "../../shared/stops.model";

@Injectable({
  providedIn: 'root'
})
export class BusesService {

  private backendUrl = 'http://localhost:8085/api/buses';

  constructor(private httpClient: HttpClient) { }

  findAllStopsForBus(id: number): Observable<BusStopsDTO>{
    let url = `${this.backendUrl}/${id}`
    return this.httpClient
      .get<BusStopsDTO>(url);
  }

  getPagingResponse(pageNumber: number, pageSize: number): Observable<PagingResponse<Bus>>{
    let url = `${this.backendUrl}/${pageNumber}/${pageSize}`;
    return this.httpClient
      .get<PagingResponse<Bus>>(url);
  }

  filterBusesByModelName(modelName: string): Observable<BusesDTO>{
    let url = this.backendUrl + `/filterBusesByModelName/${modelName}`;
    return this.httpClient.get<BusesDTO>(url);
  }

  sortBusesByCapacity():Observable<BusesDTO>{
    let url = this.backendUrl + "/sortBusesByCapacityInAscendingOrder";
    return this.httpClient.get<BusesDTO>(url);
  }

  getBuses(): Observable<BusesDTO>{
    return this.httpClient
      .get<BusesDTO>(this.backendUrl);
  }

  getBus(modelName: string): Observable<Bus>{
    const url = `${this.backendUrl}/getBus/${modelName}`;
    return this.httpClient.get<Bus>(url);

  }

  deleteBus(id: number): Observable<any>{
    let url = `${this.backendUrl}/${id}`
    console.log(url)
    return this.httpClient.delete(url);
  }

  updateBus(bus: Bus){
    let url = `${this.backendUrl}/${bus.id}`
    return this.httpClient.put(url, {
      modelName: bus.modelName,
      fuel: bus.fuel,
      capacity: bus.capacity
    })
  }

  addBus(modelName: string, fuel: string, capacity: number, driverId: number){
    return this.httpClient.post(this.backendUrl, {
      modelName:modelName,
      fuel: fuel,
      capacity: capacity,
      driverId: driverId
    })
  }

}
