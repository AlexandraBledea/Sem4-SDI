package web.controller;

import core.domain.BusStation;
import core.service.IBusStationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import core.converter.BusStationConverter;
import core.dto.BusStationSaveDTO;
import core.dto.BusStationUpdateDTO;
import core.dto.BusStationsDTO;

import java.util.List;

@RestController
public class BusStationController {

    public static final Logger logger = LoggerFactory.getLogger(BusStationController.class);

    private final IBusStationService busStationService;

    private final BusStationConverter busStationConverter;


    public BusStationController(IBusStationService busStationService, BusStationConverter busStationConverter) {
        this.busStationService = busStationService;
        this.busStationConverter = busStationConverter;
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
