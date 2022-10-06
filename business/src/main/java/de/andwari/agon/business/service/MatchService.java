package de.andwari.agon.business.service;

import de.andwari.agon.model.event.AgonEvent;
import de.andwari.agon.model.player.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final ScoreCalculator scoreCalculator;
    private final FinderService finderService;

    public void updateStandingForPlayer(AgonEvent event, Player player) {
        var playedMatches = finderService.findPlayerMatches(event, player);
        var matchesWon = scoreCalculator.matchesWon(playedMatches, player);
        var matchesDraw = scoreCalculator.matchesDraw(playedMatches, player);
        player.getStanding().setScore(3 * matchesWon + matchesDraw);
        player.getStanding().setStandingString(buildStandingString(
                matchesWon,
                matchesDraw,
                scoreCalculator.matchesLost(playedMatches, player))
        );
        player.getStanding().setOpponentGameWinPercentage(scoreCalculator.calculateOppMWP(event, playedMatches, player));
        player.getStanding().setGameWinPercentage(scoreCalculator.calculateGWP(playedMatches, player));
        player.getStanding().setOpponentMatchWinPercentage(scoreCalculator.calculateOppGWP(event, playedMatches, player));
    }

    private String buildStandingString(int matchesWon, int matchesDraw, int matchesLost) {
        return matchesWon + "-" + matchesDraw + "-" + matchesLost;
    }

}
