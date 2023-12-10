package de.andwari.agon.business.matcher;

import de.andwari.agon.business.matcher.model.MatchPair;
import de.andwari.agon.model.event.AgonEvent;
import de.andwari.agon.model.player.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class SwissMatcher {

    private final Matcher matcher;
    private final SwissMatchingService matchingService;

    public List<MatchPair> getMatchings(AgonEvent event) {
        var allPossibleMatchings = matchingService.getSwissMatchings(event);
        var players = event.getPlayers().stream().map(Player::getId).toList();
        return matcher.getMatches(players, allPossibleMatchings);
    }

}

