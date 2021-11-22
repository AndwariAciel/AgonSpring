package de.andwari.agon.app.mapper;

import de.andwari.agon.app.item.PlayerItem;
import de.andwari.agon.model.player.Player;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlayerItemMapper {

    PlayerItem toItem(Player player);
}
