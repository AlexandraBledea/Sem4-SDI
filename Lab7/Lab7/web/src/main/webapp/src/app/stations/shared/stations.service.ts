import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {BusStationsDTO, BusStation} from "../../shared/stations.model";
import {PagingResponse} from "../../shared/pagingResponse.model";

@Injectable({
  providedIn: 'root'
})
export class StationsService {

  private backendUrl = 'http://localhost:8085/api/stations';

  constructor(private httpClient: HttpClient) { }

  getPagingResponse(pageNumber: number, pageSize: number): Observable<PagingResponse<BusStation>>{
    let url = `${this.backendUrl}/${pageNumber}/${pageSize}`;
    return this.httpClient
      .get<PagingResponse<BusStation>>(url);
  }

  getStations(): Observable<BusStationsDTO>{
    return this.httpClient
      .get<BusStationsDTO>(this.backendUrl);
  }

  getStation(name: string): Observable<BusStation>{
    const url = `${this.backendUrl}/getStation/${name}`;
    return this.httpClient.get<BusStation>(url);
  }

  deleteStation(id: number): Observable<any> {
    let url = `${this.backendUrl}/${id}`
    return this.httpClient.delete(url)
  }

  updateStation(id: number, name: string){
    let url = `${this.backendUrl}/${id}`
    return this.httpClient.put(url, {
      name: name
    })
  }

  addStation(name: string, cityId: number){
    return this.httpClient.post(this.backendUrl, {
      name: name,
      cityId: cityId
    })
  }
}
