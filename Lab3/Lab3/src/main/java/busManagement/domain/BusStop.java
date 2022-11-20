package busManagement.domain;

import busManagement.utils.Pair;

import java.time.LocalTime;

public class BusStop extends BaseEntity<Pair<Long, Long>>{

    private LocalTime StopTime;


    public BusStop(LocalTime stopTime) {
        StopTime = stopTime;
    }

    public Long getBusId() {
        return this.getId().getFirst();
    }

    public Long getBusStationId() {
        return this.getId().getSecond();
    }

    public LocalTime getStopTime() {
        return StopTime;
    }

    public void setStopTime(LocalTime stopTime) {
        StopTime = stopTime;
    }

    @Override
    public String toString() {
        return "BusStop{" +
                "BusId=" + this.getId().getFirst() +
                ", BusStationId=" + this.getId().getSecond() +
                ", StopTime=" + StopTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (!(o instanceof BusStop)) return false;

        BusStop busStop = (BusStop) o;

        return getBusId().equals(busStop.getBusId())
                && getBusStationId().equals(busStop.getBusStationId())
                && getStopTime().equals(busStop.getStopTime());
    }


}
