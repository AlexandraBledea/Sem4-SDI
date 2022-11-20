import { Injectable } from '@angular/core';
import {map, Observable, repeat} from "rxjs";
import {BusStop, BusStopsDTO} from "../../shared/stops.model";
import {HttpClient} from "@angular/common/http";
import {PagingResponse} from "../../shared/pagingResponse.model";

@Injectable({
  providedIn: 'root'
})
export class StopsService {

  private backendUrl = 'http://localhost:8085/api/stops'

  constructor(private httpClient: HttpClient) { }

  getFilteredByStopTime(pageNumber: number, pageSize: number, stopTime: string): Observable<PagingResponse<BusStop>>{
    let url = `${this.backendUrl}/${stopTime}/${pageNumber}/${pageSize}`;
    return this.httpClient
      .get<PagingResponse<BusStop>>(url);
  }

  getPagingResponse(pageNumber: number, pageSize: number): Observable<PagingResponse<BusStop>>{
    let url = `${this.backendUrl}/${pageNumber}/${pageSize}`;
    return this.httpClient
      .get<PagingResponse<BusStop>>(url);
  }

  getStops(): Observable<BusStopsDTO>{
    return this.httpClient.
    get<BusStopsDTO>(this.backendUrl);
  }

  // getStop(busId: number, stationId: number): Observable<BusStop>{
  //   // @ts-ignore
  //   return this.getStops()
  //     .pipe(map(stops => stops.stops.find(stop => {stop.bus.id === busId && stop.busStation.id === stationId}))
  //     );
  // }

  deleteStop(busId: number, stationId: number): Observable<any>{
    let deleteUrl = `${this.backendUrl}/${busId}/${stationId}`;
    return this.httpClient.delete(deleteUrl);
  }

  updateStop(busId: number, stationId: number, stopTime: string): Observable<any>{
    let url = `${this.backendUrl}/${busId}/${stationId}`
    return this.httpClient.put(url, {
      stopTime: stopTime
    });
  }

  addStop(busId: number, stationId: number, stopTime: string){
    return this.httpClient.post(this.backendUrl, {
      busId: busId,
      stationId: stationId,
      stopTime: stopTime
    });
  }
}



// getBusStop(busId: number, stationId: number): Observable<BusStop> {
//   const url = `${this.backendUrl}/details/${busId}/${stationId}`
//   return this.httpClient.get<BusStop>(url);
// }
