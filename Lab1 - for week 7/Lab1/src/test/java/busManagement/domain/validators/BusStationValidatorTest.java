package busManagement.domain.validators;

import busManagement.domain.BusStation;
import busManagement.domain.City;
import busManagement.domain.exceptions.ValidatorException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;
public class BusStationValidatorTest {

    public static final String name = "";
    public static final Long cityId = -20L;
    public static final Long id = null;

    private BusStation busStation;

    @Before
    public void setUp() throws Exception{
        busStation = new BusStation(cityId, name);
        busStation.setId(id);
    }

    @Test
    public void testValidate() throws Exception{
        BusStationValidator validator = new BusStationValidator();
        Throwable ex = assertThrowsExactly(ValidatorException.class, ()->validator.validate(busStation));
        assertEquals(ex.getMessage(), "Id must not be null and greater than 0! " + "CityId must not be null and greater than 0! "
        + "BusStation must have a name! ");
    }
}
