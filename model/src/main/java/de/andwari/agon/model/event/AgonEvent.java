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

    private Long id;
    private Integer currentRound;
    private LocalDate date;
    private String eventName;
    private List<Player> players;
    private List<Round> rounds;
    private Boolean ranked;

}
