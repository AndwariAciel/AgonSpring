package de.andwari.agon.model.event;

import de.andwari.agon.model.player.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Round {

    List<Match> matches;
    Boolean open;
    Player bye;
}
