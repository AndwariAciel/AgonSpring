package de.andwari.agon.business.service;

import de.andwari.agon.model.event.AgonEvent;
import de.andwari.agon.model.event.Standing;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class StandingService {

    public Standing findStandingForPlayer(AgonEvent event, Long playerId) {
        return event.getStandings().stream()
                .filter(
                        standing -> standing.getPlayerId().equals(playerId)
                )
                .findFirst()
                .orElseThrow(
                        () -> new NoSuchElementException("No standing found for Player " + playerId)
                );
    }

}
