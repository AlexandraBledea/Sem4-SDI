package ro.ubb.movieapp.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.movieapp.core.service.MovieService;
import ro.ubb.movieapp.web.converter.MovieConverter;
import ro.ubb.movieapp.web.converter.MovieWithActorsConverter;
import ro.ubb.movieapp.web.dto.MoviesDTO;
import ro.ubb.movieapp.web.dto.MoviesWithActorsDTO;

@RestController
@CrossOrigin(allowedHeaders = "*")
public class MovieController {

    public final MovieService movieService;

    public final MovieConverter movieConverter;

    public final MovieWithActorsConverter movieWithActorsConverter;

    public MovieController(MovieService movieService, MovieConverter movieConverter, MovieWithActorsConverter movieWithActorsConverter) {
        this.movieService = movieService;
        this.movieConverter = movieConverter;
        this.movieWithActorsConverter = movieWithActorsConverter;
    }

    @RequestMapping(value="/movies/getMovies/{year}/{lessThan}")
    MoviesDTO getMoviesByYear(@PathVariable int year, @PathVariable boolean lessThan){
        return new MoviesDTO(movieConverter.convertModelsToDtos(movieService.getMoviesByYear(year, lessThan)));
    }

    @RequestMapping(value="/movies/getMoviesWithActors/{year}/{lessThan}")
    MoviesWithActorsDTO getMoviesWithActorsByYear(@PathVariable int year, @PathVariable boolean lessThan){
        return new MoviesWithActorsDTO(movieWithActorsConverter.convertModelsToDtos(movieService.getMoviesWithActorsByYear(year, lessThan)));
    }

    @RequestMapping(value="/movies/{movieId}/{actorId}", method = RequestMethod.DELETE)
    ResponseEntity<?> delete(@PathVariable Long movieId, @PathVariable Long actorId){
        movieService.deleteActor(movieId, actorId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
