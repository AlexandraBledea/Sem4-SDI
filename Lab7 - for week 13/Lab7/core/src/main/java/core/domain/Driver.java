package core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Optional;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "driver")
public class Driver extends BaseEntity<Long>{

    @Column(name = "name")
    private String name;

    @Column(name = "cnp", unique = true)
    private String cnp;

}
