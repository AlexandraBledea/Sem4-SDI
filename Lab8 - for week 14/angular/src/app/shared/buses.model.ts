import {Driver} from "./drivers.model";

export class Bus{
  id: number = 0
  modelName: string = "";
  fuel: string = "";
  capacity: number = 0;
  driver: Driver;

  constructor(modelName: string="", fuel: string="", capacity: number = 0, driver: Driver) {
    this.modelName = modelName;
    this.fuel = fuel;
    this.capacity = capacity;
    this.driver = driver;
  }

  // public constructor(init?: Partial<Bus>) {
  //   Object.assign(this, init);
  // }
}

export class BusesDTO{
  buses: Array<Bus> = []
}
