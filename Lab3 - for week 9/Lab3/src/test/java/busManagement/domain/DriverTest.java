package busManagement.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class DriverTest {
    private static final Long id1 = 1L;
    private static final Long id2 = 2L;
    private static final String fName1 = "Ion";
    private static final String lName1 = "Popescu";
    private static final String newFName = "Gigel";
    private static final String fName2 = "Vasile";
    private static final String lName2 = "Ionescu";
    private static final LocalDate date1 = LocalDate.of(2000, 7, 15);
    private static final LocalDate date2 = LocalDate.of(2001, 4, 19);
    private static final Integer months1 = 10;
    private static final Integer months2 = 7;
    private static final Integer newMonths = 11;

    private Driver driver1;
    private Driver driver2;

    @Before
    public void setUp() {
        driver1 = new Driver( fName1, lName1, date1, months1);
        driver2 = new Driver( fName2, lName2, date2, months2);
        driver1.setId(id1);
        driver2.setId(id2);
    }

    @Test
    public void testGetMonthsActive() {
        assertEquals(driver1.getMonthsActive(), months1);

        driver1.setMonthsActive(newMonths);

        assertEquals(driver1.getMonthsActive(), newMonths);
    }

    @Test
    public void testEquality() {
        assertEquals(driver1, driver1);
        assertNotEquals(driver1, 12);

        assertNotEquals(driver1, driver2);
        driver2.setId(id1);
        driver2.setFirstName(fName1);
        driver2.setLastName(lName1);
        driver2.setDateOfBirth(date1);
        driver2.setMonthsActive(months1);

        assertEquals(driver1, driver2);
    }

    @Test
    public void testToString() {
        assertEquals(
                "Driver{Id=1, First Name=Ion, Last Name=Popescu, Date of Birth=Jul 15, 2000, Months Active=10}",
                driver1.toString());

        driver1.setFirstName(newFName);

        assertEquals(
                "Driver{Id=1, First Name=Gigel, Last Name=Popescu, Date of Birth=Jul 15, 2000, Months Active=10}",
                driver1.toString());
    }
}
