package de.andwari.agon.business.player;

import de.andwari.agon.model.event.AgonEvent;
import de.andwari.agon.model.player.Player;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EventService {

    public AgonEvent createNewEvent(List<Player> players, String eventName, boolean ranked) {
        return AgonEvent.builder().eventName(eventName).currentRound(0)
                .date(LocalDate.now()).players(players).ranked(ranked)
                .build();
    }

}
