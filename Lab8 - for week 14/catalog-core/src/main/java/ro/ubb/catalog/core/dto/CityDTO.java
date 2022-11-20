package ro.ubb.catalog.core.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CityDTO extends BaseDTO<Long>{
    private String name;
    private int population;
}
