package de.andwari.agon.business.service;

import de.andwari.agon.model.event.AgonEvent;
import de.andwari.agon.model.event.Match;
import de.andwari.agon.model.event.Result;
import de.andwari.agon.model.event.Round;
import de.andwari.agon.model.player.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final ScoreCalculator scoreCalculator;
    private final FinderService finderService;
    private final StandingService standingService;

    public Match findMatchById(Long id, AgonEvent event) {
        return event.getRounds()
                .stream()
                .map(Round::getMatches)
                .flatMap(Collection::stream)
                .filter(match -> match.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Could not find Match with ID " + id));
    }

    public void updateStandingForPlayer(AgonEvent event, Player player) {
        var playedMatches = finderService.findPlayerMatches(event, player);
        var matchesWon = scoreCalculator.matchesWon(playedMatches, player);
        var matchesDraw = scoreCalculator.matchesDraw(playedMatches, player);
        var standing = standingService.findStandingForPlayer(event, player);
        standing.setScore(3 * matchesWon + matchesDraw);
        standing.setStandingString(buildStandingString(
                matchesWon,
                matchesDraw,
                scoreCalculator.matchesLost(playedMatches, player))
        );
        standing.setOpponentGameWinPercentage(scoreCalculator.calculateOppMWP(event, playedMatches, player));
        standing.setGameWinPercentage(scoreCalculator.calculateGWP(playedMatches, player));
        standing.setOpponentMatchWinPercentage(scoreCalculator.calculateOppGWP(event, playedMatches, player));
    }

    private String buildStandingString(int matchesWon, int matchesDraw, int matchesLost) {
        return matchesWon + "-" + matchesDraw + "-" + matchesLost;
    }

    public Result getResult(Integer w1, Integer w2) {
        if (w1 == 0 && w2 == 0)
            return Result.G00;
        else if (w1 == 1 && w2 == 0)
            return Result.G10;
        else if (w1 == 2 && w2 == 0)
            return Result.G20;
        else if (w1 == 0 && w2 == 1)
            return Result.G01;
        else if (w1 == 0 && w2 == 2)
            return Result.G02;
        else if (w1 == 1 && w2 == 1)
            return Result.G11;
        else if (w1 == 2 && w2 == 1)
            return Result.G12;
        else
            return Result.G12;
    }

    public void updateMatch(Match match, Integer winsPlayer1, Integer winsPlayer2) {
        match.setResult(getResult(winsPlayer1, winsPlayer2));
    }
}
