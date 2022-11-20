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
import ro.ubb.catalog.core.model.Bus;
import ro.ubb.catalog.core.model.BusStop;
import ro.ubb.catalog.core.model.Driver;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF.dbtest/db-data.xml")
public class BusServiceTest {
    @Autowired
    IBusService busService;

    @Test
    @DatabaseSetup("/META-INF.dbtest/db-data.xml")
    public void save(){
        List<Bus> buses = busService.findAll();
        assertEquals(3, buses.size());

        this.busService.save("Mercedes", "motorina", 34, 14L);

        buses = busService.findAll();
        assertEquals(4, buses.size());

    }

    @Test
    @DatabaseSetup("/META-INF.dbtest/db-data.xml")
    public void update(){
        List<Bus> buses = busService.findAll();
        assertEquals(3, buses.size());

        Bus b = this.busService.findBusByModelName("Audi");
        this.busService.update(b.getId(), b.getModelName(), "motorina", 12);

        b = this.busService.findBusByModelName("Audi");
        assertEquals("motorina", b.getFuel());
        assertEquals(12, b.getCapacity());

        buses = busService.findAll();
        assertEquals(3, buses.size());
    }

    @Test
    @DatabaseSetup("/META-INF.dbtest/db-data.xml")
    public void delete(){
        List<Bus> buses = busService.findAll();
        assertEquals(3, buses.size());

        this.busService.save("Mercedes", "motorina", 34, 14L);

        buses = busService.findAll();
        assertEquals(4, buses.size());

        this.busService.delete(14L);

        buses = busService.findAll();
        assertEquals(3, buses.size());
    }

    @Test
    @DatabaseSetup("/META-INF.dbtest/db-data.xml")
    public void findAll(){
        List<Bus> buses = busService.findAll();
        assertEquals(3, buses.size());

    }

    @Test
    @DatabaseSetup("/META-INF.dbtest/db-data.xml")
    public void findBusByModelName(){
        this.busService.save("Mercedes", "motorina", 34, 14L);
        Bus b = this.busService.findBusByModelName("Mercedes");

        assertEquals("motorina", b.getFuel());
        assertEquals(34, b.getCapacity());
    }

    @Test
    @DatabaseSetup("/META-INF.dbtest/db-data.xml")
    public void findAllStopsForBus(){
        Set<BusStop> stops = busService.findAllStopsForBus(11L);
        assertEquals(1, stops.size());
    }
}
