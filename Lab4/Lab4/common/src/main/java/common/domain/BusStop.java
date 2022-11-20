package common.domain;


import common.utils.Pair;

import java.time.LocalTime;

public class BusStop extends BaseEntity<Pair<Long, Long>>{

    private LocalTime StopTime;


    public BusStop(Long busId, Long stationId, LocalTime stopTime) {
        this.setId(new Pair<>(busId, stationId));
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
        return o instanceof BusStop && getBusId().equals(((BusStop) o).getBusId())
                && getBusStationId().equals(((BusStop) o).getBusStationId())
                && getStopTime().equals(((BusStop) o).getStopTime());
    }


}
