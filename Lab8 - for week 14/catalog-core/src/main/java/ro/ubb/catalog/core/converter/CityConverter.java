package ro.ubb.catalog.core.converter;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.City;
import ro.ubb.catalog.core.dto.CityDTO;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CityConverter extends BaseConverter<Long, City, CityDTO> {


    @Override
    public City convertDtoToModel(CityDTO dto) {
        City model = City.builder()
                        .name(dto.getName())
                        .population(dto.getPopulation())
                                .build();
        model.setId(dto.getId());
        return model;
    }

    @Override
    public CityDTO convertModelToDto(City city) {
        CityDTO cityDto = CityDTO.builder()
                .name(city.getName())
                .population(city.getPopulation())
                .build();
        cityDto.setId(city.getId());
        return cityDto;
    }

    public List<CityDTO> convertModelsToDTOsList(Collection<City> models) {
        return models.stream().map(this::convertModelToDto).collect(Collectors.toList());
    }
}
