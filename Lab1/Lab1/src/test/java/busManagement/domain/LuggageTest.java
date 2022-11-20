package busManagement.domain;

import busManagement.domain.Luggage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LuggageTest {
    public static final Long id = 21L;
    public static final Long passengerId1 = 1L;
    public static final Integer weight1 = 10;
    public static final Long passengerId2 = 2L;
    public static final Integer weight2 = 20;

    private Luggage luggage1;
    private Luggage luggage2;


    @Before
    public void setUp() throws Exception{
        luggage1 = new Luggage(passengerId1, weight1);
        luggage1.setId(id);

        luggage2 = new Luggage(passengerId2,weight2);
        luggage2.setId(id);
    }

    @Test
    public void getPassengerId1() throws Exception{
        assertEquals(passengerId1,luggage1.getPassengerId(),"Passenger ID for Luggage 1 should be equal!");
    }

    @Test
    public void getPassengerId2() throws Exception{
        assertEquals(passengerId2,luggage2.getPassengerId(),"Passenger ID for Luggage 2 should be equal!");
    }

    @Test
    public void setPassengerId1() throws Exception{
        luggage1.setPassengerId(3L);
        assertEquals(3L,luggage1.getPassengerId(),"Passenger ID for Luggage 1 should be different!");
    }

    @Test
    public void setPassengerId2() throws Exception{
        luggage2.setPassengerId(3L);
        assertEquals(3L,luggage2.getPassengerId(),"Passenger ID for Luggage 2 should be different!");
    }

    @Test
    public void getWeight1() throws Exception{
        assertEquals(weight1,luggage1.getWeight(),"Weight for Luggage 1 should be equal!");
    }

    @Test
    public void getWeight2() throws Exception{
        assertEquals(weight2,luggage2.getWeight(),"Weight for Luggage 2 should be equal!");
    }

    @Test
    public void setWeight1() throws Exception{
        luggage1.setWeight(30);
        assertEquals(30,luggage1.getWeight(),"Weight for Luggage 1 should be different!");
    }

    @Test
    public void setWeight2() throws Exception{
        luggage2.setWeight(30);
        assertEquals(30,luggage2.getWeight(),"Weight for Luggage 2 should be different!");
    }

    @Test
    public void equalsFunctionDifferent() throws Exception{
        assertEquals(false,luggage1.equals(luggage2),"The two Luggages should not be the same!");
    }

    @Test
    public void equalsFunctionSame() throws Exception{
        assertEquals(true,luggage1.equals(luggage1),"The two Luggages should be the same!");
    }


}
