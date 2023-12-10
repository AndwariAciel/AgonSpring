package de.andwari.agon.business.service;

import de.andwari.agon.business.factory.TestDataFactory;
import de.andwari.agon.model.event.Match;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
class ScoreCalculatorTest {

    @Mock
    FinderService finderService;

    @InjectMocks
    ScoreCalculator scoreCalculator;

    @Test
    void testMatchesWonP1() {
        List<Match> matches = TestDataFactory.createMatchesForPlayer1();
        var player = TestDataFactory.getPlayer(1);
        int matchesWon = scoreCalculator.matchesWon(matches, player);

        assertThat(matchesWon).isEqualTo(2);
    }

    @Test
    void testMatchesWonP3() {
        List<Match> matches = TestDataFactory.createMatchesForPlayer3();
        var player = TestDataFactory.getPlayer(3);
        int matchesWon = scoreCalculator.matchesWon(matches, player);

        assertThat(matchesWon).isEqualTo(0);
    }

    @Test
    void testMatchesWonP5() {
        List<Match> matches = TestDataFactory.createMatchesForPlayer5();
        var player = TestDataFactory.getPlayer(5);
        int matchesWon = scoreCalculator.matchesWon(matches, player);

        assertThat(matchesWon).isEqualTo(2);
    }

    @Test
    void testMatchesDraw() {
        List<Match> matches = TestDataFactory.createMatchesForPlayer5();
        var player = TestDataFactory.getPlayer(5);
        int matchesWon = scoreCalculator.matchesDraw(matches, player);

        assertThat(matchesWon).isEqualTo(1);
    }

    @Test
    void testMatchesLost() {
        List<Match> matches = TestDataFactory.createMatchesForPlayer5();
        var player = TestDataFactory.getPlayer(5);
        int matchesWon = scoreCalculator.matchesLost(matches, player);

        assertThat(matchesWon).isEqualTo(0);
    }

    @Test
    void testOppGWP() {
        var event = TestDataFactory.createEvent();
        var matches = TestDataFactory.createMatchesForPlayer5();
        var player = TestDataFactory.getPlayer(5);

        Mockito.when(finderService.findOpponents(Mockito.any(), Mockito.any())).thenCallRealMethod();
        Mockito.when(finderService.findPlayerMatches(Mockito.any(), Mockito.any())).thenCallRealMethod();

        BigDecimal oppGWP = scoreCalculator.calculateOppMWP(event, matches, player);
        MatcherAssert.assertThat(oppGWP, is(
                closeTo(BigDecimal.valueOf(0.444444), BigDecimal.valueOf(0.00001))));
    }

    @Test
    void testOppGWPForPlayer1() {
        var event = TestDataFactory.createEvent();
        var matches = TestDataFactory.createMatchesForPlayer1();
        var player = TestDataFactory.getPlayer(1);

        Mockito.when(finderService.findOpponents(Mockito.any(), Mockito.any())).thenCallRealMethod();
        Mockito.when(finderService.findPlayerMatches(Mockito.any(), Mockito.any())).thenCallRealMethod();

        BigDecimal oppGWP = scoreCalculator.calculateOppMWP(event, matches, player);
        MatcherAssert.assertThat(oppGWP, is(
                closeTo(BigDecimal.valueOf(0.555555), BigDecimal.valueOf(0.00001))));
    }

    @Test
    void testOppGWPWithEmptyList() {
        var event = TestDataFactory.createEvent();
        var player = TestDataFactory.getPlayer(1);

        Mockito.when(finderService.findOpponents(Mockito.any(), Mockito.any())).thenCallRealMethod();

        BigDecimal mwp = scoreCalculator.calculateOppMWP(event, Collections.emptyList(), player);
        MatcherAssert.assertThat(mwp, is(closeTo(BigDecimal.valueOf(0), BigDecimal.valueOf(0.000001))));
    }

    @Test
    void testMWPWithEmptyList() {
        BigDecimal mwp = scoreCalculator.calculateGWP(Collections.emptyList(), TestDataFactory.getPlayer(5));
        MatcherAssert.assertThat(mwp, is(closeTo(BigDecimal.valueOf(0.333333), BigDecimal.valueOf(0.000001))));
    }

    @Test
    void testMWPForPlayer5() {
        var matches = TestDataFactory.createMatchesForPlayer5();
        var player = TestDataFactory.getPlayer(5);

        BigDecimal mwp = scoreCalculator.calculateGWP(matches, player);
        MatcherAssert.assertThat(mwp, is(closeTo(BigDecimal.valueOf(0.714285), BigDecimal.valueOf(0.000001))));
    }

    @Test
    void testMWPForPlayer1() {
        var matches = TestDataFactory.createMatchesForPlayer1();
        var player = TestDataFactory.getPlayer(1);

        BigDecimal mwp = scoreCalculator.calculateGWP(matches, player);
        MatcherAssert.assertThat(mwp, is(closeTo(BigDecimal.valueOf(0.571428), BigDecimal.valueOf(0.000001))));
    }

    @Test
    void testOppMWPForPlayer5() {
        var event = TestDataFactory.createEvent();
        var matches = TestDataFactory.createMatchesForPlayer5();
        var player = TestDataFactory.getPlayer(5);
        Mockito.when(finderService.findOpponents(Mockito.any(), Mockito.any())).thenCallRealMethod();
        Mockito.when(finderService.findPlayerMatches(Mockito.any(), Mockito.any())).thenCallRealMethod();

        BigDecimal mwp = scoreCalculator.calculateOppGWP(event, matches, player);
        MatcherAssert.assertThat(mwp, is(closeTo(BigDecimal.valueOf(0.468253), BigDecimal.valueOf(0.000001))));
    }

    @Test
    void testOppMWPForPlayer1() {
        var event = TestDataFactory.createEvent();
        var matches = TestDataFactory.createMatchesForPlayer1();
        var player = TestDataFactory.getPlayer(1);
        Mockito.when(finderService.findOpponents(Mockito.any(), Mockito.any())).thenCallRealMethod();
        Mockito.when(finderService.findPlayerMatches(Mockito.any(), Mockito.any())).thenCallRealMethod();

        BigDecimal mwp = scoreCalculator.calculateOppGWP(event, matches, player);
        MatcherAssert.assertThat(mwp, is(closeTo(BigDecimal.valueOf(0.548280), BigDecimal.valueOf(0.000001))));

    }
}