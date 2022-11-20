package web.controller;


import core.converter.BusStopConverter;
import core.domain.Bus;
import core.domain.BusStop;
import core.dto.*;
import core.service.IBusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import core.converter.BusConverter;

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
        var bus = busConverter.convertModelToDto(this.busService.findBusByModelName(modelName));
        logger.trace("getBus - method finished");
        return bus;
    }

    @RequestMapping(value="/buses/{id}")
    BusStopsDTO findAllStopsForBus(@PathVariable Long id){
        Set<BusStop> stops = busService.findAllStopsForBus(id);
        return new BusStopsDTO(stopConverter.convertModelsToDTOs(stops));
    }

    @RequestMapping(value="/buses/{pageNumber}/{pageSize}")
    ResponseEntity<PagingResponse<BusDTO>> findAll(@PathVariable Integer pageNumber, @PathVariable Integer pageSize){
        PagingRequest pagingRequest = new PagingRequest(pageNumber, pageSize);
        PagingResponse<BusDTO> buses = busService.findAll(pagingRequest);
        return new ResponseEntity<>(buses, HttpStatus.OK);
    }

    @RequestMapping(value="/buses")
    BusesDTO findAll(){
        logger.trace("findAll buses - method entered");
        List<Bus> buses = busService.findAll();
        BusesDTO busesDTO = new BusesDTO(busConverter.convertModelsToDTOs(buses));
        logger.trace("findAll buses: " + buses);
        return busesDTO;
    }

    //TODO
//    @RequestMapping(value="/buses/filterBusesByModelName/{modelName}")
//    BusesDTO filterBusesByModelName(@PathVariable String modelName){
//        logger.trace("filterBusesByModelName - method entered - " + modelName);
//
//        List<Bus> filteredBuses = this.busService.filterBusesByModelName(modelName);
//        BusesDTO result = new BusesDTO(busConverter.convertModelsToDTOs(filteredBuses));
//
//        logger.trace("filterBusesByModelName - method finished - " + result);
//        return result;
//    }
//
//    @RequestMapping(value = "/buses/sortBusesByCapacityInAscendingOrder")
//    BusesDTO sortBusesByCapacityInAscendingOrder(){
//        logger.trace("sortBusesByCapacityInAscendingOrder - method entered");
//
//        List<Bus> sortedBuses = this.busService.sortBusesByCapacityInAscendingOrder();
//        BusesDTO result = new BusesDTO(busConverter.convertModelsToDTOs(sortedBuses));
//
//        logger.trace("sortBusesByCapacityInAscendingOrder - method finished");
//        return result;
//    }

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
