package ro.ubb.catalog.core.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true, exclude = {"stations"})
@ToString(callSuper = true, exclude = {"stations"})
@Entity
@NamedEntityGraphs({
        @NamedEntityGraph(name = "citiesWithStations",
            attributeNodes = @NamedAttributeNode(value = "stations")),

        @NamedEntityGraph(name = "citiesWithStationsAndStops",
        attributeNodes = @NamedAttributeNode(value = "stations",
                subgraph = "stationsWithStops"),
        subgraphs = {@NamedSubgraph(name="stationsWithStops",
                attributeNodes = @NamedAttributeNode(value="stops", subgraph = "stationWithBus")),
                @NamedSubgraph(name="stationWithBus",
                        attributeNodes = @NamedAttributeNode(value = "bus", subgraph = "busWithDriver")),
                @NamedSubgraph(name = "busWithDriver",
                        attributeNodes = @NamedAttributeNode(value = "driver"))
        })
})


@Table(name = "city")
public class City extends BaseEntity<Long>{

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "population")
    private int population;

    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
    Set<BusStation> stations;

    public City(String name, int population) {
        this.name = name;
        this.population = population;
    }

    public void deleteStation(BusStation station){
        this.stations.remove(station);
        station.setCity(null);
    }

    public void addStation(BusStation station, City city){
        this.stations.add(station);
        station.setCity(city);
    }

    public void updateStation(BusStation station, City city){
        BusStation busStation = this.stations.stream()
                .filter(s -> s.getCity().getId().equals(city.getId()))
                .collect(Collectors.toList())
                .get(0);
        busStation.setName(station.getName());
    }

}









//        @NamedEntityGraph(name = "citiesWithStationsAndStops",
//            attributeNodes = @NamedAttributeNode(value = "stations",
//                        subgraph = "stationsWithStops"),
//        subgraphs = @NamedSubgraph(name="stationsWithStops",
//            attributeNodes = @NamedAttributeNode(value="stops")))






//    @Override
//    public boolean equals(Object obj)
//    {
//        return obj instanceof City
//                && getName().equals(((City) obj).getName())
//                && getPopulation() == ((City) obj).getPopulation();
//    }
//
//    @Override
//    public String toString(){
//        return "City{" +
//                "name='" + name + '\'' +
//                ", population=" + population
//                +"}" + super.toString();
//    }