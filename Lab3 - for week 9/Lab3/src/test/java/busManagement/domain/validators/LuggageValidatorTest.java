package busManagement.domain.validators;

import busManagement.domain.Luggage;
import busManagement.domain.exceptions.ValidatorException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class LuggageValidatorTest {

    public static final Long passengerId = null;
    public static final Integer weight = 0;
    public static final Long id = null;
    private Luggage luggage;

    @Before
    public void setUp() throws Exception{
        luggage = new Luggage(passengerId, weight);
        luggage.setId(id);
    }

    @Test
    public void testValidate() throws Exception{
        LuggageValidator validator = new LuggageValidator();
        Throwable ex = assertThrowsExactly(ValidatorException.class, () -> validator.validate(luggage));
        assertEquals(ex.getMessage(), "Id mut not be null and greater than 0!" + "PassengerId must not be null and greater than 0!" + "Weight must not be null and greather than 0!");
    }
}
