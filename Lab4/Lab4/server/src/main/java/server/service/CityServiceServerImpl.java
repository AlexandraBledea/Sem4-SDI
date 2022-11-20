package server.service;
import common.domain.BusStation;
import common.domain.City;
import common.exceptions.BusManagementException;
import common.service.ICityService;
import org.springframework.stereotype.Service;
import server.repository.IRepository;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.StreamSupport;

@Service
public class CityServiceServerImpl implements ICityService{
    private final IRepository<Long, City> cityRepo;
    private final IRepository<Long, BusStation> busStationRepo;
    private final ExecutorService executorService;

    public CityServiceServerImpl(IRepository<Long, City> cityRepo, IRepository<Long, BusStation> busStationRepo, ExecutorService executorService) {
        this.cityRepo = cityRepo;
        this.executorService = executorService;
        this.busStationRepo = busStationRepo;
    }

    @Override
    public Future<String> save(City city) {
        return this.executorService.submit(() -> {
            cityRepo.save(city);
            return "City added successfully!";
        });
    }

    @Override
    public Future<String> update(City city) {
        return this.executorService.submit(() -> {
            var optional = cityRepo.findOne(city.getId());

            optional.ifPresentOrElse(
                    (o) -> {},
                    () -> {throw new BusManagementException("City not found!");}
            );

            City copy = optional.get();

            copy.setName(city.getName());
            copy.setPopulation(city.getPopulation());

            cityRepo.update(copy);

            return "City updated successfully!";
        });
    }

    @Override
    public Future<String> delete(Long id) {
        return this.executorService.submit(() -> {

            StreamSupport.stream(busStationRepo.findAll().spliterator(), false)
                    .filter(station -> station.getCityId().equals(id))
                    .findAny()
                    .ifPresent((busStation) -> {
                        throw new BusManagementException("There are bus stations in the city!");
                    });
            cityRepo.delete(id).orElseThrow(() -> {
                throw new BusManagementException("City does not exist");
            });


            return "City deleted successfully!";
        });
    }

    @Override
    public Future<Set<City>> findAll() {
        return executorService.submit(() -> (Set<City>) cityRepo.findAll());
    }
}
