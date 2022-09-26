package de.andwari.agon.model.event;

import de.andwari.agon.model.player.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Match {

    Player player1;
    Player player2;
    /*
    0: 0-0
    1: 1-0
    2: 0-1
    3: 1-1
    4: 2-0
    5: 0-2
    6: 2-1
    7: 1-2
     */
    Integer result;
}
