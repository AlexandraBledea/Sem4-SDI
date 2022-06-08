package ro.ubb.movieapp.web.dto;

import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MoviesWithActorsDTO {
    private Set<MovieWithActorsDTO> movies;
}
