package ro.ubb.catalog.core.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, exclude = {"city"})
@ToString(callSuper = true, exclude = {"city"})
@Builder
public class BusStationDTO extends BaseDTO<Long>{
    String name;
    CityDTO city;
}
