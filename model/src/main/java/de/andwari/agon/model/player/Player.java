package de.andwari.agon.model.player;

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

}
