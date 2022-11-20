package core.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true, exclude = {"stops"})
@ToString(callSuper = true, exclude = { "stops" })
@Table(name = "busstation")
public class BusStation extends BaseEntity<Long>{

    @Column(name = "name", unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "cityid" )
    City city;

    @OneToMany(mappedBy = "station", cascade = {CascadeType.ALL}, fetch=FetchType.EAGER, orphanRemoval = true)
    @JsonIgnore
    Set<BusStop> stops;

    public BusStation(City c, String name) {
        this.city = c;
        this.name = name;
    }

    public Set<BusStop> getStops(){
        stops = stops == null ? new HashSet<>(): stops;
        return stops;
    }

    public int getNumberStops(){
        return stops == null ? 0: stops.size();
    }

    public void addStop(BusStop stop){
        this.stops.add(stop);
        stop.setStation(stop.getStation());
    }

    public void removeStop(Long stationId){
        BusStop stop = this.stops.stream()
                .filter(s -> s.getStation().getId().equals(stationId))
                .collect(Collectors.toList())
                .get(0);

        this.stops.remove(stop);
        stop.setStation(null);
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