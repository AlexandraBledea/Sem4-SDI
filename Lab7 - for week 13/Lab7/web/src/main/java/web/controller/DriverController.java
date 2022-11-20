package web.controller;

import core.converter.DriverConverter;
import core.domain.Driver;
import core.dto.*;
import core.service.IDriverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DriverController {
    public static final Logger logger = LoggerFactory.getLogger(CityController.class);

    private final IDriverService driverService;

    private final DriverConverter driverConverter;

    public DriverController(IDriverService driverService, DriverConverter driverConverter){
        this.driverConverter = driverConverter;
        this.driverService = driverService;
    }

    @RequestMapping(value="/drivers/getDriver/{cnp}")
    DriverDTO getDriver(@PathVariable String cnp){
        logger.trace("getDriver - method entered - cnp: " + cnp);
        var driver = driverConverter.convertModelToDto(this.driverService.findDriverByCnp(cnp));
        logger.trace("getDriver - method finished");

        return driver;
    }

    @RequestMapping(value="/drivers/{pageNumber}/{pageSize}")
    ResponseEntity<PagingResponse<DriverDTO>> findAll(@PathVariable Integer pageNumber, @PathVariable Integer pageSize){
        PagingRequest pagingRequest = new PagingRequest(pageNumber, pageSize);
        PagingResponse<DriverDTO> drivers = this.driverService.findAll(pagingRequest);
        return new ResponseEntity<>(drivers, HttpStatus.OK);
    }

    @RequestMapping(value="/drivers")
    DriversDTO findAll(){
        logger.trace("findAll drivers - method entered");

        List<Driver> drivers = driverService.findAll();
        DriversDTO driversDTO = new DriversDTO(driverConverter.convertModelsToDTOs(drivers));

        logger.trace("findAll drivers - method finished: " + drivers);

        return driversDTO;
    }

    @RequestMapping(value="drivers", method = RequestMethod.POST)
    void save(@RequestBody DriverDTO driverDTO){
        logger.trace("add driver - method entered - driverDTO: " + driverDTO);

        var driver = driverConverter.convertDtoToModel(driverDTO);
        driverService.save(driver);

        logger.trace("add driver - method finished");
    }

    @RequestMapping(value="/drivers/{id}", method = RequestMethod.PUT)
    void update(@PathVariable Long id, @RequestBody DriverUpdateDTO driverUpdateDTO){
        logger.trace("update driver - method entered - driverDTO: " + driverUpdateDTO);

        driverService.update(id, driverUpdateDTO.getName());

        logger.trace("update driver - method finished");
    }

    @RequestMapping(value="/drivers/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> delete(@PathVariable Long id){
        driverService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
