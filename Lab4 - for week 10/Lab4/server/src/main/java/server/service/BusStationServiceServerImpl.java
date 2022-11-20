package server.service;
import common.domain.BusStation;
import common.domain.BusStop;
import common.domain.City;
import common.exceptions.BusManagementException;
import common.service.IBusStationService;
import common.utils.Pair;
import org.springframework.stereotype.Service;
import server.repository.IRepository;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.StreamSupport;

@Service
public class BusStationServiceServerImpl implements IBusStationService{
    private final IRepository<Long, City> cityRepo;
    private final IRepository<Long, BusStation> busStationRepo;
    private  final IRepository<Pair<Long, Long>, BusStop> busStopRepo;
    private final ExecutorService executorService;

    public BusStationServiceServerImpl(IRepository<Long, BusStation> busStationRepo, IRepository<Long, City> cityRepo, IRepository<Pair<Long, Long>, BusStop> busStopRepo, ExecutorService executorService){
        this.busStationRepo = busStationRepo;
        this.cityRepo = cityRepo;
        this.busStopRepo = busStopRepo;
        this.executorService = executorService;
    }

    @Override
    public Future<String> save(BusStation busStation) {
        return executorService.submit(() -> {

            Optional<City> cityOptional = cityRepo.findOne(busStation.getCityId());
            cityOptional.ifPresentOrElse(
                    (City c) -> {
                        busStationRepo.save(busStation);
                    },
                    () -> {
                        throw new BusManagementException("City does not exist!");
                    }
            );

            return "Bus station added successfully!";
        });
    }

    @Override
    public Future<String> update(BusStation busStation) {
        return executorService.submit(() -> {

            var optional = busStationRepo.findOne(busStation.getId());

            optional.ifPresentOrElse(
                    (o) -> {},
                    () -> {throw new BusManagementException("BusStation not found!");}
            );

            BusStation copy = optional.get();

            copy.setName(busStation.getName());

            busStationRepo.update(copy);

            return "Bus station updated successfully!";
        });
    }

    @Override
    public Future<String> delete(Long id) {
        return executorService.submit(() -> {

            StreamSupport.stream(busStopRepo.findAll().spliterator(), false)
                    .filter(busStop -> busStop.getBusStationId().equals(id))
                    .findAny()
                    .ifPresent((busStop) -> {
                        throw new BusManagementException("There are bus stops in the bus station!");
                    });
            busStationRepo.delete(id).orElseThrow(()->{
                throw new BusManagementException("BusStation does not exist!");
            });

            return "Bus station deleted successfully!";
        });
    }

    @Override
    public Future<Set<BusStation>> findAll() {
        return executorService.submit(() -> (Set<BusStation>) busStationRepo.findAll());
    }
}
