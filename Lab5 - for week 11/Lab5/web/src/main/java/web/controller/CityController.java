package web.controller;

import core.domain.City;
import core.service.ICityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import core.converter.CityConverter;
import core.dto.CitiesDTO;
import core.dto.CityDTO;

import java.util.List;

@RestController
public class CityController {

    public static final Logger logger = LoggerFactory.getLogger(CityController.class);

    private final ICityService cityService;

    private final CityConverter cityConverter;

    public CityController(ICityService cityService, CityConverter cityConverter) {
        this.cityService = cityService;
        this.cityConverter = cityConverter;
    }

    @RequestMapping(value="/cities")
    CitiesDTO findAll(){
        logger.trace("findAll cities - method entered");
        List<City> cities = cityService.findAll();
        CitiesDTO citiesDTO = new CitiesDTO(cityConverter.convertModelsToDTOs(cities));
        logger.trace("findAll cities: " + cities);
        return citiesDTO;
    }


    @RequestMapping(value="/cities", method = RequestMethod.POST)
    void save(@RequestBody CityDTO cityDTO){
        logger.trace("add city - method entered - cityDTO: " + cityDTO);

        var city = cityConverter.convertDtoToModel(cityDTO);
        cityService.save(city.getName(), city.getPopulation());

        logger.trace("add city - city added");
    }


    @RequestMapping(value="/cities/{id}", method = RequestMethod.PUT)
    void update(@PathVariable Long id, @RequestBody CityDTO cityDTO){
        logger.trace("update city - method entered - cityDTO: " + cityDTO);


        var city = cityConverter.convertDtoToModel(cityDTO);
        cityService.update(id, city.getName(), city.getPopulation());

        logger.trace("update city - city updated");
    }


    @RequestMapping(value="/cities/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> delete(@PathVariable Long id){
        cityService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
