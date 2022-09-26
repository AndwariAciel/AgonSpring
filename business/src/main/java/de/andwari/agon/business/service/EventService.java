package de.andwari.agon.business.service;

import de.andwari.agon.model.event.AgonEvent;
import de.andwari.agon.model.event.Match;
import de.andwari.agon.model.event.Round;
import de.andwari.agon.model.player.Player;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class EventService {

    public AgonEvent createNewEvent(List<Player> players, String eventName, boolean ranked) {
        return AgonEvent.builder().eventName(eventName).currentRound(0)
                .date(LocalDate.now()).players(players).ranked(ranked)
                .build();
    }

    public List<Player> createSeatings(AgonEvent event) {
        var seatings = new ArrayList<>(event.getPlayers());
         Collections.shuffle(seatings);
         return seatings;
    }

    public void createCrossPairings(List<Player> seatings, AgonEvent event) {
        // Check if bye
        Player bye = null;
        var tmpSeatings = new ArrayList<>(seatings);
        if(tmpSeatings.size() % 2 == 1) {
            bye = tmpSeatings.get(new Random().nextInt(tmpSeatings.size()) - 1);
            tmpSeatings.remove(bye);
        }
        event.setRounds(new ArrayList<>());
        var round = Round.builder().bye(bye).open(true).matches(new ArrayList<>()).build();
        // seatings size is now even.
        var halfSize = (tmpSeatings.size() / 2);
        for(int i = 0; i < halfSize; i++) {
            round.getMatches().add(
                    Match.builder()
                            .player1(tmpSeatings.get(i))
                            .player2(tmpSeatings.get(i + halfSize))
                            .build()
            );
        }
        event.getRounds().add(round);
    }
}
