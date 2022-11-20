import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {Bus, BusesDTO} from "../../shared/buses.model";

@Injectable({
  providedIn: 'root'
})
export class BusesService {

  private backendUrl = 'http://localhost:8085/api/buses';

  constructor(private httpClient: HttpClient) { }

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

  getBus(id: number): Observable<any>{
    // @ts-ignore
    return this.getBuses()
      .pipe(
        map(buses => buses.buses.find(bus => bus.id === id))
      );
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

  addBus(modelName: string, fuel: string, capacity: number){
    return this.httpClient.post(this.backendUrl, {
      modelName:modelName,
      fuel: fuel,
      capacity: capacity
    })
  }

}
