package de.andwari.agon.business.mapper;

import de.andwari.agon.core.entity.RoundEntity;
import de.andwari.agon.model.event.Round;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = MatchMapper.class)
public interface RoundMapper {

    @Mapping(target = "eventId", source = "eventId")
    RoundEntity toEntity(Round round, Long eventId);

    RoundEntity toEntity(Round round);

    Round toModel(RoundEntity entity);

}
