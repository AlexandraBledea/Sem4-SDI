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
public class BusStationServiceTest {

    @Autowired
    IBusStationService busStationService;

    @Autowired
    ICityService cityService;

    @Test
    @DatabaseSetup("/META-INF.dbtest/db-data.xml")
    public void save(){
        List<BusStation> stations = this.busStationService.findAll();
        assertEquals(3, stations.size());

        this.busStationService.save("Clujana", 11L);

        stations = this.busStationService.findAll();
        assertEquals(4, stations.size());

    }

    @Test
    @DatabaseSetup("/META-INF.dbtest/db-data.xml")
    public void update(){
        City c = new City("Mures", 12341);

        this.cityService.save(c);

        BusStation b = new BusStation(c, "Clujana");

        this.busStationService.save(b.getName(), c.getId());

        b = this.busStationService.findBusStationByName("Clujana");

        this.busStationService.update(b.getId(), "Ludus");

        b = this.busStationService.findBusStationByName("Ludus");

        assertEquals("Ludus", b.getName());


    }

    @Test
    @DatabaseSetup("/META-INF.dbtest/db-data.xml")
    public void delete(){

        List<BusStation> stations = this.busStationService.findAll();
        assertEquals(3, stations.size());

        this.busStationService.save("Clujana", 11L);

        stations = this.busStationService.findAll();
        assertEquals(4, stations.size());

        this.busStationService.delete(14L);

        stations = this.busStationService.findAll();
        assertEquals(3, stations.size());
    }

    @Test
    @DatabaseSetup("/META-INF.dbtest/db-data.xml")
    public void findAll(){
        List<BusStation> stations = this.busStationService.findAll();
        assertEquals(3, stations.size());
    }

    @Test
    @DatabaseSetup("/META-INF.dbtest/db-data.xml")
    public void findBusStationByName(){
        City c = new City("Mures", 12341);

        this.cityService.save(c);

        BusStation b = new BusStation(c, "Clujana");

        this.busStationService.save(b.getName(), c.getId());

        BusStation b2 = this.busStationService.findBusStationByName("Clujana");

        assertEquals(b.getName(), b2.getName());
    }

    @Test
    @DatabaseSetup("/META-INF.dbtest/db-data.xml")
    public void findAllStopsForStation(){
        Set<BusStop> stops = this.busStationService.findAllStopsForStation(11L);
        assertEquals(1, stops.size());
    }
}
