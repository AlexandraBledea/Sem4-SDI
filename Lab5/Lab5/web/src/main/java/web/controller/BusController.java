package web.controller;


import core.domain.Bus;
import core.service.IBusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import core.converter.BusConverter;
import core.dto.BusDTO;
import core.dto.BusesDTO;

import java.util.List;

@RestController
public class BusController {

    public static final Logger logger = LoggerFactory.getLogger(BusController.class);

    private final IBusService busService;

    private final BusConverter busConverter;


    public BusController(IBusService busService, BusConverter busConverter) {
        this.busService = busService;
        this.busConverter = busConverter;
    }

    @RequestMapping(value="/buses")
    BusesDTO findAll(){
        logger.trace("findAll buses - method entered");
        List<Bus> buses = busService.findAll();
        BusesDTO busesDTO = new BusesDTO(busConverter.convertModelsToDTOs(buses));
        logger.trace("findAll buses: " + buses);
        return busesDTO;
    }

    @RequestMapping(value="/buses", method = RequestMethod.POST)
    void save(@RequestBody BusDTO busDTO){
        logger.trace("add bus - method entered - busDTO: " + busDTO);

        var bus = busConverter.convertDtoToModel(busDTO);
        busService.save(bus.getModelName(), bus.getFuel(), bus.getCapacity());

        logger.trace("add bus - bus added");
    }

    @RequestMapping(value="/buses/{id}", method = RequestMethod.PUT)
    void update(@PathVariable Long id, @RequestBody BusDTO busDTO){
        logger.trace("update bus - method entered - busDTO: " + busDTO);

        var bus = busConverter.convertDtoToModel(busDTO);
        busService.update(id, bus.getModelName(), bus.getFuel(), bus.getCapacity());

        logger.trace("update bus - bus updated");
    }

    @RequestMapping(value="/buses/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> delete(@PathVariable Long id){
        busService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
