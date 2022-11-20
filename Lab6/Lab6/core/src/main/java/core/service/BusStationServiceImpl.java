package core.service;

import core.domain.Bus;
import core.domain.BusStation;
import core.domain.City;
import core.exceptions.BusManagementException;
import core.repository.IBusStationRepository;
import core.repository.ICityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.lang.Math.max;

@Service
public class BusStationServiceImpl implements IBusStationService{

    private final ICityRepository cityRepo;
    private final IBusStationRepository busStationRepo;

    public static final Logger logger = LoggerFactory.getLogger(BusStationServiceImpl.class);

    public BusStationServiceImpl(ICityRepository cityRepo, IBusStationRepository busStationRepo) {
        this.cityRepo = cityRepo;
        this.busStationRepo = busStationRepo;
    }

    @Override
    public void save(String name, Long cityId) {
        logger.trace("add busStation - method entered - cityId: " + cityId + ", name: " + name);

        Optional<City> city = cityRepo.findById(cityId);

        city.ifPresentOrElse(
                (City c) -> {
                    BusStation busStation = new BusStation(c, name);
//                    c.addStation(busStation, c);
                    this.busStationRepo.save(busStation);
                },
                () -> {
                    throw new BusManagementException("City does not exist");
                }
        );

        logger.trace("add busStation - method finished");
    }

    @Override
    @Transactional
    public void update(Long id, String name) {
        logger.trace("update busStation - method entered - id: " + id + ", name: " + name);

        busStationRepo.findById(id)
                .ifPresentOrElse((s) -> {
                    s.setName(name);
                },
                () -> {
                    throw new BusManagementException("BusStation not found!");
                }
        );

        logger.trace("update busStation - method finished");
    }

    @Override
    public void delete(Long id) {
        logger.trace("delete busStation - method entered - id: " + id);


//        StreamSupport.stream(busStopRepo.findAll().spliterator(), false)
//                .filter(busStop -> busStop.getId().equals(id))
//                .findAny()
//                .ifPresent((busStop) -> {
//                    throw new BusManagementException("There are bus stops in the bus station!");
//                });

        busStationRepo.findById(id)
                .ifPresentOrElse((b) -> {
//                    City c = b.getCity();
//                    c.deleteStation(b);
                    busStationRepo.deleteById(b.getId());
                    },
                        () -> {
                            throw new BusManagementException("BusStation does not exist");
                        }
                );

        logger.trace("delete busStation - method finished");
    }

    @Override
    public List<BusStation> findAll() {
        logger.trace("findAll stations - method entered");

        List<BusStation> stations = busStationRepo.findAll();

        logger.trace("findAll stations: " + stations);

        return stations;
    }
}
