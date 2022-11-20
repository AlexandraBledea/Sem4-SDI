package core.service;

import core.domain.Bus;
import core.exceptions.BusManagementException;
import core.repository.IBusRepository;
import core.repository.IBusStopRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

import static java.lang.Math.max;

@Service
public class BusServiceImpl implements IBusService{
    private final IBusRepository busRepo;
    private final IBusStopRepository busStopRepo;

    public static final Logger logger = LoggerFactory.getLogger(BusServiceImpl.class);

    public BusServiceImpl(IBusRepository busRepo, IBusStopRepository busStopRepo) {
        this.busRepo = busRepo;
        this.busStopRepo = busStopRepo;
    }


    @Override
    public void save(String modelName, String fuel, int capacity) {
        logger.trace("add bus - method entered - modelName: " +modelName + ", fuel: " + fuel + ", capacity: " + capacity);

        long id = 0;
        for (Bus b : this.busRepo.findAll())
            id = max(id, b.getId() + 1);

        Bus bus = new Bus(id, modelName, fuel, capacity);

        busRepo.save(bus);

        logger.trace("add bus - method finished");
    }

    @Override
    @Transactional
    public void update(Long busId, String modelName, String fuel, int capacity) {
        logger.trace("update bus - method entered - id: " + busId + ", modelName: " + modelName + ", fuel: " + fuel + ", capacity: " + capacity);

//        StreamSupport.stream(busStopRepo.findAll().spliterator(), false)
//                .filter(stop -> stop.getId().equals(busId))
//                .findAny()
//                .ifPresent((obj) -> {throw new BusManagementException("Stop(s) for this bus exist!");});


        busRepo.findById(busId)
                .ifPresentOrElse((b) ->{
                            b.setModelName(modelName);
                            b.setFuel(fuel);
                            b.setCapacity(capacity);
                        },
                        ()  ->{
                            throw new BusManagementException("Bus does not exist");
                        }
                );

        logger.trace("update bus - method finished");
    }

    @Override
    public void delete(Long id) {
        logger.trace("delete bus - method entered - id: " + id);

        busRepo.findById(id)
                .ifPresentOrElse((b) -> busRepo.deleteById(b.getId()),
                        () -> {
                            throw new BusManagementException("Bus does not exist");
                        }
                );

        logger.trace("delete bus - method finished");
    }

    @Override
    public List<Bus> findAll() {
        logger.trace("findAll buses - method entered");

        List<Bus> buses = busRepo.findAll();

        logger.trace("findAll buses: " + buses);

        return buses;
    }
}
