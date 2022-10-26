package de.andwari.agon.business.mapper;

import de.andwari.agon.core.entity.MatchEntity;
import de.andwari.agon.model.event.Match;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = PlayerMapper.class)
public interface MatchMapper {

    MatchEntity toEntity(Match match);

    Match toModel(MatchEntity entity);
}
