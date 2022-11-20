package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Driver;
import ro.ubb.catalog.core.converter.DriverConverter;
import ro.ubb.catalog.core.repository.driver.DriverRepository;

import java.util.List;

@Service
public class DriverServiceImpl implements IDriverService {
    private final DriverRepository driverRepository;

    public static final Logger logger = LoggerFactory.getLogger(DriverServiceImpl.class);

    public DriverServiceImpl(DriverRepository driverRepository){
        this.driverRepository = driverRepository;
    }

    @Override
    public void save(Driver driver) {
        logger.trace("add driver - method entered - name: " + driver.getName() + ", cnp: " + driver.getCnp());

        this.driverRepository.save(driver);

        logger.trace("add driver - method finished");
    }

    @Override
    @Transactional
    public void update(Long id, String name) {
        logger.trace("update driver - method entered - id: " + id + ", name: " + name);

        this.driverRepository.findById(id)
                .ifPresent((d) -> {
                    d.setName(name);
                }
        );

        logger.trace("update driver - method finished");
    }

    @Override
    public void delete(Long id) {

        logger.trace("delete driver - method entered - id: " + id);

        driverRepository.findById(id)
                .ifPresent((d) -> driverRepository.deleteById(d.getId()));

        logger.trace("delete driver - method finished");
    }

    @Override
    public List<Driver> findAll() {
        logger.trace("findAll drivers - method entered");

        List<Driver> drivers = driverRepository.findAll();

        logger.trace("findAll drivers - method finished " + drivers);

        return drivers;
    }


    @Override
    public Driver findDriverByCnp(String cnp) {
        return this.driverRepository.findDriverByCnp(cnp);
    }
}
