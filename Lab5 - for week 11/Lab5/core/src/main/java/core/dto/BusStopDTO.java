package core.dto;

import core.domain.Bus;
import core.domain.BusStation;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BusStopDTO extends BaseDTO<Long>{
    Bus bus;
    BusStation busStation;
    String stopTime;

}
