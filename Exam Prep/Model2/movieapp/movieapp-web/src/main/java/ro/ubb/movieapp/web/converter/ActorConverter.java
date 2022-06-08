package ro.ubb.movieapp.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.movieapp.core.model.Actor;
import ro.ubb.movieapp.web.dto.ActorDTO;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ActorConverter extends BaseConverter<Actor, ActorDTO> {
    @Override
    public Actor convertDtoToModel(ActorDTO dto) {
        Actor actor = Actor.builder()
                .name(dto.getName())
                .rating(dto.getRating())
                .build();
        actor.setId(actor.getId());

        return actor;
    }

    @Override
    public ActorDTO convertModelToDto(Actor actor) {
        ActorDTO actorDto = ActorDTO.builder()
                .name(actor.getName())
                .rating(actor.getRating())
                .build();

        actorDto.setId(actor.getId());
        return actorDto;
    }

    public List<ActorDTO> convertModelsToDTOList(Collection<Actor> models){
        return models.stream().map(this::convertModelToDto).collect(Collectors.toList());
    }
}
