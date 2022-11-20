package core.converter;

import core.domain.Bus;
import org.springframework.stereotype.Component;
import core.dto.BusDTO;

@Component
public class BusConverter extends BaseConverter<Long, Bus, BusDTO>{
    @Override
    public Bus convertDtoToModel(BusDTO dto) {
        var model = Bus.builder()
                        .modelName(dto.getModelName())
                        .capacity(dto.getCapacity())
                        .fuel(dto.getFuel())
                        .build();
        model.setId(dto.getId());
        return model;
    }

    @Override
    public BusDTO convertModelToDto(Bus bus) {
        var busDto = new BusDTO(bus.getModelName(), bus.getFuel(), bus.getCapacity());
        busDto.setId(bus.getId());
        return busDto;
    }
}
