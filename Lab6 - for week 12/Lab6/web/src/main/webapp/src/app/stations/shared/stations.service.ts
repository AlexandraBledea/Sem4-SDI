import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {BusStationsDTO, BusStation} from "../../shared/stations.model";

@Injectable({
  providedIn: 'root'
})
export class StationsService {

  private backendUrl = 'http://localhost:8085/api/stations';

  constructor(private httpClient: HttpClient) { }

  getStations(): Observable<BusStationsDTO>{
    return this.httpClient
      .get<BusStationsDTO>(this.backendUrl);
  }

  getStation(id: number): Observable<BusStation>{
    //@ts-ignore
    return this.getStations()
      .pipe(
        map(stations => stations.stations.find(station => station.id === id))
      )
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
