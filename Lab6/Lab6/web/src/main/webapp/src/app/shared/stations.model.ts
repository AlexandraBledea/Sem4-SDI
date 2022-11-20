import {City} from "./cities.model";
import {BusStop} from "./stops.model";

export class BusStation{
  id: number = 0
  name: string;
  city: City;

  constructor(name: string="", city: City) {
    this.name = name;
    this.city = city;
  }

}

export class BusStationsDTO{
  stations: Array<BusStation> = []
}
