package core.dto;

import core.domain.City;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode()
@ToString(callSuper = true)
public class BusStationSaveDTO{
    String name;
    Long cityId;

}
