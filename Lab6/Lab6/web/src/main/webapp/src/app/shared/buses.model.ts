export class Bus{
  id: number = 0
  modelName: string = "";
  fuel: string = "";
  capacity: number = 0;

  constructor(modelName: string="", fuel: string="", capacity: number = 0) {
    this.modelName = modelName;
    this.fuel = fuel;
    this.capacity = capacity;
  }

  // public constructor(init?: Partial<Bus>) {
  //   Object.assign(this, init);
  // }
}

export class BusesDTO{
  buses: Array<Bus> = []
}
