package ro.ubb.catalog.core.repository;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import ro.ubb.catalog.core.ITConfig;
import ro.ubb.catalog.core.model.City;
import ro.ubb.catalog.core.repository.city.CityRepository;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF.dbtest/db-data.xml")
public class CityRepositoryTest {

    @Autowired
    CityRepository cityRepository;

    @Test
    public void findAllWithStationsQuery(){
        List<City> cities = cityRepository.findAllWithStationsQuery();
        assertEquals(4, cities.size());
    }

    @Test
    public void findAllWithStationsAndStopsQuery(){
        List<City> cities = cityRepository.findAllWithStationsAndStopsQuery();
        assertEquals(4, cities.size());
    }

    @Test
    public void findAllWithStations(){
        List<City> cities = cityRepository.findAllWithStations();
        assertEquals(4, cities.size());
    }

    @Test
    public void findAllWithStationsAndStops(){
        List<City> cities = cityRepository.findAllWithStationsAndStops();
        assertEquals(4, cities.size());
    }

    @Test
    public void findCityByName(){
        String name = "Arad";
        City city = cityRepository.findCityByName(name);
        assertEquals(name, city.getName());

    }

}
