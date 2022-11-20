import {Bus} from "./buses.model";
import {BusStation} from "./stations.model";

export class BusStop{
  bus: Bus;
  busStation: BusStation;
  stopTime: string;

  constructor(bus: Bus, busStation: BusStation, stopTime: string = "") {
    this.stopTime = stopTime;
    this.bus = bus;
    this.busStation = busStation;
  }

}

export class BusStopsDTO{
  stops: Array<BusStop> = []
}



// export class BusStopSave {
//   busId: number;
//   stationId: number;
//   stopTime: string;
//
//   constructor(busId: number, stationId: number, stopTime: string = "") {
//     this.stopTime = stopTime;
//     this.busId = busId;
//     this.stationId = stationId;
//   }
// }
//
// export class BusStopUpdate{
//   stopTime: string;
//
//   constructor(stopTime: string) {
//     this.stopTime = stopTime;
//   }
// }
