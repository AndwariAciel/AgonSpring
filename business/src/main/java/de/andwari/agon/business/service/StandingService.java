package de.andwari.agon.business.service;

import de.andwari.agon.model.event.AgonEvent;
import de.andwari.agon.model.event.Standing;
import de.andwari.agon.model.player.Player;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class StandingService {

    public Standing findStandingForPlayer(AgonEvent event, Player player) {
        return event.getStandings().stream()
                .filter(
                        standing -> standing.getPlayerId().equals(player.getId())
                )
                .findFirst()
                .orElseThrow(
                        () -> new NoSuchElementException("No standing found for Player " + player.getId())
                );
    }

}
