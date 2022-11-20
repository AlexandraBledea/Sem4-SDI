package core.converter;

import core.domain.Bus;
import core.domain.City;
import core.dto.CityDTO;
import org.springframework.stereotype.Component;
import core.dto.BusDTO;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BusConverter extends BaseConverter<Long, Bus, BusDTO>{
    @Override
    public Bus convertDtoToModel(BusDTO dto) {
//        var model = Bus.builder()
//                        .modelName(dto.getModelName())
//                        .capacity(dto.getCapacity())
//                        .fuel(dto.getFuel())
//                        .build();
//        model.setId(dto.getId());
//        return model;
        var model = new Bus();
        model.setId(dto.getId());
        model.setModelName(dto.getModelName());
        model.setCapacity(dto.getCapacity());
        model.setFuel(dto.getFuel());
        model.setDriver(dto.getDriver());

        return model;
    }

    @Override
    public BusDTO convertModelToDto(Bus bus) {
        var busDto = new BusDTO(bus.getModelName(), bus.getFuel(), bus.getCapacity(), bus.getDriver());
        busDto.setId(bus.getId());
        return busDto;
    }

    public List<BusDTO> convertModelsToDTOsList(Collection<Bus> models) {
        return models.stream().map(this::convertModelToDto).collect(Collectors.toList());
    }
}
