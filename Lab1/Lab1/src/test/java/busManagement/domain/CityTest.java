package busManagement.domain;

import busManagement.domain.City;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CityTest
{
    public static final Long id = 1L;
    public static final Long newID = 2L;
    public static final String name = "a1";
    public static final String newName = "a2";
    public static final int population = 10;
    public static final int newPopulation = 20;

    private City city;
    private City city2;

    @Before
    public void setUp() throws Exception{
        city = new City(name, population);
        city.setId(id);

        city2 = new City(name, population);
        city.setId(id);
    }

    @Test
    public void testGetName() throws Exception{
        assertEquals(name, city.getName(), "Names should be equal");
    }

    @Test
    public void testGetPopulation() throws Exception{
        assertEquals(population, city.getPopulation(), "Populations should be equal");
    }

    @Test
    public void testSetName() throws Exception{
        city.setName(newName);
        assertEquals(newName, city.getName(), "Names should be equal");
    }

    @Test
    public void testSetPopulation() throws Exception{
        city.setPopulation(newPopulation);
        assertEquals(newPopulation, city.getPopulation(), "Populations should be equal");
    }

    @Test
    public void testGetId() throws Exception{
        assertEquals(id, city.getId(), "Ids should be equal");
    }

    @Test
    public void testSetId() throws Exception{
        city.setId(newID);
        assertEquals(newID, city.getId(), "Ids should be equal");
    }

    @Test
    public void testEquals() throws Exception{
        assertEquals(true, city.equals(city2), "Objects should be the same");
    }

}
