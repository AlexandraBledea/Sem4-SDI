package ro.ubb.catalog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.catalog.core.converter.BusStopConverter;
import ro.ubb.catalog.core.dto.*;
import ro.ubb.catalog.core.model.BusStation;
import ro.ubb.catalog.core.model.BusStop;
import ro.ubb.catalog.core.service.IBusStationService;
import ro.ubb.catalog.core.converter.BusStationConverter;

import java.util.List;
import java.util.Set;

@RestController
public class BusStationController {

    public static final Logger logger = LoggerFactory.getLogger(BusStationController.class);

    private final IBusStationService busStationService;

    private final BusStationConverter busStationConverter;

    private final BusStopConverter stopConverter;


    public BusStationController(IBusStationService busStationService, BusStationConverter busStationConverter, BusStopConverter stopConverter) {
        this.busStationService = busStationService;
        this.busStationConverter = busStationConverter;
        this.stopConverter = stopConverter;
    }

    @RequestMapping(value="/stations/getStation/{name}")
    BusStationDTO getStation(@PathVariable String name){
        logger.trace("getStation - method entered - name: " + name);
        BusStationDTO station = busStationConverter.convertModelToDto(this.busStationService.findBusStationByName(name));
        logger.trace("getStation - method finished");
        return station;
    }

    @RequestMapping(value="/stations/{id}")
    BusStopsDTO findAllStopsForStation(@PathVariable Long id){
        logger.trace("findAllStopsForStation - method entered");

        Set<BusStop> stops = busStationService.findAllStopsForStation(id);

        logger.trace("findAllStopsForStation - method finished: " + stops);
        return new BusStopsDTO(stopConverter.convertModelsToDTOs(stops));
    }

    @RequestMapping(value="/stations")
    BusStationsDTO findAll(){
        logger.trace("findAll stations - method entered");
        List<BusStation> stations = busStationService.findAll();
        BusStationsDTO stationsDTO = new BusStationsDTO(busStationConverter.convertModelsToDTOs(stations));
        logger.trace("findAll stations: " + stations);
        return stationsDTO;
    }

    @RequestMapping(value = "/stations", method = RequestMethod.POST)
    void save(@RequestBody BusStationSaveDTO busStationSaveDTO){
        logger.trace("add bus - method entered - busStationSaveDTO: " + busStationSaveDTO);

        busStationService.save(busStationSaveDTO.getName(), busStationSaveDTO.getCityId());

        logger.trace("add bus - bus added");
    }

    @RequestMapping(value = "/stations/{id}", method = RequestMethod.PUT)
    void update(@PathVariable Long id, @RequestBody BusStationUpdateDTO busStationUpdateDTO){
        logger.trace("update bus - method entered - busStationUpdateDTO: " + busStationUpdateDTO);
        busStationService.update(id, busStationUpdateDTO.getName());

        logger.trace("update bus - bus updated");
    }

    @RequestMapping(value = "/stations/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> delete(@PathVariable Long id){
        busStationService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
