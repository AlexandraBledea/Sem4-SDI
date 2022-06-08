package ro.ubb.movieapp.core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.movieapp.core.model.Movie;
import ro.ubb.movieapp.core.repository.MovieRepository;

import java.util.List;
import java.util.Optional;

/**
 * author: radu
 */


@Service
public class MovieServiceImpl implements MovieService {

    MovieRepository movieRepository;

    MovieServiceImpl(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Movie> getMoviesByYear(int year, boolean lessThan) {
        if(lessThan) {
            return this.movieRepository.getMoviesLessThanYear(year);
        }
        else {
            return this.movieRepository.getMoviesGreaterThanYear(year);
        }
    }

    @Override
    public List<Movie> getMoviesWithActorsByYear(int year, boolean lessThan) {
        if(lessThan) {
            return this.movieRepository.getMoviesWithActorsLessThanYear(year);
        }
        else {
            return this.movieRepository.getMoviesWithActorsGreaterThanYear(year);
        }
    }

    @Override
    @Transactional
    public void deleteActor(Long movieId, Long actorId) {
        Optional<Movie> movie = this.movieRepository.findById(movieId);
        movie.get().deleteActor(actorId);
    }
}
