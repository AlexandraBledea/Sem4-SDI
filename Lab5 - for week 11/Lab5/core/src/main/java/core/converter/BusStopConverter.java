package core.converter;

import core.domain.BusStop;
import org.springframework.stereotype.Component;
import core.dto.BusStopDTO;

@Component
public class BusStopConverter extends BaseConverter<Long, BusStop, BusStopDTO> {
    @Override
    public BusStop convertDtoToModel(BusStopDTO dto) {
        var model = new BusStop();
        model.setId(dto.getId());
        model.setBus(dto.getBus());
        model.setStation(dto.getBusStation());
        model.setStopTime(dto.getStopTime());
        return model;
    }

    @Override
    public BusStopDTO convertModelToDto(BusStop busStop) {
        var busStopDto = new BusStopDTO();
        busStopDto.setId(busStop.getId());
        busStopDto.setBus(busStop.getBus());
        busStopDto.setBusStation(busStop.getStation());
        busStopDto.setStopTime(busStop.getStopTime());
        return busStopDto;
    }
}
