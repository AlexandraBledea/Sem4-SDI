package ro.ubb.catalog.core.model;

import lombok.*;

import javax.persistence.*;
import java.util.Optional;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "driver")
@EqualsAndHashCode
@ToString
public class Driver extends BaseEntity<Long>{

    @Column(name = "name")
    private String name;

    @Column(name = "cnp", unique = true)
    private String cnp;

}

































//@NamedEntityGraphs({
//        @NamedEntityGraph(name = "driversWithBuses",
//            attributeNodes = @NamedAttributeNode(value = "bus")),
//
//        @NamedEntityGraph(name = "driverWithBusesAndStations",
//            attributeNodes = {@NamedAttributeNode(value = "bus", subgraph = "busWithDriver"),
//            @NamedAttributeNode(value = "bus", subgraph = "busWithStops")},
//        subgraphs = {
//                @NamedSubgraph(name = "busWithDriver", attributeNodes = @NamedAttributeNode(value = "driver")),
//                @NamedSubgraph(name = "busWithStops", attributeNodes = @NamedAttributeNode(value = "stops", subgraph = "stopsWithStation")),
//                @NamedSubgraph(name = "stopsWithStations", attributeNodes = @NamedAttributeNode(value = "station", subgraph = "stationWithCity")),
//                @NamedSubgraph(name = "stationWithCity", attributeNodes = @NamedAttributeNode(value = "city"))
//
//        })
//})

//@NamedEntityGraph(name = "driverWithBusAndStations",
//        attributeNodes = @NamedAttributeNode(value = "bus", subgraph = "busWithStops"),
//        subgraphs = {
//                @NamedSubgraph(name="stopsWithStation",
//                        attributeNodes = @NamedAttributeNode(value = "station", subgraph = "stationWithCity")),
//                @NamedSubgraph(name = "stationWithCity", attributeNodes = @NamedAttributeNode("city"))
//        })