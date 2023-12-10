package de.andwari.agon.app.mapper;

import de.andwari.agon.app.item.MatchItem;
import de.andwari.agon.model.event.Match;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MatchItemMapper {

    @Mapping(target = "matchId", source = "id")
    @Mapping(target = "player1", source = "player1.name")
    @Mapping(target = "player2", source = "player2.name")
    @Mapping(target = "winsPlayer1", source = "result.p1")
    @Mapping(target = "winsPlayer2", source = "result.p2")
    @Mapping(target = "match", source = "match")
    MatchItem toMatchItem(Match match);

}
