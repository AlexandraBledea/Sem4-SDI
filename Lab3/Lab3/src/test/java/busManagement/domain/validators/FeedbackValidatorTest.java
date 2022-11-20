package busManagement.domain.validators;


import busManagement.domain.Feedback;
import busManagement.domain.exceptions.ValidatorException;
import busManagement.utils.Pair;
import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FeedbackValidatorTest {

    public static final Pair<Long,Long> id = new Pair<>(null,null);
    public static final String review = "";

    private Feedback feedback;

    @Before
    public void setUp() throws Exception{
        feedback = new Feedback(review);
        feedback.setId(id);
    }

    @Test
    public void testValidate() throws Exception{
        FeedbackValidator validator = new FeedbackValidator();
        Throwable ex = assertThrowsExactly(ValidatorException.class, () -> validator.validate(feedback));
        assertEquals(ex.getMessage(), "Ids must not be null and greater than 0! " + "The review cannot be empty");

    }
}
