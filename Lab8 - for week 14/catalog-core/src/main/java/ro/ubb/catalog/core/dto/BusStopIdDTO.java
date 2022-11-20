package ro.ubb.catalog.core.dto;


import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode()
@ToString(callSuper = true)
public class BusStopIdDTO{
    Long busId;

    Long stationId;
}
