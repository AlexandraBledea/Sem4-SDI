package busManagement.domain.validators;

import busManagement.domain.Person;
import busManagement.domain.exceptions.ValidatorException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class PersonValidatorTest {
    private static final Long id = 1L;

    private Person person;
    private PersonValidator validator;

    @Before
    public void setUp() {
        person = new Person( "Ion", "Popescu", LocalDate.of(2000, 7, 15));
        validator = new PersonValidator();
        person.setId(id);
    }

    @Test
    public void testIdNotNull() {
        person.setId(null);
        Throwable exception = assertThrowsExactly(ValidatorException.class, () -> validator.validate(person));
        assertEquals(exception.getMessage(), "Id must be non null and positive!\n");
    }

    @Test
    public void testIdPositive() {
        person.setId(-1L);
        Throwable exception = assertThrowsExactly(ValidatorException.class, () -> validator.validate(person));
        assertEquals(exception.getMessage(), "Id must be non null and positive!\n");
    }

    @Test
    public void testFirstNameNotNull() {
        person.setFirstName(null);
        Throwable exception = assertThrowsExactly(ValidatorException.class, () -> validator.validate(person));
        assertEquals(exception.getMessage(), "First name must be non null and non empty!\n");
    }

    @Test
    public void testFirstNameNotEmpty() {
        person.setFirstName("");
        Throwable exception = assertThrowsExactly(ValidatorException.class, () -> validator.validate(person));
        assertEquals(exception.getMessage(), "First name must be non null and non empty!\n");
    }

    @Test
    public void testLastNameNotNull() {
        person.setLastName(null);
        Throwable exception = assertThrowsExactly(ValidatorException.class, () -> validator.validate(person));
        assertEquals(exception.getMessage(), "Last name must be non null and non empty!\n");
    }

    @Test
    public void testLastNameNotEmpty() {
        person.setLastName("");
        Throwable exception = assertThrowsExactly(ValidatorException.class, () -> validator.validate(person));
        assertEquals(exception.getMessage(), "Last name must be non null and non empty!\n");
    }

    @Test
    public void testDateNotNull() {
        person.setDateOfBirth(null);
        Throwable exception = assertThrowsExactly(ValidatorException.class, () -> validator.validate(person));
        assertEquals(exception.getMessage(), "Date must be non null and before the current date!\n");
    }

    @Test
    public void testDateNotAfterToday() {
        person.setDateOfBirth(LocalDate.now().plusDays(1));
        Throwable exception = assertThrowsExactly(ValidatorException.class, () -> validator.validate(person));
        assertEquals(exception.getMessage(), "Date must be non null and before the current date!\n");
    }
}
