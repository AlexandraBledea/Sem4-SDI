package busManagement.domain.validators;

import busManagement.domain.Driver;
import busManagement.domain.exceptions.ValidatorException;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class DriverValidatorTest {
    private Driver driver;
    private DriverValidator validator;

    @Before
    public void setUp() {
        driver = new Driver( "Ion", "Popescu", LocalDate.of(2000, 7, 15), 10);
        driver.setId(1L);
        validator = new DriverValidator();
    }

    @Test
    public void testPersonValidity() {
        driver.setId(null);

        Throwable exception = assertThrowsExactly(ValidatorException.class, () -> validator.validate(driver));
        assertEquals(exception.getMessage(), "Id must be non null and positive!\n");
    }

    @Test
    public void testMonthsNotNull() {
        driver.setMonthsActive(null);

        Throwable exception = assertThrowsExactly(ValidatorException.class, () -> validator.validate(driver));
        assertEquals(exception.getMessage(), "Months active must be non null and positive!\n");
    }

    @Test
    public void testMonthsPositive() {
        driver.setMonthsActive(-1);

        Throwable exception = assertThrowsExactly(ValidatorException.class, () -> validator.validate(driver));
        assertEquals(exception.getMessage(), "Months active must be non null and positive!\n");
    }
}
