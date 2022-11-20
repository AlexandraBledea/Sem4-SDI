package ro.ubb.catalog.core.converter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.dto.BusDTO;
import ro.ubb.catalog.core.model.Bus;
import ro.ubb.catalog.core.model.Driver;
import ro.ubb.catalog.core.dto.DriverDTO;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DriverConverter extends BaseConverter<Long, Driver, DriverDTO> {

    @Override
    public Driver convertDtoToModel(DriverDTO dto) {
        Driver driver = Driver.builder()
                .cnp(dto.getCnp())
                .name(dto.getName())
                .build();
        driver.setId(dto.getId());
        return driver;
    }

    @Override
    public DriverDTO convertModelToDto(Driver driver) {
        DriverDTO driverDto = DriverDTO.builder()
                .name(driver.getName())
                .cnp(driver.getCnp())
                .build();
        driverDto.setId(driver.getId());
        return driverDto;
    }

    public List<DriverDTO> convertModelsToDTOsList(Collection<Driver> models) {
        return models.stream().map(this::convertModelToDto).collect(Collectors.toList());
    }
}



//    public Driver convertDtoToModelOnlyDriver(DriverDTO dto){
//        Driver driver = Driver.builder()
//                .cnp(dto.getCnp())
//                .name(dto.getName())
//                .build();
//        driver.setId(dto.getId());
//        return driver;
//    }
//
//    public DriverDTO convertModelToDtoOnlyDriver(Driver driver){
//        DriverDTO driverDto = DriverDTO.builder()
//                .name(driver.getName())
//                .cnp(driver.getCnp())
//                .build();
//        driverDto.setId(driver.getId());
//        return driverDto;
//    }
