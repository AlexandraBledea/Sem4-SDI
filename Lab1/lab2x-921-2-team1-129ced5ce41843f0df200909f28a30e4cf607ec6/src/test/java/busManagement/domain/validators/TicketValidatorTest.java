package busManagement.domain.validators;

import busManagement.domain.Ticket;
import busManagement.domain.exceptions.ValidatorException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TicketValidatorTest {

    private static Ticket ticket1;
    private static Ticket ticket2;
    private static Ticket ticket3;
    private static Ticket ticket4;
    private static Ticket ticket5;
    private static Ticket ticket6;
    private static Ticket ticket7;
    private static Ticket ticket8;

    private static TicketValidator ticketValidator;

    @Before
    public void setUp() {

        ticketValidator = new TicketValidator();

        ticket1 = new Ticket(1L, 1L, 4L, 200L);
        ticket1.setId(-1L);

        ticket2 = new Ticket(1L, 1L, 4L, 200L);
        ticket2.setId(null);

        ticket3 = new Ticket(-1L, 1L, 4L, 200L);
        ticket3.setId(0L);

        ticket4 = new Ticket(null, 1L, 4L, 200L);
        ticket4.setId(1L);

        ticket5 = new Ticket(1L, -1L, 26L, 200L);
        ticket5.setId(2L);

        ticket6 = new Ticket(1L, null, null, 200L);
        ticket6.setId(3L);

        ticket7 = new Ticket(1L, 1L, 4L, -120L);
        ticket7.setId(4L);

        ticket8 = new Ticket(1L, 1L, 4L, null);
        ticket8.setId(5L);
    }

    @Test
    public void testIdNotNegative() {
        Throwable exc = assertThrowsExactly(ValidatorException.class, () -> ticketValidator.validate(ticket1));
        assertEquals(exc.getMessage(), "Id must not be null or negative!\n");
    }

    @Test
    public void testIdNotNull() {
        Throwable exc = assertThrowsExactly(ValidatorException.class, () -> ticketValidator.validate(ticket2));
        assertEquals(exc.getMessage(), "Id must not be null or negative!\n");
    }

    @Test
    public void testPassagerIdNotNegative() {
        Throwable exc = assertThrowsExactly(ValidatorException.class, () -> ticketValidator.validate(ticket3));
        assertEquals(exc.getMessage(), "PassagerId must not be null or negative!\n");
    }

    @Test
    public void testPassagerIdNotNull() {
        Throwable exc = assertThrowsExactly(ValidatorException.class, () -> ticketValidator.validate(ticket4));
        assertEquals(exc.getMessage(), "PassagerId must not be null or negative!\n");
    }

    @Test
    public void testBusIdNotNegative() {
        Throwable exc = assertThrowsExactly(ValidatorException.class, () -> ticketValidator.validate(ticket5));
        assertEquals(exc.getMessage(), "BusId must not be null or negative!\n");
    }

    @Test
    public void testBusIdNotNull() {
        Throwable exc = assertThrowsExactly(ValidatorException.class, () -> ticketValidator.validate(ticket6));
        assertEquals(exc.getMessage(), "BusId must not be null or negative!\n");
    }

    @Test
    public void testBoardingTimeValid() {
        Throwable exc = assertThrowsExactly(ValidatorException.class, () -> ticketValidator.validate(ticket5));
        assertEquals(exc.getMessage(), "BoardingTime must be not null or in range (0, 23)!\n");
    }

    @Test
    public void testBoardingTimeNotNull() {
        Throwable exc = assertThrowsExactly(ValidatorException.class, () -> ticketValidator.validate(ticket6));
        assertEquals(exc.getMessage(), "BoardingTime must be not null or in range (0, 23)!\n");
    }

    @Test
    public void testPriceNotNegative() {
        Throwable exc = assertThrowsExactly(ValidatorException.class, () -> ticketValidator.validate(ticket7));
        assertEquals(exc.getMessage(), "Price must not be null or negative!\n");
    }

    @Test
    public void testPriceNotNull() {
        Throwable exc = assertThrowsExactly(ValidatorException.class, () -> ticketValidator.validate(ticket8));
        assertEquals(exc.getMessage(), "Price must not be null or negative!\n");
    }
}