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
import ro.ubb.catalog.core.model.Driver;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF.dbtest/db-data.xml")
public class DriverServiceTest {

    @Autowired
    IDriverService driverService;

    @Test
    @DatabaseSetup("/META-INF.dbtest/db-data.xml")
    public void save(){
        List<Driver> drivers = driverService.findAll();
        assertEquals(4, drivers.size());

        Driver newDriver = new Driver("Andrei", "126217");
        this.driverService.save(newDriver);

        drivers = driverService.findAll();
        assertEquals(5, drivers.size());

    }
    @Test
    @DatabaseSetup("/META-INF.dbtest/db-data.xml")
    public void delete(){
        List<Driver> drivers = driverService.findAll();
        assertEquals(4, drivers.size());

        this.driverService.delete(11L);

        drivers = driverService.findAll();
        assertEquals(3, drivers.size());

    }

    @Test
    @DatabaseSetup("/META-INF.dbtest/db-data.xml")
    public void update(){
        List<Driver> drivers = driverService.findAll();
        assertEquals(4, drivers.size());

        Driver d = this.driverService.findDriverByCnp("123");
        assertEquals("Ana", d.getName());

        this.driverService.update(11L, "Cosmina");

        d = this.driverService.findDriverByCnp("123");
        assertEquals("Cosmina", d.getName());

        drivers = driverService.findAll();
        assertEquals(4, drivers.size());
    }

    @Test
    @DatabaseSetup("/META-INF.dbtest/db-data.xml")
    public void findAll(){
        List<Driver> drivers = driverService.findAll();
        assertEquals(4, drivers.size());
    }

    @Test
    @DatabaseSetup("/META-INF.dbtest/db-data.xml")
    public void findDriverByCnp(){
        Driver d = new Driver("Maria", "999");
        this.driverService.save(d);

        Driver d2 = driverService.findDriverByCnp("999");
        assertEquals(d.getName(), d2.getName());
    }
}
