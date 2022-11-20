package core.converter;

import core.domain.Bus;
import org.springframework.stereotype.Component;
import core.dto.BusDTO;

@Component
public class BusConverter extends BaseConverter<Long, Bus, BusDTO>{
    @Override
    public Bus convertDtoToModel(BusDTO dto) {
        var model = new Bus();
        model.setId(dto.getId());
        model.setModelName(dto.getModelName());
        model.setFuel(dto.getFuel());
        model.setCapacity(dto.getCapacity());
        return model;
    }

    @Override
    public BusDTO convertModelToDto(Bus bus) {
        var busDto = new BusDTO(bus.getModelName(), bus.getFuel(), bus.getCapacity());
        busDto.setId(bus.getId());
        return busDto;
    }
}
