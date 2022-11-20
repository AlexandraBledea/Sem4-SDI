package busManagement.domain.validators;

import busManagement.domain.Bus;
import busManagement.domain.exceptions.ValidatorException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BusValidatorTest {

    private static Bus bus1;
    private static Bus bus2;
    private static Bus bus3;
    private static Bus bus4;
    private static Bus bus5;
    private static Bus bus6;
    private static Bus bus7;
    private static Bus bus8;

    private static BusValidator busValidator;

    @Before
    public void setUp() {

        busValidator = new BusValidator();

        bus1 = new Bus(101L, 201L, "ElectricBus");
        bus1.setId(-1L);

        bus2 = new Bus(101L, 202L, "ElectricBus");
        bus2.setId(null);

        bus3 = new Bus(-1L, 201L, "ElectricBus");
        bus3.setId(0L);

        bus4 = new Bus(null, 201L, "ElectricBus");
        bus4.setId(1L);

        bus5 = new Bus(101L, -1L, "ElectricBus");
        bus5.setId(2L);

        bus6 = new Bus(101L, null, "ElectricBus");
        bus6.setId(3L);

        bus7 = new Bus(101L, 201L, "");
        bus7.setId(4L);

        bus8 = new Bus(101L, 202L, null);
        bus8.setId(5L);
    }

    @Test
    public void testIdNotNegative() {

        Throwable exc = assertThrowsExactly(ValidatorException.class, () -> busValidator.validate(bus1));
        assertEquals(exc.getMessage(), "Id must not be null or negative!\n");
    }

    @Test
    public void testIdNotNull() {

        Throwable exc = assertThrowsExactly(ValidatorException.class, () -> busValidator.validate(bus2));
        assertEquals(exc.getMessage(), "Id must not be null or negative!\n");
    }

    @Test
    public void testCompanyIdNotNegative() {

        Throwable exc = assertThrowsExactly(ValidatorException.class, () -> busValidator.validate(bus3));
        assertEquals(exc.getMessage(), "CompanyId must not be null or negative!\n");
    }

    @Test
    public void testCompanyIdNotNull() {

        Throwable exc = assertThrowsExactly(ValidatorException.class, () -> busValidator.validate(bus4));
        assertEquals(exc.getMessage(), "CompanyId must not be null or negative!\n");
    }

    @Test
    public void testDriverIdNotNegative() {

        Throwable exc = assertThrowsExactly(ValidatorException.class, () -> busValidator.validate(bus5));
        assertEquals(exc.getMessage(), "DriverId must not be null or negative!\n");
    }

    @Test
    public void testDriverIdNotNull() {

        Throwable exc = assertThrowsExactly(ValidatorException.class, () -> busValidator.validate(bus6));
        assertEquals(exc.getMessage(), "DriverId must not be null or negative!\n");
    }

    @Test
    public void testModelNameNotEmpty() {

        Throwable exc = assertThrowsExactly(ValidatorException.class, () -> busValidator.validate(bus7));
        assertEquals(exc.getMessage(), "Bus must have a non-null and non-empty ModelName!\n");
    }

    @Test
    public void testModelNameNotNull() {

        Throwable exc = assertThrowsExactly(ValidatorException.class, () -> busValidator.validate(bus8));
        assertEquals(exc.getMessage(), "Bus must have a non-null and non-empty ModelName!\n");
    }
}