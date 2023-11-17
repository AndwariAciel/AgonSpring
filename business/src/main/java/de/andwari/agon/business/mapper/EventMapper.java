package de.andwari.agon.business.mapper;

import de.andwari.agon.core.entity.EventEntity;
import de.andwari.agon.model.event.AgonEvent;
import de.andwari.agon.model.player.Player;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        uses = {PlayerMapper.class, RoundMapper.class})
public abstract class EventMapper {

    @Mapping(target = "playerIds", source = "players", qualifiedByName = "mapIds")
    public abstract EventEntity toEntity(AgonEvent event);

    @Named("mapIds")
    List<Long> mapIds(List<Player> players) {
        return players.stream().map(Player::getId).collect(Collectors.toList());
    }
}
