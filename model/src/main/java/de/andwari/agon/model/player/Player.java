package de.andwari.agon.model.player;

import de.andwari.agon.model.event.Standing;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Player {

    Long id;
    String name;
    String dci;
    Boolean member;
    Standing standing;

}
