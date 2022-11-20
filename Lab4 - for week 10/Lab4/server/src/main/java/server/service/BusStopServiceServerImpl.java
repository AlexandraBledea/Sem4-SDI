package server.service;
import common.domain.Bus;
import common.domain.BusStation;
import common.domain.BusStop;
import common.exceptions.BusManagementException;
import common.service.IBusStopService;
import common.utils.Pair;
import org.springframework.stereotype.Service;
import server.repository.IRepository;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Predicate;

@Service
public class BusStopServiceServerImpl implements IBusStopService{
    private final IRepository<Long, Bus> busRepo;
    private  final IRepository<Long, BusStation> busStationRepo;
    private final IRepository<Pair<Long, Long>, BusStop> busStopRepo;
    private final ExecutorService executorService;

    BusStopServiceServerImpl(IRepository<Pair<Long, Long>, BusStop> busStopRepo, IRepository<Long, Bus> busRepo, IRepository<Long, BusStation> busStationRepo, ExecutorService executorService){
        this.executorService = executorService;
        this.busStopRepo = busStopRepo;
        this.busRepo = busRepo;
        this.busStationRepo = busStationRepo;
    }

    @Override
    public Future<String> save(BusStop busStop) {
        return this.executorService.submit(() -> {

            Optional<Bus> busOptional = busRepo.findOne(busStop.getBusId());
            Optional<BusStation> busStationOptional = busStationRepo.findOne(busStop.getBusStationId());

            Predicate<Optional<Bus>> busNull = Optional::isEmpty;
            Predicate<Optional<BusStation>> busStationNull = Optional::isEmpty;

            final Pair<Boolean, String> resultBus =
                    new Pair<>(busNull.test(busOptional), "No Bus with the given ID has been found!\n");

            final Pair<Boolean, String> resultBusStation =
                    new Pair<>(busStationNull.test(busStationOptional), "No Bus Station with the given ID has been found!\n");

            List<Pair<Boolean, String>> testList = new ArrayList<>();

            testList.add(resultBus);
            testList.add(resultBusStation);


            testList.stream()
                    .filter((p) -> p.getFirst().equals(true))
                    .map(Pair::getSecond)
                    .reduce(String::concat)
                    .ifPresentOrElse(
                            (msg) -> {
                                throw new BusManagementException(msg);
                            },
                            () -> busStopRepo.save(busStop)
                    );

            return "Stop added successfully!";

        });

    }

    @Override
    public Future<String> update(BusStop busStop) {
        return this.executorService.submit(() -> {

            var optional = busStopRepo.findOne(busStop.getId());

            optional.ifPresentOrElse(
                    (o) ->{},
                    ()  ->{throw new BusManagementException("Bus stop does not exist");}
            );
            BusStop copy = optional.get();

            copy.setStopTime(busStop.getStopTime());

            busStopRepo.update(copy);

            return "Stop updated successfully!";

        });
    }

    @Override
    public Future<String> delete(Pair<Long, Long> id) {
        return this.executorService.submit(() -> {

            busStopRepo.delete(id).orElseThrow(() -> {
                throw new BusManagementException("Bus stop does not exist");
            });

            return "Stop deleted successfully!";

        });

    }

    @Override
    public Future<Set<BusStop>> findAll() {
        return executorService.submit(() -> (Set<BusStop>) busStopRepo.findAll());
    }
}
