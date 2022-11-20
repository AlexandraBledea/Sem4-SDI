package core.service;

import core.domain.Bus;
import core.dto.BusDTO;
import core.dto.BusFilterDTO;
import core.exceptions.BusManagementException;
import core.repository.IBusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.Math.max;

@Service
public class BusServiceImpl implements IBusService{
    private final IBusRepository busRepo;

    public static final Logger logger = LoggerFactory.getLogger(BusServiceImpl.class);

    public BusServiceImpl(IBusRepository busRepo) {
        this.busRepo = busRepo;
    }

    @Override
    public List<Bus> filterBusesByModelName(String modelName) {
        return this.busRepo.filterBusesByModelName(modelName);
    }

    @Override
    public List<Bus> sortBusesByCapacityInAscendingOrder() {
        return this.busRepo.findAllByOrderByCapacityAsc();
//        return this.busRepo.sortBusesByCapacityInAscendingOrder();
    }

    @Override
    public void save(Bus bus) {
        logger.trace("add bus - method entered - modelName: " +bus.getModelName() + ", fuel: " + bus.getFuel() + ", capacity: " + bus.getCapacity());

        busRepo.save(bus);

        logger.trace("add bus - method finished");
    }

    @Override
    @Transactional
    public void update(Long busId, String modelName, String fuel, int capacity) {
        logger.trace("update bus - method entered - id: " + busId + ", modelName: " + modelName + ", fuel: " + fuel + ", capacity: " + capacity);

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
