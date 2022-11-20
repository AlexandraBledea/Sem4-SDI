package busManagement.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class PersonTest {
    private static final Long id1 = 1L;
    private static final Long id2 = 2L;
    private static final Long newId = 4L;
    private static final String fName1 = "Ion";
    private static final String lName1 = "Popescu";
    private static final String newFName = "Gigel";
    private static final String newLName = "Vasilescu";
    private static final String fName2 = "Vasile";
    private static final String lName2 = "Ionescu";
    private static final LocalDate date1 = LocalDate.of(2000, 7, 15);
    private static final LocalDate date2 = LocalDate.of(2001, 4, 19);
    private static final LocalDate newDate = LocalDate.of(2002, 7, 15);;


    private Person person1;
    private Person person2;

    @Before
    public void setUp() {
        person1 = new Person( fName1, lName1, date1);
        person2 = new Person( fName2, lName2, date2);
        person1.setId(id1);
        person2.setId(id2);
    }

    @Test
    public void testGetId() {
        Long id = person1.getId();

        assertEquals(id1, id, "Person id should be 1!");
        person1.setId(newId);
        assertEquals(newId, person1.getId(), "Person id should be 4!");
    }

    @Test
    public void testGetFirstName() {
        assertEquals(fName1, person1.getFirstName(), "Person first name should be Ion!");

        person1.setFirstName(newFName);

        assertEquals(newFName, person1.getFirstName(), "Person first name should be Gigel!");
    }

    @Test
    public void testGetLastName() {
        assertEquals(lName1, person1.getLastName(), "Person last name should be Popescu!");

        person1.setLastName(newLName);

        assertEquals(newLName, person1.getLastName(), "Person last name should be Vasilescu!");
    }

    @Test
    public void testGetDate() {
        assertEquals(2000, person1.getDateOfBirth().getYear(), "Person year of birth should be 2000!");
        assertEquals(7, person1.getDateOfBirth().getMonth().getValue(), "Person month of birth should be 7!");
        assertEquals(15, person1.getDateOfBirth().getDayOfMonth(), "Person day of birth should be 15!");

        person1.setDateOfBirth(newDate);

        assertEquals(2002, person1.getDateOfBirth().getYear(), "Person year of birth should be 2002!");
        assertEquals(7, person1.getDateOfBirth().getMonth().getValue(), "Person month of birth should be 7!");
        assertEquals(15, person1.getDateOfBirth().getDayOfMonth(), "Person day of birth should be 15!");
    }

    @Test
    public void testToString() {
        assertEquals("Person{" +
                "Id=1, First Name=Ion, Last Name=Popescu, Date of Birth=Jul 15, 2000}", person1.toString(),
                "To String output wrong!");

        person1.setFirstName(newFName);

        assertEquals("Person{" +
                        "Id=1, First Name=Gigel, Last Name=Popescu, Date of Birth=Jul 15, 2000}", person1.toString(),
                "To String output wrong!");

    }

    @Test
    public void testEquality() {
        assertEquals(person1, person1);
        assertNotEquals(12, person1);

        assertNotEquals(person2, person1);
        person2.setId(id1);
        person2.setFirstName(fName1);
        person2.setLastName(lName1);
        person2.setDateOfBirth(date1);

        assertEquals(person1, person2);
    }
}
