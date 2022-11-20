package core.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class BusesFilterResponseDTO implements Serializable {

    List<BusFilterDTO> filteredBusesDTO;
}
