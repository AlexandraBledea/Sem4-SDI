package core.domain;


import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.time.LocalTime;


@Entity
@IdClass(BusStopPK.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "busstop")
public class BusStop implements Serializable {

    @Id
    @ManyToOne(optional = false, fetch=FetchType.EAGER, cascade = {CascadeType.ALL})
    @JsonIgnore
    @JoinColumn(name = "busid", referencedColumnName = "id")
    private Bus bus;

    @Id
    @ManyToOne(optional = false, fetch=FetchType.EAGER, cascade = {CascadeType.ALL})
    @JsonIgnore
    @JoinColumn(name = "stationid", referencedColumnName = "id")
    private BusStation station;

    @Column(name = "stoptime")
    private String stopTime;

    @Override
    public String toString() {
        return "BusStop{" +
                "BusId=" + bus.getId()+
                ", BusStationId=" + station.getId() +
                ", StopTime=" + stopTime +
                '}' + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof BusStop && getStopTime().equals(((BusStop) o).getStopTime());
    }


}
