package de.andwari.agon.business.mapper;

import de.andwari.agon.core.entity.PlayerEntity;
import de.andwari.agon.model.player.Player;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

    PlayerEntity toEntity(Player player);

    @Mapping(target = "dropped", constant = "false")
    Player toModel(PlayerEntity player);

}
