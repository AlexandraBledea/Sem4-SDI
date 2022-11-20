package ro.ubb.catalog.core.service;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import ro.ubb.catalog.core.ITConfig;
import ro.ubb.catalog.core.model.BusStation;
import ro.ubb.catalog.core.model.BusStop;
import ro.ubb.catalog.core.model.City;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF.dbtest/db-data.xml")
public class CityServiceTest {

    @Autowired
    ICityService cityService;

    @Test
    @DatabaseSetup("/META-INF.dbtest/db-data.xml")
    public void save(){

        List<City> cities = this.cityService.findAll();
        assertEquals(4, cities.size());

        City c = new City("Sighet", 124112);
        this.cityService.save(c);

        cities = this.cityService.findAll();
        assertEquals(5, cities.size());
    }

    @Test
    @DatabaseSetup("/META-INF.dbtest/db-data.xml")
    public void update(){
        List<City> cities = this.cityService.findAll();
        assertEquals(4, cities.size());

        City c = this.cityService.findCityByName("Arad");
        assertEquals(2000, c.getPopulation());

        this.cityService.update(12L, "Arad", 4511);

        c = this.cityService.findCityByName("Arad");
        assertEquals(4511, c.getPopulation());

        cities = this.cityService.findAll();
        assertEquals(4, cities.size());
    }

    @Test
    @DatabaseSetup("/META-INF.dbtest/db-data.xml")
    public void delete(){

        List<City> cities = this.cityService.findAll();
        assertEquals(4, cities.size());

        this.cityService.delete(14L);

        cities = this.cityService.findAll();
        assertEquals(3, cities.size());

    }

    @Test
    @DatabaseSetup("/META-INF.dbtest/db-data.xml")
    public void findAll(){
        List<City> cities = this.cityService.findAll();
        assertEquals(4, cities.size());
    }

    @Test
    @DatabaseSetup("/META-INF.dbtest/db-data.xml")
    public void findCityByName(){

        City c = new City("Baia Mare", 216471);
        this.cityService.save(c);

        City c2 = this.cityService.findCityByName("Baia Mare");
        assertEquals(c.getName(), c2.getName());
    }

    @Test
    @DatabaseSetup("/META-INF.dbtest/db-data.xml")
    public void findAllStationsForCity(){
        List<City> cities = this.cityService.findAll();
        assertEquals(4, cities.size());

        Set<BusStation> stations = this.cityService.findAllStationsForCity(11L);
        assertEquals(1, stations.size());

    }

    @Test
    @DatabaseSetup("/META-INF.dbtest/db-data.xml")
    public void findAllStopsForCity(){
        List<City> cities = this.cityService.findAll();
        assertEquals(4, cities.size());

        Set<BusStop> stops = this.cityService.findAllStopsForCity(11L, 11L);
        assertEquals(1, stops.size());
    }
}

