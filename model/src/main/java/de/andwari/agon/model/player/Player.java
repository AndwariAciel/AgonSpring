package de.andwari.agon.model.player;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Player {

    String name;
    String dci;
    Boolean member;

}
