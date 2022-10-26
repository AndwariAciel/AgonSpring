package de.andwari.agon.business.mapper;

import de.andwari.agon.core.entity.EventEntity;
import de.andwari.agon.model.event.AgonEvent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {PlayerMapper.class, RoundMapper.class})
public interface EventMapper {

    EventEntity toEntity(AgonEvent event);
}
