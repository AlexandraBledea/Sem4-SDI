package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.exceptions.BusManagementException;
import ro.ubb.catalog.core.model.Bus;
import ro.ubb.catalog.core.model.BusStation;
import ro.ubb.catalog.core.model.BusStop;
import ro.ubb.catalog.core.model.City;
import ro.ubb.catalog.core.repository.busStation.BusStationRepository;
import ro.ubb.catalog.core.repository.city.CityRepository;
import ro.ubb.catalog.core.converter.CityConverter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.lang.Math.max;

@Service
public class CityServiceImpl implements ICityService{
    private final CityRepository cityRepo;
    private final BusStationRepository busStationRepo;

    public static final Logger logger = LoggerFactory.getLogger(CityServiceImpl.class);

    public CityServiceImpl(CityRepository cityRepo, BusStationRepository busStationRepo) {
        this.cityRepo = cityRepo;
        this.busStationRepo = busStationRepo;
    }


    @Override
    public Set<BusStation> findAllStationsForCity(Long id) {
        logger.trace("findAllStationsForCity - method entered");
        List<City> cities = this.cityRepo.findAllWithStations();
        for(City c: cities){
            if(c.getId().equals(id)){
                logger.trace("findAllStationsForCity - method finished " + c.getStations());
                return c.getStations();
            }
        }
        return null;
    }

    @Override
    public Set<BusStop> findAllStopsForCity(Long cityId, Long stationId) {
        logger.trace("findAllStopsForCity - method entered");
        List<City> cities = this.cityRepo.findAllWithStationsAndStops();
        for(City c: cities){
            if(c.getId().equals(cityId)){
                Set<BusStation> stations = c.getStations();
                for(BusStation station: stations){
                    if(station.getId().equals(stationId)){
                        logger.trace("findAllStopsForCity - method finished " + station.getStops());
                        return station.getStops();
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void save(City city) {
        logger.trace("add city - method entered - name: " + city.getName() + ", population: " + city.getPopulation());

        cityRepo.save(city);
        logger.trace("add city - method finished");

    }

    @Override
    @Transactional
    public void update(Long id, String name, int population) {
        logger.trace("update city - method entered - id: " + id + ", name: " + name + ", population: " + population);

        cityRepo.findById(id)
                .ifPresent((c) -> {
                        c.setName(name);
                        c.setPopulation(population);
                    }
        );

        logger.trace("update city - method finished");
    }

    @Override
    public void delete(Long id) {
        logger.trace("delete city - method entered - id: " + id);

        StreamSupport.stream(busStationRepo.findAll().spliterator(), false)
                .filter(station -> station.getCity().getId().equals(id))
                .findAny()
                .ifPresent((busStation) -> {
                    throw new BusManagementException("There are bus stations in the city!");
                });

        cityRepo.findById(id)
                .ifPresent((c) -> cityRepo.deleteById(c.getId())
                    );

        logger.trace("delete city - method finished");
    }

    @Override
    @Transactional
    public List<City> findAll() {
        logger.trace("findAll cities - method entered");

        List<City> cities = cityRepo.findAllWithStationsAndStops();

        logger.trace("findAll cities: " + cities);

       return cities;
    }

    @Override
    public City findCityByName(String name) {
        return this.cityRepo.findCityByName(name);
    }

}






