package core.converter;

import core.domain.City;
import core.domain.Driver;
import core.dto.CityDTO;
import core.dto.DriverDTO;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DriverConverter extends BaseConverter<Long, Driver, DriverDTO> {
    @Override
    public Driver convertDtoToModel(DriverDTO dto) {
        var driver = Driver.builder()
                .cnp(dto.getCnp())
                .name(dto.getName())
                .build();
        driver.setId(dto.getId());
        return driver;
    }

    @Override
    public DriverDTO convertModelToDto(Driver driver) {
        var driverDto = new DriverDTO(driver.getName(), driver.getCnp());
        driverDto.setId(driver.getId());
        return driverDto;
    }

    public List<DriverDTO> convertModelsToDTOsList(Collection<Driver> models) {
        return models.stream().map(this::convertModelToDto).collect(Collectors.toList());
    }
}
