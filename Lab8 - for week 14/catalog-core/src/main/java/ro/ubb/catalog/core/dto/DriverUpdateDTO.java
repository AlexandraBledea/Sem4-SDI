package ro.ubb.catalog.core.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DriverUpdateDTO extends BaseDTO<Long> {
    private String name;
}
