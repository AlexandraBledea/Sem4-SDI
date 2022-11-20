package busManagement.domain.validators;

import busManagement.domain.BusStop;
import busManagement.domain.exceptions.ValidatorException;
import busManagement.utils.Pair;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class BusStopValidatorTest {
    public static final LocalTime stopTime = LocalTime.MIN;
    public static final Pair<Long, Long> id = null;
    private BusStop busStop;


    @Before
    public void setUp() throws Exception{
        busStop = new BusStop(stopTime);
        busStop.setId(id);
    }

    @Test
    public void testValidate() throws Exception{
        BusStopValidator validator = new BusStopValidator();
        Throwable ex = assertThrowsExactly(ValidatorException.class, () -> validator.validate(busStop));
        assertEquals(ex.getMessage(), "Components of Id must not be null or negative!\n" +
                "Bus Stop must have a non-null and greater than zero Stop Time!\n");
    }
}
