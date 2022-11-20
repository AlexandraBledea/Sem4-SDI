package web.controller;


import core.domain.BusStop;
import core.service.IBusStopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import core.converter.BusStopConverter;
import core.dto.BusStopSaveDTO;
import core.dto.BusStopUpdateDTO;
import core.dto.BusStopsDTO;

import java.util.List;

@RestController
public class BusStopController {

    public static final Logger logger = LoggerFactory.getLogger(BusStopController.class);

    private final IBusStopService busStopService;

    private final BusStopConverter busStopConverter;


    public BusStopController(IBusStopService busStopService, BusStopConverter busStopConverter) {
        this.busStopService = busStopService;
        this.busStopConverter = busStopConverter;
    }


    @RequestMapping(value="/stops")
    BusStopsDTO findAll(){
        logger.trace("findAll stops - method entered");
        List<BusStop> stops = busStopService.findAll();
        BusStopsDTO busStopsDTO = new BusStopsDTO(busStopConverter.convertModelsToDTOs(stops));
        logger.trace("findAll stops: " + stops);
        return busStopsDTO;
    }

    @RequestMapping(value="/stops", method = RequestMethod.POST)
    void save(@RequestBody BusStopSaveDTO busStopSaveUpdateDTO){
        logger.trace("add stop - method entered - busStopSaveUpdateDTO: " + busStopSaveUpdateDTO);

        busStopService.save(busStopSaveUpdateDTO.getBusId(), busStopSaveUpdateDTO.getStationId(), busStopSaveUpdateDTO.getStopTime());

        logger.trace("add city - city added");
    }


    @RequestMapping(value="/stops/{id}", method = RequestMethod.PUT)
    void update(@PathVariable Long id, @RequestBody BusStopUpdateDTO busStopUpdateDTO){
        logger.trace("update stop - method entered - busStopSaveUpdateDTO: " + busStopUpdateDTO);

        busStopService.update(id, busStopUpdateDTO.getStopTime());

        logger.trace("update stop - city updated");
    }


    @RequestMapping(value="/stops/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> delete(@PathVariable Long id){
        busStopService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
