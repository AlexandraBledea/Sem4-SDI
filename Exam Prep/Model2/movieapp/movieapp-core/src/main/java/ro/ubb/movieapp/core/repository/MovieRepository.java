package ro.ubb.movieapp.core.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.ubb.movieapp.core.model.Movie;

import java.util.List;

/**
 * author: radu
 */
public interface MovieRepository extends MovieAppRepository<Movie, Long> {


    @Query("select distinct m from Movie m where m.year<=:year")
    @EntityGraph(value="moviesWithActors", type = EntityGraph.EntityGraphType.LOAD)
    List<Movie> getMoviesWithActorsLessThanYear(@Param("year") int year);

    @Query("select distinct m from Movie m where m.year>=:year")
    @EntityGraph(value="moviesWithActors", type = EntityGraph.EntityGraphType.LOAD)
    List<Movie> getMoviesWithActorsGreaterThanYear(@Param("year") int year);

    @Query("select distinct m from Movie m where m.year<=:year")
    List<Movie> getMoviesLessThanYear(@Param("year") int year);

    @Query("select distinct m from Movie m where m.year>=:year")
    List<Movie> getMoviesGreaterThanYear(@Param("year") int year);
}
