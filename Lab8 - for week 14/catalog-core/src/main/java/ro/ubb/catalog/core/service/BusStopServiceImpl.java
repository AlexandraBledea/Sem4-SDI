package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.exceptions.BusManagementException;
import ro.ubb.catalog.core.model.Bus;
import ro.ubb.catalog.core.model.BusStation;
import ro.ubb.catalog.core.model.BusStop;
import ro.ubb.catalog.core.repository.bus.BusRepository;
import ro.ubb.catalog.core.repository.busStation.BusStationRepository;
import ro.ubb.catalog.core.converter.BusStopConverter;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static java.lang.Math.max;

@Service
public class BusStopServiceImpl implements IBusStopService{
    private final BusRepository busRepo;
    private  final BusStationRepository busStationRepo;

    public static final Logger logger = LoggerFactory.getLogger(BusStopServiceImpl.class);

    public BusStopServiceImpl(BusRepository busRepo, BusStationRepository busStationRepo) {
        this.busRepo = busRepo;
        this.busStationRepo = busStationRepo;
    }


    @Override
    @Transactional
    public void save(Long busId, Long stationId, String stopTime) {
        logger.trace("add busStop - method - entered - busId: " + busId + ", stationId: " + stationId + ", stopTime: " + stopTime);

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

        Optional.of(bus.getStops()
                .stream()
                .anyMatch(s -> s.getBus().getId().equals(busId) && s.getStation().getId().equals(stationId)
                        )
                    )
                        .filter(bool -> bool.equals(true))
                                .ifPresent((trueVal) -> {
                                    existsForBus.set(true);
                                });


        if(existsForBus.get()){
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
    @Transactional
    public List<BusStop> findAll() {
        logger.trace("findAll stops - method entered");
        List<Bus> buses = this.busRepo.findAllWithDriverAndStops();
        List<BusStop> stops = buses.stream()
                        .flatMap(b -> b.getStops().stream())
                .collect(Collectors.toList());
        logger.trace("findAll stops: " + stops);
        return stops;
    }

}
