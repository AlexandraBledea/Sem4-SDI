import {Bus} from "./buses.model";

export class Driver{
  id: number = 0;
  name: string = "";
  cnp: string = "";
  bus: Bus | undefined;

  constructor(name: string = "", cnp: string = "") {
    this.name = name;
    this.cnp = cnp;
  }
}

export class DriversDTO{
  drivers: Array<Driver> = []
}
