package ro.ubb.catalog.core.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BusStopsDTO {
    Set<BusStopDTO> stops;
}
