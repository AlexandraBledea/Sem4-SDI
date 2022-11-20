package core.dto;

import lombok.*;

import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
public class BusStationFilterPopulationDTO implements Serializable {
    long id;

    String stationName;

    String cityName;

    int population;

}
