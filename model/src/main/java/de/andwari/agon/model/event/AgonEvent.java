package de.andwari.agon.model.event;

import de.andwari.agon.model.player.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class AgonEvent {

    Integer currentRound;
    LocalDate date;
    String eventName;
    List<Player> players;
    List<Round> rounds;
    Boolean ranked;

}
