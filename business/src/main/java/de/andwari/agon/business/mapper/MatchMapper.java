package de.andwari.agon.business.mapper;

import de.andwari.agon.core.entity.MatchEntity;
import de.andwari.agon.model.event.Match;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = PlayerMapper.class)
public interface MatchMapper {

    @Mapping(target = "player1", source = "player1.id")
    @Mapping(target = "player2", source = "player2.id")
    MatchEntity toEntity(Match match);

//    Match toModel(MatchEntity entity);
}
