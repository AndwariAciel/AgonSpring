package de.andwari.agon.app.mapper;

import de.andwari.agon.app.item.MatchItem;
import de.andwari.agon.model.event.Match;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MatchMapper {

    @Mapping(target = "matchId", source = "id")
    @Mapping(target = "player1", source = "player1.name")
    @Mapping(target = "player2", source = "player2.name")
    MatchItem toMatchItem(Match match);
}
