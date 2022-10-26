package de.andwari.agon.model.event;

import de.andwari.agon.model.player.Player;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PlayerMatchInfo {
    private Player player;
    private List<Player> opponents;
    private List<Match> playedMatches;
}
