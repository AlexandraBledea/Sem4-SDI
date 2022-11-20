package core.service;

import core.converter.BusStopConverter;
import core.domain.Bus;
import core.domain.BusStation;
import core.domain.BusStop;
import core.dto.BusStopDTO;
import core.dto.PagingRequest;
import core.dto.PagingResponse;
import core.exceptions.BusManagementException;
import core.repository.IBusRepository;
import core.repository.IBusStationRepository;
import core.utils.RestPagingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static java.lang.Math.max;

@Service
public class BusStopServiceImpl implements IBusStopService{
    private final IBusRepository busRepo;
    private  final IBusStationRepository busStationRepo;
    private final BusStopConverter busStopConverter;

    public static final Logger logger = LoggerFactory.getLogger(BusStopServiceImpl.class);

    public BusStopServiceImpl(IBusRepository busRepo, IBusStationRepository busStationRepo, BusStopConverter busStopConverter) {
        this.busRepo = busRepo;
        this.busStationRepo = busStationRepo;
        this.busStopConverter = busStopConverter;
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
//        AtomicReference<Boolean> existsForStation = new AtomicReference<>(false);

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

    @Override
    public PagingResponse<BusStopDTO> findAll(PagingRequest pagingRequest) {
        if(RestPagingUtil.isRequestPaged(pagingRequest)){

//            Page<Bus> busesPage = this.busRepo.findAll(RestPagingUtil.buildPageRequest(pagingRequest));

            List<Bus> buses = this.busRepo.findAll();
            List<BusStop> stops = buses.stream()
                    .flatMap(b -> b.getStops().stream())
                    .collect(Collectors.toList());

            List<BusStopDTO> busStopDTOS = this.busStopConverter.convertModelsToDTOsList(stops);


            int pageNumber = pagingRequest.getPageNumber();
            int pageSize = pagingRequest.getPageSize();

            int offset = pageNumber * pageSize;
            int totalPages;
            int nrOfElems;

            int modulo = stops.size() % pageSize;
            if(modulo == 0){
                totalPages = stops.size() / pageSize;
            }
            else {
                totalPages = stops.size() / pageSize + 1;
            }

            if(pageNumber == totalPages - 1 && modulo != 0){
                nrOfElems = modulo;
            } else {
                nrOfElems = pageSize;
            }

            List<BusStopDTO> result = busStopDTOS.subList(offset, offset + nrOfElems);

            return new PagingResponse<>(
                    (long) stops.size(),
                    (long) pageNumber,
                    (long) nrOfElems,
                    (long) offset,
                    (long) totalPages,
                    result);
        } else {
            List<Bus> busList = this.busRepo.findAll();

            List<BusStop> stopList = busList.stream()
                    .flatMap(b -> b.getStops().stream())
                    .collect(Collectors.toList());

            List<BusStopDTO> busStopDTOList = this.busStopConverter.convertModelsToDTOsList(stopList);

            return new PagingResponse<>(
                    (long) busStopDTOList.size(),
                    0L,
                    0L,
                    0L,
                    0L,
                    busStopDTOList);
        }
    }

    @Override
    public PagingResponse<BusStopDTO> findAllFilteredByStopTime(PagingRequest pagingRequest, String stopTime) {
        if(RestPagingUtil.isRequestPaged(pagingRequest)){

            Page<Bus> busesPage = this.busRepo.findAll(RestPagingUtil.buildPageRequest(pagingRequest));

            List<Bus> buses = this.busRepo.findAll();
            List<BusStop> stops = buses.stream()
                    .flatMap(b -> b.getStops().stream())
                    .collect(Collectors.toList());

            List<BusStop> filteredStops = new ArrayList<BusStop>();

            for(BusStop s: stops){
                if(s.getStopTime().equals(stopTime)){
                    filteredStops.add(s);
                }
            }

            List<BusStopDTO> busStopDTOS = this.busStopConverter.convertModelsToDTOsList(filteredStops);



            int pageNumber = pagingRequest.getPageNumber();
            int pageSize = pagingRequest.getPageSize();

            int offset = pageNumber * pageSize;
            int totalPages;
            int nrOfElems;

            int modulo = filteredStops.size() % pageSize;
            if(modulo == 0){
                totalPages = filteredStops.size() / pageSize;
            }
            else {
                totalPages = filteredStops.size() / pageSize + 1;
            }

            if(pageNumber == totalPages - 1 && modulo != 0){
                nrOfElems = modulo;
            } else {
                nrOfElems = pageSize;
            }

            List<BusStopDTO> result = busStopDTOS.subList(offset, offset + nrOfElems);

            return new PagingResponse<>(
                    (long) filteredStops.size(),
                    (long) pageNumber,
                    (long) nrOfElems,
                    (long) offset,
                    (long) totalPages,
                    result);
        } else {
            List<Bus> busList = this.busRepo.findAll();

            List<BusStop> stopList = busList.stream()
                    .flatMap(b -> b.getStops().stream())
                    .collect(Collectors.toList());

            List<BusStop> filteredStopList = new ArrayList<BusStop>();

            for(BusStop s: stopList){
                if(s.getStopTime().equals(stopTime)){
                    filteredStopList.add(s);
                }
            }

            List<BusStopDTO> busStopDTOList = this.busStopConverter.convertModelsToDTOsList(filteredStopList);

            return new PagingResponse<>(
                    (long) busStopDTOList.size(),
                    0L,
                    0L,
                    0L,
                    0L,
                    busStopDTOList);
        }
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
