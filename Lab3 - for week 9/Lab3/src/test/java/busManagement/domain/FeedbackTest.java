package busManagement.domain;

import busManagement.utils.Pair;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FeedbackTest {
    public static final Pair<Long,Long> id = new Pair<>(1L,1L);
    public static final Pair<Long,Long> newId = new Pair<>(2L,2L);
    public static final String review = "r1";
    public static final String newReview = "r2";

    private Feedback feedback;
    private Feedback feedback2;

    @Before
    public void setUp() throws Exception{
        feedback = new Feedback(review);
        feedback.setId(id);

        feedback2 = new Feedback(review);
        feedback2.setId(id);
    }

    @Test
    public void testGetName() throws Exception{
        assertEquals(review, feedback.getReview(), "Names should be equal");
    }

    @Test
    public void testSetName() throws Exception{
        feedback.setReview(newReview);
        assertEquals(newReview, feedback.getReview(), "Names should be equal");
    }

    @Test
    public void testGetId() throws Exception{
        assertEquals(id, feedback.getId(), "Ids should be equal");
    }

    @Test
    public void testSetId() throws Exception{
        feedback.setId(newId);
        assertEquals(newId, feedback.getId(), "Ids should be equal");
    }

    @Test
    public void testEquals() throws Exception{
        assertEquals(true, feedback.equals(feedback2), "Objects should be the same");
    }
}
