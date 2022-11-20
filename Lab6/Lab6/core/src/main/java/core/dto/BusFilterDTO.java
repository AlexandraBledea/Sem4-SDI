package core.dto;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class BusFilterDTO implements Serializable {

    long id;

    String modelName;

    String fuel;

    int capacity;

}
