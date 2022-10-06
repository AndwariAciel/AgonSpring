package de.andwari.agon.business.service;

import de.andwari.agon.model.event.AgonEvent;
import de.andwari.agon.model.event.Match;
import de.andwari.agon.model.player.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static de.andwari.agon.model.event.Result.*;

@Service
@RequiredArgsConstructor
public class ScoreCalculator {

    private static final BigDecimal ONE_THIRD = BigDecimal.valueOf(0.333333);
    private final FinderService finderService;

    public BigDecimal calculateOppGWP(AgonEvent event, List<Match> playedMatches, Player player) {
        var opponents = finderService.findOpponents(playedMatches, player);
        var sumOfGWP = opponents.stream().map(opp -> {
            var playedOppMatches = finderService.findPlayerMatches(event, opp);
            return calculateGWP(playedOppMatches, opp);
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
        if(opponents.isEmpty())
            return BigDecimal.ZERO;
        return sumOfGWP.divide(BigDecimal.valueOf(opponents.size()), RoundingMode.HALF_UP);
    }

    public BigDecimal calculateOppMWP(AgonEvent event, List<Match> playedMatches, Player player) {
        var opponents = finderService.findOpponents(playedMatches, player);
        if(opponents.isEmpty())
            return BigDecimal.ZERO;
        var sumOfMWP = opponents.stream().map(opp -> {
            var playedOppMatches = finderService.findPlayerMatches(event, opp);
            var matchesWon = matchesWon(playedOppMatches, opp);
            return getPercentage(matchesWon, playedOppMatches.size());
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
        return sumOfMWP.divide(getBigDecimal(opponents.size()), RoundingMode.HALF_UP);
    }

    public BigDecimal calculateGWP(List<Match> playedMatches, Player player) {
        var wonGames = playedMatches.stream().mapToInt(match -> {
            if (match.getPlayer1().equals(player))
                return match.getResult().p1;
            return match.getResult().p2;
        }).sum();
        var allGames = playedMatches.stream().mapToInt(match ->
                match.getResult().p1 + match.getResult().p2
        ).sum();
        return getPercentage(wonGames, allGames);
    }

    private BigDecimal getPercentage(int dividend, int divisor) {
        if(divisor == 0)
            return ONE_THIRD;
        var percentage = getBigDecimal(dividend).divide(
                getBigDecimal(divisor),
                RoundingMode.HALF_UP);
        if (percentage.compareTo(ONE_THIRD) < 0)
            return ONE_THIRD;
        return percentage;
    }

    public int matchesWon(List<Match> matches, Player player) {
        return (int) matches.stream().filter(match -> getMatchResult(match, player) == 1).count();
    }

    public int matchesDraw(List<Match> matches, Player player) {
        return (int) matches.stream().filter(match -> getMatchResult(match, player) == 0).count();
    }

    public int matchesLost(List<Match> matches, Player player) {
        return (int) matches.stream().filter(match -> getMatchResult(match, player) == -1).count();
    }

    private int getMatchResult(Match match, Player player) {
        if (match.getPlayer1().equals(player)) {
            if (match.getResult() == G10 || match.getResult() == G20 || match.getResult() == G21)
                return 1;
            else if (match.getResult() == G00 || match.getResult() == G11)
                return 0;
            else
                return -1;
        }
        if (match.getResult() == G01 || match.getResult() == G02 || match.getResult() == G12)
            return 1;
        else if (match.getResult() == G00 || match.getResult() == G11)
            return 0;
        else
            return -1;
    }

    private BigDecimal getBigDecimal(int value) {
        return BigDecimal.valueOf(value).setScale(6, RoundingMode.HALF_UP);
    }
}
