import { Component, OnInit } from '@angular/core';
import {BusesService} from "../shared/buses.service";
import {Router} from "@angular/router";
import {Bus} from "../../shared/buses.model";

@Component({
  selector: 'app-bus-list',
  templateUrl: './bus-list.component.html',
  styleUrls: ['./bus-list.component.css']
})
export class BusListComponent implements OnInit {

  errorMessage: string = "";
  buses: Array<Bus> = []
  countStatistics: number = 0
  id: number = 0

  constructor(private busService: BusesService, private router: Router) { }

  ngOnInit(): void {
    this.getBuses()
  }

  getBuses(){
    this.busService.getBuses()
      .subscribe(
        buses => {
          this.buses = buses.buses
          console.log(buses)
        },
        error => this.errorMessage = <any> error
      )
  }

  filterBusesByModelName(filterModelName: HTMLInputElement) {
    this.busService.filterBusesByModelName(filterModelName.value)
      .subscribe(buses => {
        this.buses = buses.buses
        console.log(buses)
      },
        error => this.errorMessage = <any> error
      )
  }

  computeStatisticsModelName(statisticsModelName: HTMLInputElement){
    this.busService.filterBusesByModelName(statisticsModelName.value)
      .subscribe(buses => {
        this.countStatistics = buses.buses.length
          console.log(buses)
        },
        error => this.errorMessage = <any> error
      )
  }

  sortBusesByCapacity() {
    // this.busService.sortBusesByCapacity()
    //   .subscribe(buses => {
    //       this.buses = buses.buses
    //       console.log(buses)
    //     },
    //     error => this.errorMessage = <any> error
    //   )
    this.buses.sort((b1, b2) => {
      if(b1.capacity > b2.capacity){
        return -1;
      }
      if(b1.capacity < b2.capacity){
        return 1;
      }
      return 0;
    })
  }


  onUpdate(bus: Bus) {
    (<HTMLInputElement>document.getElementById("id2")).value = String(bus.id);
    (<HTMLInputElement>document.getElementById("modelName2")).value = (bus.modelName);
    (<HTMLInputElement>document.getElementById("fuel2")).value = (bus.fuel);
    (<HTMLInputElement>document.getElementById("capacity2")).value = String(bus.capacity);

    let displayVal:string = document.getElementById('update_form')!.style.display;
    if (displayVal === "none")
      document.getElementById('update_form')!.style.display = "inline";
    else document.getElementById('update_form')!.style.display = "none";
  }

  onDelete(id: number) {
    this.busService.deleteBus(id)
      .subscribe(r =>{
        console.log(r);
      });

    this.buses = this.buses.filter(function (el){
      return el.id != id
    })
  }

  addBus(newModelName: HTMLInputElement, newFuel: HTMLInputElement, newCapacity: HTMLInputElement) {
      this.busService.addBus(newModelName.value, newFuel.value, +newCapacity.value).subscribe(
        r => {
          console.log(r);
          this.ngOnInit();
        }
      )
  }

  updateBus(uID: HTMLInputElement, uModelName: HTMLInputElement, uFuel: HTMLInputElement, uCapacity: HTMLInputElement) {
    let updatedBus: Bus = {
      id: +uID.value,
      modelName: uModelName.value,
      fuel: uFuel.value,
      capacity: +uCapacity.value
    }

    this.busService.updateBus(updatedBus).subscribe(
      r => {
        console.log(r);
        document.getElementById('update_form')!.style.display = "none";
        this.ngOnInit();
      }
    )
  }

  refreshTable() {
    this.ngOnInit();
  }
}
