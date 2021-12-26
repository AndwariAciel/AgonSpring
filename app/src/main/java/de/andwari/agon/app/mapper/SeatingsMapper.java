package de.andwari.agon.app.mapper;

import de.andwari.agon.app.item.SeatingsItem;
import de.andwari.agon.model.player.Player;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SeatingsMapper {

    @Mapping(target = "playerId", source = "player.id")
    @Mapping(target = "name", source = "player.name")
    @Mapping(target = "seatingNumber", source = "seating")
    SeatingsItem toSeating(Player player, String seating);
}
