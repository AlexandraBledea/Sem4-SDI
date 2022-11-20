package ro.ubb.catalog.core.dto;


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
