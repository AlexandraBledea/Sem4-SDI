package ro.ubb.movieapp.web.dto;

import lombok.*;

import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MovieWithActorsDTO extends BaseDto{
    String title;
    int year;
    private Set<ActorDTO> actors;
}
