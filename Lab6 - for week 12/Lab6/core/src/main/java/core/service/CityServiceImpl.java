package core.service;

import core.domain.Bus;
import core.domain.City;
import core.exceptions.BusManagementException;
import core.repository.IBusStationRepository;
import core.repository.ICityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

import static java.lang.Math.max;

@Service
public class CityServiceImpl implements ICityService{
    private final ICityRepository cityRepo;
    private final IBusStationRepository busStationRepo;

    public static final Logger logger = LoggerFactory.getLogger(CityServiceImpl.class);

    public CityServiceImpl(ICityRepository cityRepo, IBusStationRepository busStationRepo) {
        this.cityRepo = cityRepo;
        this.busStationRepo = busStationRepo;
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
                .ifPresentOrElse((c) -> {
                        c.setName(name);
                        c.setPopulation(population);
                    },
                    () -> {throw new BusManagementException("City not found!");}
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
                .ifPresentOrElse((c) -> cityRepo.deleteById(c.getId()),
                        () -> {
                            throw new BusManagementException("City does not exist");
                        }
                    );

        logger.trace("delete city - method finished");
    }

    @Override
    public List<City> findAll() {
        logger.trace("findAll cities - method entered");

        List<City> cities = cityRepo.findAll();

        logger.trace("findAll cities: " + cities);

       return cities;
    }
}
