package busManagement.domain.validators;
import busManagement.domain.City;
import busManagement.domain.exceptions.ValidatorException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;
public class CityValidatorTest {

    public static final String name = "";
    public static final int population = -20;
    public static final Long id = null;
    private City city;

    @Before
    public void setUp() throws Exception{
        city = new City(name, population);
        city.setId(id);
    }

    @Test
    public void testValidate() throws Exception{
        CityValidator validator = new CityValidator();
        Throwable ex = assertThrowsExactly(ValidatorException.class, () -> validator.validate(city));
        assertEquals(ex.getMessage(), "Id must not be null and greater than 0! " + "City must have a name! " + "Population must be greater than 0! ");
    }

}
