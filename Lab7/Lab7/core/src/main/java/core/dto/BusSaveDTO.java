package core.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode()
@ToString(callSuper = true)
public class BusSaveDTO {
    private String modelName;

    private String fuel;

    private int capacity;

    private Long driverId;
}
