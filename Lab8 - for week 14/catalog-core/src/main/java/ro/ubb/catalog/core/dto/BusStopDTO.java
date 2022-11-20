package ro.ubb.catalog.core.dto;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(exclude = {"bus", "busStation"})
@ToString(callSuper = true, exclude = {"bus", "busStation"})
public class BusStopDTO implements Serializable {
    BusDTO bus;
    BusStationDTO busStation;
    String stopTime;

}
