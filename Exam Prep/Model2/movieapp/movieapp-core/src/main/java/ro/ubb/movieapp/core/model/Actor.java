package ro.ubb.movieapp.core.model;

import lombok.*;

import javax.persistence.*;

/**
 * author: radu
 */


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="actor")
public class Actor extends BaseEntity<Long> {
    @Column(name="name", unique = true)
    private String name;

    @Column(name="rating")
    private int rating;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="movie_id")
    private Movie movie;

}
