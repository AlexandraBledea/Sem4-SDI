package ro.ubb.movieapp.core.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * author: radu
 */


@NamedEntityGraphs({
        @NamedEntityGraph(name="moviesWithActors",
        attributeNodes = @NamedAttributeNode(value = "actors"))
})

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="movie")
public class Movie extends BaseEntity<Long> {

    @Column(name="title", unique = true)
    private String title;

    @Column(name="year")
    private int year;

    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Actor> actors;

    public void deleteActor(Long actorID){
        Actor actor = actors.stream().filter(a -> a.getId().equals(actorID)).collect(Collectors.toList()).get(0);
        this.actors.remove(actor);
        actor.setMovie(null);
    }
}
