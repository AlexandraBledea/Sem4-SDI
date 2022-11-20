import { Component, OnInit } from '@angular/core';
import {BusesService} from "../shared/buses.service";
import {Router} from "@angular/router";
import {Bus} from "../../shared/buses.model";
import { Driver } from 'src/app/shared/drivers.model';
import {DriversService} from "../../drivers/shared/drivers.service";
import {PagingResponse} from "../../shared/pagingResponse.model";
import {City} from "../../shared/cities.model";
import {BusStop} from "../../shared/stops.model";

@Component({
  selector: 'app-bus-list',
  templateUrl: './bus-list.component.html',
  styleUrls: ['./bus-list.component.css']
})
export class BusListComponent implements OnInit {

  errorMessage: string = "";
  stops: Array<BusStop> = [];
  buses: Array<Bus> = []
  drivers: Array<Driver> = [];
  driver: Driver = new Driver();
  id: number = 0

  selectedOption: string = '';



  constructor(private busService: BusesService, private driverService: DriversService, private router: Router) { }

  ngOnInit(): void {
    this.getBuses();
    this.getDrivers();
  }

  getDrivers(){
    this.driverService.getDrivers()
      .subscribe(
        drivers => {
          this.drivers = drivers.drivers
          console.log(drivers)
        }
      )
  }

  displayStops(id: number) {
    this.busService.findAllStopsForBus(id)
      .subscribe(
        stops => {
          this.stops = stops.stops;
          console.log(stops);
        }
      )
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
    (<HTMLInputElement>document.getElementById("modelName2")).value = (bus.modelName);
    (<HTMLInputElement>document.getElementById("fuel2")).value = (bus.fuel);
    (<HTMLInputElement>document.getElementById("capacity2")).value = String(bus.capacity);
    this.id = bus.id


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
    if(this.selectedOption == "")
      return;
    if(this.selectedOption == "choose driver") {
      return;
    }
      this.busService.addBus(newModelName.value, newFuel.value, +newCapacity.value, this.driver.id).subscribe(
        r => {
          console.log(r);
          this.ngOnInit();
        }
      )
  }

  updateBus(uModelName: HTMLInputElement, uFuel: HTMLInputElement, uCapacity: HTMLInputElement) {
    let updatedBus: Bus = {
      id: this.id,
      modelName: uModelName.value,
      fuel: uFuel.value,
      capacity: +uCapacity.value,
      driver: new Driver()
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

  setDriver(){
    if(this.selectedOption == "")
      return;
    if(this.selectedOption == "choose driver") {
      return;
    }
    var splitted = this.selectedOption.split(",", 2)
    this.driverService.getDriver(splitted[1]).subscribe(d => {
      this.driver = d
      console.log(d.id + " " + d.name + " " + d.cnp)
    })
  }

}


