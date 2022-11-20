package ro.ubb.catalog.core.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BusStopUpdateDTO extends BaseDTO<Long> {
    String stopTime;
}
