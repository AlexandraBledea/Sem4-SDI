package ro.ubb.movieapp.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.movieapp.core.model.Movie;
import ro.ubb.movieapp.web.dto.MovieWithActorsDTO;
import ro.ubb.movieapp.web.dto.MoviesWithActorsDTO;

@Component
public class MovieWithActorsConverter extends BaseConverter<Movie, MovieWithActorsDTO> {

    private final ActorConverter actorConverter;

    public MovieWithActorsConverter(ActorConverter actorConverter) {
        this.actorConverter = actorConverter;
    }

    @Override
    public Movie convertDtoToModel(MovieWithActorsDTO dto) {
        Movie model = new Movie();
        model.setId(dto.getId());
        model.setYear(dto.getYear());
        model.setTitle(dto.getTitle());
        model.setActors(actorConverter.convertDtosToModels(dto.getActors()));
        return model;
    }

    @Override
    public MovieWithActorsDTO convertModelToDto(Movie movie) {
        MovieWithActorsDTO dto = new MovieWithActorsDTO(movie.getTitle(), movie.getYear(), actorConverter.convertModelsToDtos(movie.getActors()));
        dto.setId(movie.getId());
        return dto;
    }
}