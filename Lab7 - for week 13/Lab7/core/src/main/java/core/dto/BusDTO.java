package core.dto;

import core.domain.Driver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class BusDTO extends BaseDTO<Long>{
    private String modelName;

    private String fuel;

    private int capacity;

    private Driver driver;
}
