package de.andwari.agon.business.service;

import de.andwari.agon.model.event.AgonEvent;
import de.andwari.agon.model.event.Match;
import de.andwari.agon.model.event.Round;
import de.andwari.agon.model.player.Player;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FinderService {

    public List<Match> findPlayerMatches(AgonEvent event, Player player) {
        return event.getRounds().stream()
                .map(Round::getMatches)
                .flatMap(Collection::stream)
                .filter(match ->
                        match.getPlayer1().equals(player) || match.getPlayer2().equals(player)
                ).collect(Collectors.toList());
    }

    public List<Player> findOpponents(List<Match> playedMatches, Player player) {
        return playedMatches.stream().map(m -> {
            if (m.getPlayer1().equals(player))
                return m.getPlayer2();
            return m.getPlayer1();
        }).collect(Collectors.toList());
    }
}
