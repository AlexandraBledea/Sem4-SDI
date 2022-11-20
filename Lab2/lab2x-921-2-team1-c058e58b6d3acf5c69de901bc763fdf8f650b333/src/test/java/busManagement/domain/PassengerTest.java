package busManagement.domain;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class PassengerTest {
    private static final Long id1 = 1L;
    private static final Long id2 = 2L;
    private static final String firstName1 = "Adrian";
    private static final String firstName2 = "Vasile";
    private static final String lastName1 = "Popescu";
    private static final String lastName2 = "Ionescu";
    private static final LocalDate date1 = LocalDate.of(2001, 4, 10);
    private static final LocalDate date2 = LocalDate.of(2003, 12, 2);
    private static final Integer timesTravelled1 = 10;
    private static final Integer timesTravelled2 = 40;

    private static Passenger passenger1;
    private static Passenger passenger2;

    @Before
    public void setUp(){
        passenger1 = new Passenger(firstName1, lastName1, date1, timesTravelled1);
        passenger2 = new Passenger(firstName2, lastName2, date2, timesTravelled2);
        passenger1.setId(id1);
        passenger2.setId(id2);
    }

    @Test
    public void testGetTimesTravelled(){
        assertEquals(passenger1.getTimesTravelled(), timesTravelled1);

        passenger1.setTimesTravelled(20);

        assertEquals(passenger1.getTimesTravelled(), 20);
    }

    @Test
    public void testEquals(){
        assertNotEquals(passenger1, passenger2);
        assertEquals(passenger1, passenger1);
        assertNotEquals(passenger1, 87);

        passenger2.setId(id1);
        passenger2.setFirstName(firstName1);
        passenger2.setLastName(lastName1);
        passenger2.setDateOfBirth(date1);
        passenger2.setTimesTravelled(timesTravelled2);

        assertEquals(passenger1, passenger2);
    }
}
