package de.andwari.agon.app.mapper;

import de.andwari.agon.app.item.StandingItem;
import de.andwari.agon.model.event.Standing;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StandingMapper {

    @Mapping(target = "player", source = "playerName")
    StandingItem toItem(Standing standing);

}
