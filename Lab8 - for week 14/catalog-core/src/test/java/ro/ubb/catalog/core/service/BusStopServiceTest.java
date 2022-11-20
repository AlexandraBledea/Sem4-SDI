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
import ro.ubb.catalog.core.model.BusStop;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF.dbtest/db-data.xml")
public class BusStopServiceTest {

    @Autowired
    IBusStopService busStopService;

    @Test
    @DatabaseSetup("/META-INF.dbtest/db-data.xml")
    public void save(){
        List<BusStop> stops = this.busStopService.findAll();
        assertEquals(3, stops.size());

        this.busStopService.save(11L, 11L, "13:45");

        stops = this.busStopService.findAll();
        assertEquals(4, stops.size());
    }

    @Test
    @DatabaseSetup("/META-INF.dbtest/db-data.xml")
    public void delete(){
        List<BusStop> stops = this.busStopService.findAll();
        assertEquals(3, stops.size());

        this.busStopService.save(11L, 11L, "13:45");

        stops = this.busStopService.findAll();
        assertEquals(4, stops.size());

        this.busStopService.delete(11L, 11L);

        stops = this.busStopService.findAll();
        assertEquals(3, stops.size());
    }

//    @Test
//    @DatabaseSetup("/META-INF.dbtest/db-data.xml")
//    public void findAll(){
//        List<BusStop> stops = this.busStopService.findAll();
//        assertEquals(3, stops.size());
//    }
}
