package ro.ubb.movieapp.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.movieapp.core.model.Movie;
import ro.ubb.movieapp.web.dto.MovieDTO;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MovieConverter extends BaseConverter<Movie, MovieDTO> {

    @Override
    public Movie convertDtoToModel(MovieDTO dto) {
        Movie movie = Movie.builder()
                .title(dto.getTitle())
                .year(dto.getYear())
                .build();

        movie.setId(dto.getId());
        return movie;
    }

    @Override
    public MovieDTO convertModelToDto(Movie movie) {
        MovieDTO movieDTO = MovieDTO.builder()
                .title(movie.getTitle())
                .year(movie.getYear())
                .build();

        movieDTO.setId(movie.getId());
        return movieDTO;
    }

    public List<MovieDTO> convertModelsToDTOList(Collection<Movie> models){
        return models.stream().map(this::convertModelToDto).collect(Collectors.toList());
    }
}
