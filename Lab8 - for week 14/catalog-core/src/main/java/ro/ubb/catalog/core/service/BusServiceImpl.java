package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.exceptions.BusManagementException;
import ro.ubb.catalog.core.model.Bus;
import ro.ubb.catalog.core.model.BusStop;
import ro.ubb.catalog.core.model.Driver;
import ro.ubb.catalog.core.repository.driver.DriverRepository;
import ro.ubb.catalog.core.repository.bus.BusRepository;
import ro.ubb.catalog.core.converter.BusConverter;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static java.lang.Math.max;

@Service
public class BusServiceImpl implements IBusService {
    private final BusRepository busRepo;
    private final DriverRepository driverRepository;
    public static final Logger logger = LoggerFactory.getLogger(BusServiceImpl.class);

    public BusServiceImpl(BusRepository busRepo, DriverRepository driverRepository) {
        this.busRepo = busRepo;
        this.driverRepository = driverRepository;
    }

    @Override
    public Bus findBusByModelName(String modelName) {
        logger.trace("findBusByModelName - method entered - modelName: " + modelName);
        List<Bus> buses = this.busRepo.findAllWithDriver();
        for(Bus b: buses){
            if(b.getModelName().equals(modelName)){
                logger.trace("findBusByModelName - method finished - bus: " + b);
                return b;
            }
        }
        return null;
    }

    @Override
    public Set<BusStop> findAllStopsForBus(Long id) {
        logger.trace("findAllStopsForBus - method entered");
        List<Bus> buses = this.busRepo.findAllWithDriverAndStops();
        for(Bus b: buses){
            if(b.getId().equals(id)){
                logger.trace("findAllStopsForBus - method finished: " + b.getStops());
                return b.getStops();
            }
        }

        return null;
    }

    @Override
    @Transactional
    public void save(String modelName, String fuel, int capacity, Long driverId) {
        logger.trace("add bus - method entered - modelName: " +modelName + ", fuel: " + fuel + ", capacity: " + capacity + ", driverId: " + driverId);

        Optional<Driver> driver = driverRepository.findById(driverId);

        List<Bus> buses = this.busRepo.findAllWithDriver();
        for(Bus b: buses){
            if(Objects.equals(b.getDriver().getId(), driverId)){
                throw new BusManagementException("Driver already belongs to a bus!");
            }
        }

        driver.ifPresent(
                (Driver d) -> {
                    Bus b = new Bus(modelName, fuel, capacity, d);
                    this.busRepo.save(b);
                    b.setDriver(d);
                    this.driverRepository.save(d);
                }
        );

        logger.trace("add bus - method finished");
    }

    @Override
    @Transactional
    public void update(Long busId, String modelName, String fuel, int capacity) {
        logger.trace("update bus - method entered - id: " + busId + ", modelName: " + modelName + ", fuel: " + fuel + ", capacity: " + capacity);

        busRepo.findById(busId)
                .ifPresent((b) ->{
                            b.setModelName(modelName);
                            b.setFuel(fuel);
                            b.setCapacity(capacity);
                        }
                );

        logger.trace("update bus - method finished");
    }

    @Override
    @Transactional
    public void delete(Long id) {
        logger.trace("delete bus - method entered - id: " + id);
        Optional<Bus> b = this.busRepo.findById(id);
        busRepo.deleteById(id);
        logger.trace("delete bus - method finished");
    }

    @Override
    public List<Bus> findAll() {
        logger.trace("findAll buses - method entered - something else here");

        List<Bus> buses = busRepo.findAllWithDriver();
        for(Bus b: buses){
            System.out.println(b.getDriver());
        }

        logger.trace("findAll buses: - something else here " + buses);

        return buses;
    }
}
