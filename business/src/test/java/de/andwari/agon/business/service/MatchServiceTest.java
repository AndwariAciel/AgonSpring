package de.andwari.agon.business.service;

import de.andwari.agon.business.factory.TestDataFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MatchServiceTest {

    @Mock
    ScoreCalculator calculator;
    @Mock
    FinderService finder;

    @InjectMocks
    MatchService matchService;

    @Test
    void testScoreUpdateForPlayer1() {
        var event = TestDataFactory.createEvent();
        var matches = TestDataFactory.createMatchesForPlayer1();
        var player = TestDataFactory.getPlayer("player1");

        when(finder.findPlayerMatches(any(), any())).thenReturn(matches);
        when(calculator.matchesWon(any(), any())).thenReturn(5);
        when(calculator.matchesDraw(any(), any())).thenReturn(3);
        when(calculator.matchesLost(any(), any())).thenReturn(1);
        when(calculator.calculateGWP(any(), any())).thenReturn(BigDecimal.valueOf(0.7));
        when(calculator.calculateOppMWP(any(), any(), any())).thenReturn(BigDecimal.valueOf(0.6));
        when(calculator.calculateOppGWP(any(), any(), any())).thenReturn(BigDecimal.valueOf(0.5));

        matchService.updateStandingForPlayer(event, player);

        assertThat(player.getStanding().getScore()).isEqualTo(18);
        assertThat(player.getStanding().getStandingString()).isEqualTo("5-3-1");
        assertThat(player.getStanding().getGameWinPercentage()).isEqualTo(BigDecimal.valueOf(0.7));
        assertThat(player.getStanding().getOpponentGameWinPercentage()).isEqualTo(BigDecimal.valueOf(0.6));
        assertThat(player.getStanding().getOpponentMatchWinPercentage()).isEqualTo(BigDecimal.valueOf(0.5));
    }

}