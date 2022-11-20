package core.domain;

import lombok.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class BusStopPK implements Serializable {
    private Bus bus;
    private BusStation station;
}
