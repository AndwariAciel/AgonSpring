package de.andwari.agon.business.service;

import de.andwari.agon.business.factory.TestDataFactory;
import de.andwari.agon.model.event.Standing;
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
    @Mock
    StandingService standingService;

    @InjectMocks
    MatchService matchService;

    @Test
    void testScoreUpdateForPlayer1() {
        var event = TestDataFactory.createEvent();
        var matches = TestDataFactory.createMatchesForPlayer1();
        var player = TestDataFactory.getPlayer("player1");
        var standing = Standing.builder().build();

        when(finder.findPlayerMatches(any(), any())).thenReturn(matches);
        when(calculator.matchesWon(any(), any())).thenReturn(5);
        when(calculator.matchesDraw(any(), any())).thenReturn(3);
        when(calculator.matchesLost(any(), any())).thenReturn(1);
        when(calculator.calculateGWP(any(), any())).thenReturn(BigDecimal.valueOf(0.7));
        when(calculator.calculateOppMWP(any(), any(), any())).thenReturn(BigDecimal.valueOf(0.6));
        when(calculator.calculateOppGWP(any(), any(), any())).thenReturn(BigDecimal.valueOf(0.5));
        when(standingService.findStandingForPlayer(any(), any())).thenReturn(standing);

        matchService.updateStandingForPlayer(event, player);

        assertThat(standing.getScore()).isEqualTo(18);
        assertThat(standing.getStandingString()).isEqualTo("5-3-1");
        assertThat(standing.getGameWinPercentage()).isEqualTo(BigDecimal.valueOf(0.7));
        assertThat(standing.getOpponentGameWinPercentage()).isEqualTo(BigDecimal.valueOf(0.6));
        assertThat(standing.getOpponentMatchWinPercentage()).isEqualTo(BigDecimal.valueOf(0.5));
    }

}