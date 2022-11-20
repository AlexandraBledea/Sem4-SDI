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
import ro.ubb.catalog.core.model.BusStation;
import ro.ubb.catalog.core.repository.busStation.BusStationRepository;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF.dbtest/db-data.xml")
public class BusStationRepositoryTest {

    @Autowired
    BusStationRepository busStationRepository;

    @Test
    public void findAllWithCityAndStopsQuery(){
        List<BusStation> stations = this.busStationRepository.findAllWithCityAndStopsQuery();
        assertEquals(3, stations.size());
    }

    @Test
    public void findAllWithCitiesQuery(){
        List<BusStation> stations = this.busStationRepository.findAllWithCitiesQuery();
        assertEquals(3, stations.size());
    }

    @Test
    public void findAllWithCityAndStops(){
        List<BusStation> stations = this.busStationRepository.findAllWithCityAndStops();
        assertEquals(3, stations.size());
    }

    @Test
    public void findAllWithCities(){
        List<BusStation> stations = this.busStationRepository.findAllWithCities();
        assertEquals(3, stations.size());
    }

}
