package ro.ubb.movieapp.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MovieDTO extends BaseDto{
    String title;
    int year;
}
