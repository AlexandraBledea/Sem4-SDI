package core.converter;

import core.domain.City;
import org.springframework.stereotype.Component;
import core.dto.CityDTO;

@Component
public class CityConverter extends BaseConverter<Long, City, CityDTO> {


    @Override
    public City convertDtoToModel(CityDTO dto) {
        var model = new City();
        model.setId(dto.getId());
        model.setPopulation(dto.getPopulation());
        model.setName(dto.getName());

        return model;
    }

    @Override
    public CityDTO convertModelToDto(City city) {
        var cityDto = new CityDTO(city.getName(), city.getPopulation());
        cityDto.setId(city.getId());
        return cityDto;
    }
}
