package ro.ubb.movieapp.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ActorDTO extends BaseDto{
    String name;
    int rating;

}
