package ro.ubb.catalog.core.model;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@NamedEntityGraphs({
        @NamedEntityGraph(name = "busesWithDriver",
                attributeNodes = @NamedAttributeNode(value = "driver")),

        @NamedEntityGraph(name = "busesWithDriverAndStops",
            attributeNodes = {@NamedAttributeNode(value="driver"),
                        @NamedAttributeNode(value = "stops", subgraph = "stopsWithStations")},
        subgraphs = {
                @NamedSubgraph(name="stopsWithStation",
                                attributeNodes = @NamedAttributeNode(value="station", subgraph = "stationWithCity")),
                    @NamedSubgraph(name="stationWithCity",
                                    attributeNodes = @NamedAttributeNode(value = "city"))
        })
})
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true, exclude = {"stops", "driver"})
@ToString(callSuper = true, exclude = {"stops", "driver"})
@Table(name = "bus")
public class Bus extends BaseEntity<Long> {


    @Column(name = "modelname", unique = true)
    private String modelName;

    @Column(name = "fuel")
    private String fuel;

    @Column(name = "capacity")
    private int capacity;

    @OneToMany(mappedBy = "bus", cascade = {CascadeType.ALL}, fetch= FetchType.LAZY, orphanRemoval = true)
    Set<BusStop> stops;

    @OneToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "driverid")
    private Driver driver;

    public Bus(String modelName, String fuel, int capacity, Driver driver) {
        this.modelName = modelName;
        this.fuel = fuel;
        this.capacity = capacity;
        this.driver = driver;
    }

    public Set<BusStop> getStops(){
        stops = stops == null ? new HashSet<>(): stops;
        return stops;
    }

    public int getNumberStops(){
        return stops == null ? 0: stops.size();
    }

    public List<BusStation> getStations(){
        stops = stops == null ? new HashSet<>(): stops;
        return this.stops.stream()
                .map(BusStop::getStation)
                .collect(Collectors.toList());
    }

    public void addStop(BusStop stop){
        this.stops.add(stop);
        stop.setBus(stop.getBus());
    }

    public void removeStop(Long busId){
        BusStop stop = this.stops.stream()
                .filter(s -> s.getBus().getId().equals(busId))
                .collect(Collectors.toList())
                .get(0);

        this.stops.remove(stop);
        stop.setBus(null);
    }

    public void updateStop(Long busId, Long stationId, String stopTime){
        BusStop stop = this.stops.stream()
                .filter(s -> s.getBus().getId().equals(busId))
                .filter(s -> s.getStation().getId().equals(stationId))
                .collect(Collectors.toList())
                .get(0);
        stop.setStopTime(stopTime);
    }

}