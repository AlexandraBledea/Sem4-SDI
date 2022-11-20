package ro.ubb.catalog.core.converter;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Bus;
import ro.ubb.catalog.core.dto.BusDTO;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BusConverter extends BaseConverter<Long, Bus, BusDTO>{

    private final DriverConverter driverConverter;

    public BusConverter(DriverConverter driverConverter) {
        this.driverConverter = driverConverter;
    }

    @Override
    public Bus convertDtoToModel(BusDTO dto) {
//        Driver driverModel = driverConverter.convertDtoToModelOnlyDriver(dto.getDriver());

        Bus model = Bus.builder()
                        .modelName(dto.getModelName())
                        .capacity(dto.getCapacity())
                        .fuel(dto.getFuel())
                        .driver(driverConverter.convertDtoToModel(dto.getDriver()))
                        .build();

        model.setId(dto.getId());
        return model;
    }

    @Override
    public BusDTO convertModelToDto(Bus bus) {
//        DriverDTO driverDTOModel = driverConverter.convertModelToDtoOnlyDriver(bus.getDriver());

        BusDTO busDto = BusDTO.builder()
                .modelName(bus.getModelName())
                .capacity(bus.getCapacity())
                .fuel(bus.getFuel())
                .driver(driverConverter.convertModelToDto(bus.getDriver()))
                .build();

        busDto.setId(bus.getId());
        return busDto;
    }

    public List<BusDTO> convertModelsToDTOsList(Collection<Bus> models) {
        return models.stream().map(this::convertModelToDto).collect(Collectors.toList());
    }
}











//    public BusDTO convertModelToDtoOnlyBus(Bus bus){
//        BusDTO b = BusDTO.builder()
//                .modelName(bus.getModelName())
//                .capacity(bus.getCapacity())
//                .fuel(bus.getFuel())
//                .build();
//        b.setId(bus.getId());
//        return b;
//    }
//
//    public Bus convertDtoToModelOnlyBus(BusDTO busDTO){
//        Bus b = Bus.builder()
//                .modelName(busDTO.getModelName())
//                .capacity(busDTO.getCapacity())
//                .fuel(busDTO.getFuel())
//                .build();
//
//        b.setId(busDTO.getId());
//        return b;
//    }