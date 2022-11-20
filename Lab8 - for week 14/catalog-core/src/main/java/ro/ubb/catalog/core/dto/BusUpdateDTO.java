package ro.ubb.catalog.core.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BusUpdateDTO extends BaseDTO<Long>{
    private String modelName;

    private String fuel;

    private int capacity;
}
