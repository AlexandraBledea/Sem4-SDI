package core.dto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString
public class PagingRequest {
    private Integer pageNumber;

    private int pageSize;
}
