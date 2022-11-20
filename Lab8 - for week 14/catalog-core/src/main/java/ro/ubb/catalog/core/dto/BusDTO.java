package ro.ubb.catalog.core.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = true, exclude = {"driver"})
@ToString(callSuper = true, exclude = {"driver"})
public class BusDTO extends BaseDTO<Long>{
    private String modelName;

    private String fuel;

    private int capacity;

    private DriverDTO driver;
}
