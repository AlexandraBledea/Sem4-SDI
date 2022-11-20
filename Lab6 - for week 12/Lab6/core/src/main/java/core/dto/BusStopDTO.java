package core.dto;

import core.domain.Bus;
import core.domain.BusStation;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode
@ToString(callSuper = true)
public class BusStopDTO implements Serializable {
    BusDTO bus;
    BusStationDTO busStation;
    String stopTime;

}
