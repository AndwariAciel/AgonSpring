package de.andwari.agon.business.matcher;

import de.andwari.agon.business.matcher.model.MatchPair;
import de.andwari.agon.business.player.PlayerService;
import de.andwari.agon.model.event.AgonEvent;
import de.andwari.agon.model.player.Player;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SwissMatcher {

    private final Matcher matcher;
    private final SwissMatchingService matchingService;
    private final PlayerService playerService;

    public List<MatchPair> getMatchings(AgonEvent event) {
        var allPossibleMatchings = matchingService.getSwissMatchings(event);
        var players = playerService.getActivePlayerIds(event);
        return matcher.getMatches(players, allPossibleMatchings);
    }

}

