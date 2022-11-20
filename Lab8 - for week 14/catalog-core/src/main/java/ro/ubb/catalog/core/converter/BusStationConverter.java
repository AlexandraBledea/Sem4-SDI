package ro.ubb.catalog.core.converter;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.BusStation;
import ro.ubb.catalog.core.dto.BusStationDTO;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BusStationConverter extends BaseConverter<Long, BusStation, BusStationDTO> {


    private final CityConverter cityConverter;

    public BusStationConverter(CityConverter cityConverter) {
        this.cityConverter = cityConverter;
    }

    @Override
    public BusStation convertDtoToModel(BusStationDTO dto) {
        BusStation model = BusStation.builder()
                .name(dto.getName())
                .city(cityConverter.convertDtoToModel(dto.getCity()))
                .build();

        model.setId(dto.id);
        return model;
    }

    @Override
    public BusStationDTO convertModelToDto(BusStation busStation) {
        BusStationDTO busStationDto = BusStationDTO.builder()
                .name(busStation.getName())
                .city(cityConverter.convertModelToDto(busStation.getCity()))
                .build();

        busStationDto.setId(busStation.getId());
        return busStationDto;
    }



    public List<BusStationDTO> convertModelsToDTOsList(Collection<BusStation> models) {
        return models.stream().map(this::convertModelToDto).collect(Collectors.toList());
    }
}
