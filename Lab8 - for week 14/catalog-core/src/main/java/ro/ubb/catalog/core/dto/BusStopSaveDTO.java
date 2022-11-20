package ro.ubb.catalog.core.dto;

import lombok.*;

import java.time.LocalTime;
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode()
@ToString(callSuper = true)
public class BusStopSaveDTO {
    Long busId;
    Long stationId;
    String stopTime;
}
