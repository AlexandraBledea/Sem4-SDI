package ro.ubb.catalog.web.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.catalog.core.dto.*;
import ro.ubb.catalog.core.model.Bus;
import ro.ubb.catalog.core.model.BusStop;
import ro.ubb.catalog.core.service.IBusService;
import ro.ubb.catalog.core.converter.BusConverter;
import ro.ubb.catalog.core.converter.BusStopConverter;

import java.util.List;
import java.util.Set;

@RestController
public class BusController {

    public static final Logger logger = LoggerFactory.getLogger(BusController.class);

    private final IBusService busService;

    private final BusStopConverter stopConverter;

    private final BusConverter busConverter;


    public BusController(IBusService busService, BusStopConverter stopConverter, BusConverter busConverter) {
        this.busService = busService;
        this.stopConverter = stopConverter;
        this.busConverter = busConverter;
    }

    @RequestMapping(value="/buses/getBus/{modelName}")
    BusDTO getBus(@PathVariable String modelName){
        logger.trace("getBus - method entered - modelName: " + modelName);
        BusDTO bus = busConverter.convertModelToDto(this.busService.findBusByModelName(modelName));
        logger.trace("getBus - method finished");
        return bus;
    }

    @RequestMapping(value="/buses/{id}")
    BusStopsDTO findAllStopsForBus(@PathVariable Long id){
        Set<BusStop> stops = busService.findAllStopsForBus(id);
        return new BusStopsDTO(stopConverter.convertModelsToDTOs(stops));
    }

    @RequestMapping(value="/buses")
    BusesDTO findAll(){
        logger.trace("findAll buses - method entered");
        List<Bus> buses = busService.findAll();
        System.out.println(buses);
        BusesDTO busesDTO = new BusesDTO(busConverter.convertModelsToDTOs(buses));
        logger.trace("findAll buses: " + buses);
        return busesDTO;
    }


    @RequestMapping(value="/buses", method = RequestMethod.POST)
    void save(@RequestBody BusSaveDTO busSaveDTO){
        logger.trace("add bus - method entered - busDTO: " + busSaveDTO);

        busService.save(busSaveDTO.getModelName(), busSaveDTO.getFuel(), busSaveDTO.getCapacity(), busSaveDTO.getDriverId());

        logger.trace("add bus - bus added");
    }

    @RequestMapping(value="/buses/{id}", method = RequestMethod.PUT)
    void update(@PathVariable Long id, @RequestBody BusUpdateDTO busDTO){
        logger.trace("update bus - method entered - busDTO: " + busDTO);

        busService.update(id, busDTO.getModelName(), busDTO.getFuel(), busDTO.getCapacity());

        logger.trace("update bus - bus updated");
    }

    @RequestMapping(value="/buses/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> delete(@PathVariable Long id){
        busService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
