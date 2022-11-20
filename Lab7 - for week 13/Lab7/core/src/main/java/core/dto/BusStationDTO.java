package core.dto;

import core.domain.City;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"city"})
@ToString(callSuper = true, exclude = {"city"})

public class BusStationDTO extends BaseDTO<Long>{
    String name;
    City city;
}
