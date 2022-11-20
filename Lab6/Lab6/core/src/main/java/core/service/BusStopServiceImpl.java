package core.service;

import core.domain.Bus;
import core.domain.BusStation;
import core.domain.BusStop;
import core.exceptions.BusManagementException;
import core.repository.IBusRepository;
import core.repository.IBusStationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static java.lang.Math.max;

@Service
public class BusStopServiceImpl implements IBusStopService{
    private final IBusRepository busRepo;
    private  final IBusStationRepository busStationRepo;

    public static final Logger logger = LoggerFactory.getLogger(BusStopServiceImpl.class);

    public BusStopServiceImpl(IBusRepository busRepo, IBusStationRepository busStationRepo) {
        this.busRepo = busRepo;
        this.busStationRepo = busStationRepo;
    }


    @Override
    @Transactional
    public void save(Long busId, Long stationId, String stopTime) {
        logger.trace("add busStop - method - entered - busId: " + busId + ", stationId: " + stationId + ", stopTime: " + stopTime);

        Optional<Bus> optionalBus = this.busRepo.findById(busId);
        Optional<BusStation> optionalBusStation = this.busStationRepo.findById(stationId);

        if(optionalBus.isEmpty()){
            throw  new BusManagementException("Invalid bus for the bus stop!");
        }

        if(optionalBusStation.isEmpty()){
            throw  new BusManagementException("Invalid bus station for the bus stop!");
        }


        Bus bus = this.busRepo.findAll()
                        .stream()
                        .filter(b -> b.getId().equals(busId))
                        .collect(Collectors.toList())
                        .get(0);

        BusStation busStation = this.busStationRepo.findAll()
                        .stream()
                        .filter(s-> s.getId().equals(stationId))
                        .collect(Collectors.toList())
                        .get(0);

        AtomicReference<Boolean> existsForBus = new AtomicReference<>(false);
        AtomicReference<Boolean> existsForStation = new AtomicReference<>(false);

        Optional.of(bus.getStops()
                .stream()
                .anyMatch(s -> s.getBus().getId().equals(busId)
                        )
                    )
                        .filter(bool -> bool.equals(true))
                                .ifPresent((trueVal) -> {
                                    existsForBus.set(true);
                                });

        Optional.of(busStation.getStops()
                .stream()
                .anyMatch(s -> s.getStation().getId().equals(stationId)
                        )
                )
                        .filter(bool -> bool.equals(true))
                            .ifPresent((trueVal) ->{
                                    existsForStation.set(true);
                            });

        if(existsForBus.get() && existsForStation.get()){
            throw new BusManagementException("BusStop already exists");
        }

        BusStop stop = new BusStop(bus, busStation, stopTime);
        stop.setBus(bus);
        stop.setStation(busStation);
        bus.addStop(stop);
        busStation.addStop(stop);

        logger.trace("add busStop - method finished");
    }

    @Override
    @Transactional
    public void update(Long busId, Long stationId, String stopTime) {
        logger.trace("update busStop - method entered - busId: " + busId + ", stationId: " +  stationId + ", stopTime: " + stopTime);

        Optional<Bus> optionalBus = this.busRepo.findById(busId);
        Optional<BusStation> optionalBusStation = this.busStationRepo.findById(stationId);

        if(optionalBus.isEmpty()){
            throw  new BusManagementException("Invalid bus for the bus stop!");
        }

        if(optionalBusStation.isEmpty()){
            throw  new BusManagementException("Invalid bus station for the bus stop!");
        }


        Bus bus = this.busRepo.findAll()
                .stream()
                .filter(b -> b.getId().equals(busId))
                .collect(Collectors.toList())
                .get(0);

        BusStation busStation = this.busStationRepo.findAll()
                .stream()
                .filter(s-> s.getId().equals(stationId))
                .collect(Collectors.toList())
                .get(0);

        bus.updateStop(busId, stationId, stopTime);
        busStation.updateStop(busId, stationId, stopTime);

        logger.trace("update busStop - method finished");
    }

    @Override
    @Transactional
    public void delete(Long busId, Long stationId) {
        logger.trace("delete busStop - method entered - busId: " + busId + ", stationId: " + stationId);

        Optional<Bus> optionalBus = this.busRepo.findById(busId);
        Optional<BusStation> optionalBusStation = this.busStationRepo.findById(stationId);

        if(optionalBus.isEmpty()){
            throw  new BusManagementException("Invalid bus for the bus stop!");
        }

        if(optionalBusStation.isEmpty()){
            throw  new BusManagementException("Invalid bus station for the bus stop!");
        }


        Bus bus = this.busRepo.findAll()
                .stream()
                .filter(b -> b.getId().equals(busId))
                .collect(Collectors.toList())
                .get(0);

        BusStation busStation = this.busStationRepo.findAll()
                .stream()
                .filter(s-> s.getId().equals(stationId))
                .collect(Collectors.toList())
                .get(0);

        bus.removeStop(busId);
        busStation.removeStop(stationId);

        logger.trace("delete busStop - method finished");
    }

    @Override
    public List<BusStop> findAll() {
        logger.trace("findAll stops - method entered");
        List<Bus> buses = this.busRepo.findAll();
        List<BusStop> stops = buses.stream()
                        .flatMap(b -> b.getStops().stream())
                .collect(Collectors.toList());
        logger.trace("findAll stations: " + stops);
        return stops;
    }

    public BusStop findOne(Long busId, Long stationId){

        logger.trace("findOne stop - method entered");

        Optional<Bus> optionalBus = this.busRepo.findById(busId);
        Optional<BusStation> optionalBusStation = this.busStationRepo.findById(stationId);

        if(optionalBus.isEmpty()){
            throw  new BusManagementException("Invalid bus for the bus stop!");
        }

        if(optionalBusStation.isEmpty()){
            throw  new BusManagementException("Invalid bus station for the bus stop!");
        }


        Bus bus = this.busRepo.findAll()
                .stream()
                .filter(b -> b.getId().equals(busId))
                .collect(Collectors.toList())
                .get(0);

        return bus.getStops()
                .stream()
                .filter(s -> s.getBus().getId().equals(busId))
                .filter(s -> s.getStation().getId().equals(stationId))
                .collect(Collectors.toList()).get(0);
    }
}
