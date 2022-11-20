package core.service;

import core.converter.BusConverter;
import core.domain.Bus;
import core.domain.BusStop;
import core.domain.Driver;
import core.dto.BusDTO;
import core.dto.PagingRequest;
import core.dto.PagingResponse;
import core.exceptions.BusManagementException;
import core.repository.IBusRepository;
import core.repository.IDriverRepository;
import core.utils.RestPagingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static java.lang.Math.max;

@Service
public class BusServiceImpl implements IBusService{
    private final IBusRepository busRepo;
    private final IDriverRepository driverRepository;
    private final BusConverter busConverter;

    public static final Logger logger = LoggerFactory.getLogger(BusServiceImpl.class);

    public BusServiceImpl(IBusRepository busRepo, IDriverRepository driverRepository, BusConverter busConverter) {
        this.busRepo = busRepo;
        this.driverRepository = driverRepository;
        this.busConverter = busConverter;
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
    public Bus findBusByModelName(String modelName) {
        return this.busRepo.findBusByModelName(modelName);
    }

    @Override
    public Set<BusStop> findAllStopsForBus(Long id) {
        Optional<Bus> b = this.busRepo.findById(id);
        return b.get().getStops();
    }

    @Override
    public void save(String modelName, String fuel, int capacity, Long driverId) {
        logger.trace("add bus - method entered - modelName: " +modelName + ", fuel: " + fuel + ", capacity: " + capacity + ", driverId: " + driverId);

        Optional<Driver> driver = driverRepository.findById(driverId);

        List<Bus> buses = this.busRepo.findAll();
        for(Bus b: buses){
            if(Objects.equals(b.getDriver().getId(), driverId)){
                throw new BusManagementException("Driver already belongs to a bus!");
            }
        }

        driver.ifPresentOrElse(
                (Driver d) -> {
                    Bus b = new Bus(modelName, fuel, capacity, d);
                    this.busRepo.save(b);
                },
                () -> {
                    throw new BusManagementException("Driver not found!");
                }
        );

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

    @Override
    public PagingResponse<BusDTO> findAll(PagingRequest pagingRequest) {
        if(RestPagingUtil.isRequestPaged(pagingRequest)){
            Page<Bus> page;
            page = this.busRepo.findAll(RestPagingUtil.buildPageRequest(pagingRequest));

            List<Bus> busList = page.getContent();
            List<BusDTO> busDTOS = this.busConverter.convertModelsToDTOsList(busList);
            return new PagingResponse<>(
                    page.getTotalElements(),
                    (long) page.getNumber(),
                    (long) page.getNumberOfElements(),
                    RestPagingUtil.buildPageRequest(pagingRequest).getOffset(),
                    (long) page.getTotalPages(),
                    busDTOS);
        } else {
            List<Bus> buses = this.busRepo.findAll();
            List<BusDTO> busDTOList = this.busConverter.convertModelsToDTOsList(buses);
            return new PagingResponse<>(
                    (long) busDTOList.size(),
                    0L,
                    0L,
                    0L,
                    0L,
                    busDTOList);
        }
    }
}
