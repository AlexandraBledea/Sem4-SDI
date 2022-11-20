package core.dto;

import core.domain.City;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)

public class BusStationDTO extends BaseDTO<Long>{
    String name;
    City city;
}
