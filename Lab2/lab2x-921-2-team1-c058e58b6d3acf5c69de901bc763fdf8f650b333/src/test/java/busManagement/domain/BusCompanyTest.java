package busManagement.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BusCompanyTest {

    public static final Long id = 1L;
    public static final Long newId = 2L;
    public static final String name = "BusCompany1";
    public static final String newName = "BusCompany2";

    private BusCompany busCompany1;
    private BusCompany busCompany2;

    @Before
    public void setUp() {
        busCompany1 = new BusCompany(name);
        busCompany1.setId(id);

        busCompany2 = new BusCompany(name);
        busCompany2.setId(id);
    }

    @Test
    public void testGetName() {
        assertEquals(name, busCompany1.getName(), "Names should be equal");
    }

    @Test
    public void testSetName() {
        busCompany1.setName(newName);
        assertEquals(newName, busCompany1.getName(), "Names should be equal");
    }

    @Test
    public void testGetId() {
        assertEquals(id, busCompany1.getId(), "Ids should be equal");
    }

    @Test
    public void testSetId() {
        busCompany1.setId(newId);
        assertEquals(newId, busCompany1.getId(), "Ids should be equal");
    }

    @Test
    public void testEquals() {
        assertEquals(busCompany1, busCompany2);
    }

    @Test
    public void testToString() {
        String expectedResult1 = "BusCompany{name='BusCompany1'}";
        System.out.println(busCompany1.toString());
        assertEquals(busCompany1.toString(), expectedResult1);
    }

}
