package core.converter;

import core.domain.BusStation;
import org.springframework.stereotype.Component;
import core.dto.BusStationDTO;

@Component
public class BusStationConverter extends BaseConverter<Long, BusStation, BusStationDTO> {
    @Override
    public BusStation convertDtoToModel(BusStationDTO dto) {
        var model = new BusStation();
        model.setName(dto.getName());
        model.setId(dto.getId());
        model.setCity(dto.getCity());
        return model;
    }

    @Override
    public BusStationDTO convertModelToDto(BusStation busStation) {
        var busStationDto = new BusStationDTO();
        busStationDto.setId(busStation.getId());
        busStationDto.setCity(busStation.getCity());
        busStationDto.setName(busStation.getName());
        return busStationDto;
    }
}
