package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.BusStation;
import ro.ubb.catalog.core.model.BusStop;
import ro.ubb.catalog.core.model.City;
import ro.ubb.catalog.core.repository.busStation.BusStationRepository;
import ro.ubb.catalog.core.repository.city.CityRepository;
import ro.ubb.catalog.core.converter.BusStationConverter;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.lang.Math.max;

@Service
public class BusStationServiceImpl implements IBusStationService{

    private final CityRepository cityRepo;
    private final BusStationRepository busStationRepo;

    public static final Logger logger = LoggerFactory.getLogger(BusStationServiceImpl.class);

    public BusStationServiceImpl(CityRepository cityRepo, BusStationRepository busStationRepo) {
        this.cityRepo = cityRepo;
        this.busStationRepo = busStationRepo;
    }

    @Override
    public BusStation findBusStationByName(String name) {
        logger.trace("findBusStationByName - method entered - name: " + name);
        List<BusStation> stations = this.busStationRepo.findAllWithCities();
        for(BusStation s: stations){
            if(s.getName().equals(name)){
                logger.trace("findBusStationByName - method entered - station: " + s);
                return s;
            }
        }
        return null;
    }

    @Override
    public Set<BusStop> findAllStopsForStation(Long id) {
        logger.trace("findAllStopsForStation - method entered");
        List<BusStation> stations = this.busStationRepo.findAllWithCityAndStops();
        for(BusStation s: stations){
            if(s.getId().equals(id)){
                logger.trace("findAllStopsForStation - method finished: " + s.getStops());
                return s.getStops();
            }
        }

        return null;
    }

    @Override
    @Transactional
    public void save(String name, Long cityId) {
        logger.trace("add busStation - method entered - cityId: " + cityId + ", name: " + name);

        Optional<City> city = cityRepo.findById(cityId);

        city.ifPresent(
                (City c) -> {
                    BusStation busStation = new BusStation(c, name);
                    c.addStation(busStation, c);
                    this.busStationRepo.save(busStation);
                }
        );

        logger.trace("add busStation - method finished");
    }

    @Override
    @Transactional
    public void update(Long id, String name) {
        logger.trace("update busStation - method entered - id: " + id + ", name: " + name);

        busStationRepo.findById(id)
                .ifPresent((s) -> {
                    s.setName(name);
                    City c = s.getCity();
                    c.updateStation(s, c);
                }
        );

        logger.trace("update busStation - method finished");
    }

    @Override
    @Transactional
    public void delete(Long id) {
        logger.trace("delete busStation - method entered - id: " + id);

        busStationRepo.findById(id)
                .ifPresent((b) -> {
                    busStationRepo.deleteById(b.getId());
                    City c = b.getCity();
                    c.deleteStation(b);
                    this.cityRepo.save(c);
                    }
                );

        logger.trace("delete busStation - method finished");
    }

    @Override
    public List<BusStation> findAll() {
        logger.trace("findAll stations - method entered");

        List<BusStation> stations = busStationRepo.findAllWithCities();

        logger.trace("findAll stations: " + stations);

        return stations;
    }


}
