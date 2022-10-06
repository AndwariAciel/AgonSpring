package de.andwari.agon.model.event;

import de.andwari.agon.model.player.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Match {

    Long id;
    Player player1;
    Player player2;
    Result result;
}
