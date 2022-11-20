package ro.ubb.catalog.web.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.catalog.core.converter.BusStationConverter;
import ro.ubb.catalog.core.converter.BusStopConverter;
import ro.ubb.catalog.core.dto.BusStationsDTO;
import ro.ubb.catalog.core.dto.BusStopsDTO;
import ro.ubb.catalog.core.model.BusStation;
import ro.ubb.catalog.core.model.BusStop;
import ro.ubb.catalog.core.model.City;
import ro.ubb.catalog.core.service.ICityService;
import ro.ubb.catalog.core.converter.CityConverter;
import ro.ubb.catalog.core.dto.CitiesDTO;
import ro.ubb.catalog.core.dto.CityDTO;

import java.util.List;
import java.util.Set;

@RestController
public class CityController {

    public static final Logger logger = LoggerFactory.getLogger(CityController.class);

    private final ICityService cityService;

    private final CityConverter cityConverter;

    private final BusStationConverter stationConverter;

    private final BusStopConverter stopConverter;

    public CityController(ICityService cityService, CityConverter cityConverter, BusStationConverter stationConverter, BusStopConverter stopConverter) {
        this.cityService = cityService;
        this.cityConverter = cityConverter;
        this.stationConverter = stationConverter;
        this.stopConverter = stopConverter;
    }


    @RequestMapping(value="/cities/{id}")
    BusStationsDTO findAllStationsForCity(@PathVariable Long id){
        logger.trace("findAllStationsForCity - method entered");

        Set<BusStation> stations = cityService.findAllStationsForCity(id);

        logger.trace("findAllStationsForCity - method finished: " + stations);

        return new BusStationsDTO(stationConverter.convertModelsToDTOs(stations));
    }

    @RequestMapping(value="/cities/{cityId}/{stationId}")
    BusStopsDTO findAllStopsForCity(@PathVariable Long cityId, @PathVariable Long stationId){
        logger.trace("findAllStopsForCity - method entered");

        Set<BusStop> stops = cityService.findAllStopsForCity(cityId, stationId);

        logger.trace("findAllStopsForCity - method finished: " + stops);

        return new BusStopsDTO(stopConverter.convertModelsToDTOs(stops));
    }

    @RequestMapping(value="/cities/getCity/{name}")
    CityDTO getCity(@PathVariable String name){
        logger.trace("getCity - method entered - name: " + name);
        CityDTO city = cityConverter.convertModelToDto(this.cityService.findCityByName(name));
        logger.trace("getCity - method finished");
        return city;
    }

    @RequestMapping(value="/cities")
    CitiesDTO findAll(){
        logger.trace("findAll cities - method entered");
        List<City> cities = cityService.findAll();
        CitiesDTO citiesDTO = new CitiesDTO(cityConverter.convertModelsToDTOsList(cities));
        logger.trace("findAll cities: " + cities);
        return citiesDTO;
    }


    @RequestMapping(value="/cities", method = RequestMethod.POST)
    void save(@RequestBody CityDTO cityDTO){
        logger.trace("add city - method entered - cityDTO: " + cityDTO);

        City city = cityConverter.convertDtoToModel(cityDTO);
        cityService.save(city);

        logger.trace("add city - city added");
    }


    @RequestMapping(value="/cities/{id}", method = RequestMethod.PUT)
    void update(@PathVariable Long id, @RequestBody CityDTO cityDTO){
        logger.trace("update city - method entered - cityDTO: " + cityDTO);


        City city = cityConverter.convertDtoToModel(cityDTO);
        cityService.update(id, city.getName(), city.getPopulation());

        logger.trace("update city - city updated");
    }


    @RequestMapping(value="/cities/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> delete(@PathVariable Long id){
        cityService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
