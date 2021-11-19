package de.andwari.agon.business.mapper;

import de.andwari.agon.core.entity.PlayerEntity;
import de.andwari.agon.model.player.Player;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

    PlayerEntity toEntity(Player player);

    Player toModel(PlayerEntity player);

}
