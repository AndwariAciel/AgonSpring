package de.andwari.agon.model.event;

import de.andwari.agon.model.player.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Match {

    private Long id;
    private Player player1;
    private Player player2;
    private Result result;
    private boolean byeMatch;
}
