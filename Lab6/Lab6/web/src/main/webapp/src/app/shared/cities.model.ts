export class City{
  id: number = 0;
  name: string = "";
  population: number = 0;

  constructor(name: string = "", population: number = 0) {
    this.name = name;
    this.population = population;
  }
  // public constructor(init?: Partial<Bus>) {
  //   Object.assign(this, init);
  // }

}


export class CitiesDto{
  cities: Array<City> = []
}
