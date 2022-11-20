package core.service;

import core.domain.Bus;
import core.domain.BusStation;
import core.domain.BusStop;
import core.exceptions.BusManagementException;
import core.repository.IBusRepository;
import core.repository.IBusStationRepository;
import core.repository.IBusStopRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static java.lang.Math.max;

@Service
public class BusStopServiceImpl implements IBusStopService{
    private final IBusRepository busRepo;
    private  final IBusStationRepository busStationRepo;
    private final IBusStopRepository busStopRepo;

    public static final Logger logger = LoggerFactory.getLogger(BusStopServiceImpl.class);

    public BusStopServiceImpl(IBusRepository busRepo, IBusStationRepository busStationRepo, IBusStopRepository busStopRepo) {
        this.busRepo = busRepo;
        this.busStationRepo = busStationRepo;
        this.busStopRepo = busStopRepo;
    }


    @Override
    public void save(Long busId, Long stationId, String stopTime) {
        logger.trace("add busStop - method - entered - busId: " + busId + ", stationId: " + stationId + ", stopTime: " + stopTime);

        Optional<Bus> bus = busRepo.findById(busId);
        Optional<BusStation> station = busStationRepo.findById(stationId);

        bus.ifPresentOrElse(
                (Bus b) -> {
                    station.ifPresentOrElse(
                            (BusStation s) -> {

                                long id = 0;
                                for (BusStop bs : this.busStopRepo.findAll())
                                    id = max(id, bs.getId() + 1);

                                BusStop stop = new BusStop(id, stopTime, b, s);
                                this.busStopRepo.save(stop);
                            },
                            () -> {
                                throw new BusManagementException("Station does not exist");
                            }
                    );
                },
                () -> {
                    throw new BusManagementException("Bus does not exist");
                }
        );

    }

    @Override
    @Transactional
    public void update(Long id, String stopTime) {
        logger.trace("update busStop - method entered - id: " + id + ", stopTime: " + stopTime);

        busStopRepo.findById(id)
                .ifPresentOrElse(
                        (stop) -> stop.setStopTime(stopTime),
                        () -> {
                            throw new BusManagementException("BusStop does not exist");
                        }
                );

        logger.trace("update busStop - method finished");
    }

    @Override
    public void delete(Long id) {
        logger.trace("delete busStop - method entered - id: " + id);
        busStopRepo.findById(id)
                .ifPresentOrElse((s) -> {
                    busStopRepo.deleteById(id);
                        },
                        () -> {
                            throw new BusManagementException("BusStop does not exist");
                        }
                );

        logger.trace("delete busStop - method finished");
    }

    @Override
    public List<BusStop> findAll() {
        logger.trace("findAll stops - method entered");

        List<BusStop> stops = busStopRepo.findAll();

        logger.trace("findAll stations: " + stops);

        return stops;
    }
}
