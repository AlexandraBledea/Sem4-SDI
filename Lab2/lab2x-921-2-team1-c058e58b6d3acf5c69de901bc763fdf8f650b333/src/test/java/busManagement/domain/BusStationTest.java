package busManagement.domain;
import busManagement.domain.BusStation;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BusStationTest {

    public static final Long id = 1L;
    public static final Long newID = 2L;
    public static final String name = "a1";
    public static final String newName = "a2";
    public static final Long cityId = 10L;
    public static final Long newCityId = 20L;

    private BusStation busStation1;
    private BusStation busStation2;

    @Before
    public void setUp() throws Exception{
        busStation1 = new BusStation(cityId, name);
        busStation1.setId(id);

        busStation2 = new BusStation(cityId, name);
        busStation2.setId(id);
    }

    @Test
    public void testGetCityId() throws Exception{
        assertEquals(cityId, busStation1.getCityId(), "Ids should be equal");
    }

    @Test
    public void testSetCityId() throws Exception{
        busStation1.setCityId(newCityId);
        assertEquals(newCityId, busStation1.getCityId(), "Ids should be equal");
    }

    @Test
    public void testGetName() throws Exception{
        assertEquals(name, busStation1.getName(), "Names should be equal");
    }

    @Test
    public void testSetName() throws Exception{
        busStation1.setName(newName);
        assertEquals(newName, busStation1.getName(), "Names should be equal");
    }

    @Test
    public void testGetId() throws Exception{
        assertEquals(id, busStation1.getId(), "Ids should be equal");
    }

    @Test
    public void testSetId() throws Exception{
        busStation1.setId(newID);
        assertEquals(newID, busStation1.getId(), "Ids should be equal");
    }

    @Test
    public void testEquals() throws Exception{
        assertEquals(true, busStation1.equals(busStation2), "Objects should be the same");
    }

}
