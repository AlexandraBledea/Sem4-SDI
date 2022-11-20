package ro.ubb.catalog.core.repository;
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
import ro.ubb.catalog.core.repository.bus.BusRepository;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF.dbtest/db-data.xml")
public class BusRepositoryTest {

    @Autowired
    BusRepository busRepository;

    @Test
    public void findAllWithDriverQuery(){
        List<Bus> buses = this.busRepository.findAllWithDriverQuery();
        assertEquals(3, buses.size());
    }

    @Test
    public void findAllWithDriverAndStopsQuery(){
        List<Bus> buses = this.busRepository.findAllWithDriverAndStopsQuery();
        assertEquals(3, buses.size());
    }

    @Test
    public void findAllWithDriver(){
        List<Bus> buses = this.busRepository.findAllWithDriver();
        assertEquals(3, buses.size());
    }

    @Test
    public void findAllWithDriverAndStops(){
        List<Bus> buses = this.busRepository.findAllWithDriverAndStops();
        assertEquals(3, buses.size());
    }
}
