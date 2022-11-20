package core.converter;

import core.domain.BusStop;
import core.dto.BusStopsDTO;
import org.springframework.stereotype.Component;
import core.dto.BusStopDTO;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BusStopConverter {

    BusConverter busConverter;
    BusStationConverter busStationConverter;

    BusStopConverter(BusConverter busConverter, BusStationConverter busStationConverter){
        this.busConverter = busConverter;
        this.busStationConverter = busStationConverter;
    }

    public BusStop convertDtoToModel(BusStopDTO dto) {
        var model = BusStop.builder()
                        .bus(this.busConverter.convertDtoToModel(dto.getBus()))
                        .station(this.busStationConverter.convertDtoToModel(dto.getBusStation()))
                        .stopTime(dto.getStopTime())
                        .build();
        return model;
    }
    
    public BusStopDTO convertModelToDto(BusStop busStop) {
        var busStopDto = BusStopDTO.builder()
                        .bus(this.busConverter.convertModelToDto(busStop.getBus()))
                        .busStation(this.busStationConverter.convertModelToDto(busStop.getStation()))
                        .stopTime(busStop.getStopTime())
                        .build();

        return busStopDto;
    }

    public Set<BusStopDTO> convertModelsToDTOs(Collection<BusStop> stops) {
        return stops.stream()
                .map(this::convertModelToDto)
                .collect(Collectors.toCollection(
                        LinkedHashSet::new
                ));
    }
}
