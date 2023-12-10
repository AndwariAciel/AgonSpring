package de.andwari.agon.model.player;

import de.andwari.agon.model.event.Standing;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Player {

    private Long id;
    private String name;
    private String dci;
    private Boolean member;
    private Standing standing;

}
