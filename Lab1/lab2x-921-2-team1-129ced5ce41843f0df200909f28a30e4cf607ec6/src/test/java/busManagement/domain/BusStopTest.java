package busManagement.domain;

import busManagement.utils.Pair;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BusStopTest {

    public static final Pair<Long, Long> id = new Pair<Long, Long>(2L, 4L);
    public static final Pair<Long, Long> newID = new Pair<Long, Long>(3L, 5L);
    public static final Long newBusStationID = 5L;
    public static final LocalTime stopTime = LocalTime.NOON;
    public static final LocalTime newStopTime = LocalTime.NOON.minusHours(2);

    private BusStop busStop;
    private BusStop busStop2;

    @BeforeEach
    void setUp() {
        busStop = new BusStop(stopTime);
        busStop.setId(id);
        busStop2 = new BusStop(stopTime);
        busStop2.setId(id);
    }

    @Test
    void getId() {
        assertEquals(id, busStop.getId(), "Buses Ids should be equal");
    }

    @Test
    void getStopTime() {
        assertEquals(stopTime, busStop.getStopTime(), "Stop times should be equal");
    }

    @Test
    void setBusId() {
        busStop.setId(newID);
        assertEquals(newID, busStop.getId(), "Buses Ids should be equal");
    }


    @Test
    void setStopTime() {
        busStop.setStopTime(newStopTime);
        assertEquals(newStopTime, busStop.getStopTime(), "Stop times should be equal");
    }
    @Test
    void testEquals() {
        assertEquals(true, busStop.equals(busStop2), "Objects should be the same");
    }



}
