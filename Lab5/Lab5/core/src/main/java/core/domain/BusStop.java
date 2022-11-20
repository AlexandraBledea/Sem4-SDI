package core.domain;



import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalTime;


@Entity
@Table(name = "busstop")
public class BusStop extends BaseEntity<Long>{
    public BusStop(){}

    @ManyToOne
    @JoinColumn(name = "busid")
    Bus bus;

    @ManyToOne
    @JoinColumn(name = "stationid")
    BusStation station;

    @Column(name = "stoptime")
    private String stopTime;

    public BusStop(Long id,  String stopTime, Bus bus, BusStation station) {
        this.setId(id);
        this.stopTime = stopTime;
        this.bus = bus;
        this.station = station;
    }

    public Bus getBus(){return this.bus;}

    public void setBus(Bus bus){this.bus = bus;}

    public BusStation getStation(){return this.station;}

    public void setStation(BusStation station){this.station = station;}

    public Long getBusId(){return this.bus.getId();}

    public void setBusId(Long busId){this.bus.setId(busId);}

    public Long getStationId(){return this.station.getId();}

    public void setStationId(Long stationId){this.station.setId(stationId);}

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    @Override
    public String toString() {
        return "BusStop{" +
                "BusId=" + this.getBusId()+
                ", BusStationId=" + this.getStationId() +
                ", StopTime=" + stopTime +
                '}' + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof BusStop && getStopTime().equals(((BusStop) o).getStopTime());
    }


}
