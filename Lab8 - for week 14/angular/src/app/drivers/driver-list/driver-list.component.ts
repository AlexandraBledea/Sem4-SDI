import { Component, OnInit } from '@angular/core';
import {Driver} from "../../shared/drivers.model";
import {DriversService} from "../shared/drivers.service";
import {Router} from "@angular/router";
import {PagingResponse} from "../../shared/pagingResponse.model";

@Component({
  selector: 'app-driver-list',
  templateUrl: './driver-list.component.html',
  styleUrls: ['./driver-list.component.css']
})
export class DriverListComponent implements OnInit {

  errorMessage: string = "";
  drivers: Array<Driver> = [];
  id: number = 0;


  constructor(private driverService: DriversService, private router: Router) { }

  ngOnInit(): void {
    this.getDrivers();
  }

  getDrivers(){
    this.driverService.getDrivers()
      .subscribe(
        drivers => {
          this.drivers = drivers.drivers
          console.log(drivers)
        },
        error => this.errorMessage = <any>error
      );
  }

  onUpdate(driver: Driver){
    (<HTMLInputElement>document.getElementById("name2")).value = (driver.name);
    this.id = driver.id;

    let displayVal:string = document.getElementById('update_form')!.style.display;
    if (displayVal === "none")
      document.getElementById('update_form')!.style.display = "inline";
    else document.getElementById('update_form')!.style.display = "none";
  }

  onDelete(id: number){
    this.driverService.deleteDriver(id)
      .subscribe(r => {
        console.log(r)
        this.ngOnInit()
      });
  }

  updateDriver(uName: HTMLInputElement){
    this.driverService.updateDriver(this.id, uName.value).subscribe(
      r => {
        console.log(r);
        document.getElementById('update_form')!.style.display = "none";
        this.ngOnInit();
      }
    )
  }

  addDriver(newName: HTMLInputElement, newCnp: HTMLInputElement){
    this.driverService.addDriver(newName.value, newCnp.value).subscribe(
      r => {
        console.log(r)
        this.ngOnInit()
      }
    );
    (<HTMLInputElement>document.getElementById("name1")).value = "";
    (<HTMLInputElement>document.getElementById("cnp1")).value = "";
  }

}
