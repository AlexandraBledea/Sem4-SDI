package ro.ubb.catalog.core.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BusStationUpdateDTO extends BaseDTO<Long>{
    String name;
}
