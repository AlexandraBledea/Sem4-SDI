package server.service;
import common.domain.Bus;
import common.domain.BusStop;
import common.exceptions.BusManagementException;
import common.service.IBusService;
import common.utils.Pair;
import org.springframework.stereotype.Service;
import server.repository.IRepository;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

@Service
public class BusServiceServerImpl implements IBusService{
    private final IRepository<Long, Bus> busRepo;
    private final IRepository<Pair<Long, Long>, BusStop> busStopRepo;
    private final ExecutorService executorService;

    public BusServiceServerImpl(IRepository<Long, Bus> busRepo, IRepository<Pair<Long, Long>, BusStop> busStopRepo, ExecutorService executorService) {
        this.busRepo = busRepo;
        this.busStopRepo = busStopRepo;
        this.executorService = executorService;
    }

    @Override
    public Future<String> save(Bus bus) {
        return executorService.submit(() -> {
            busRepo.save(bus);
            return "Bus added successfully!";
        });
    }

    @Override
    public Future<String> update(Bus bus) throws BusManagementException{
        return executorService.submit(() -> {
            var optional = busRepo.findOne(bus.getId());

            optional.ifPresentOrElse(
                    (o) ->{},
                    ()  ->{
                        throw new RuntimeException("Bus does not exist");}
            );

            Bus copy = optional.get();

            copy.setModelName(bus.getModelName());
            copy.setFuel(bus.getFuel());
            copy.setCapacity(bus.getCapacity());

            busRepo.update(copy);
            return "Bus updated successfully!";
        });
    }

    @Override
    public Future<String> delete(Long id) {

        return executorService.submit(() -> {
            StreamSupport.stream(busStopRepo.findAll().spliterator(), false)
                    .filter(stop -> stop.getId().getFirst().equals(id))
                    .findAny()
                    .ifPresent((obj) -> {throw new BusManagementException("Stop(s) for this bus exist!");});

            busRepo.delete(id).orElseThrow(() -> {
                throw new BusManagementException("The bus with the given ID does not exist.");});

            return "Bus deleted successfully!";
        });

    }

    @Override
    public Future<Set<Bus>> findAll() {
        return executorService.submit(() -> (Set<Bus>) busRepo.findAll());
    }
}
