package de.andwari.agon.business.service;

import de.andwari.agon.business.factory.TestDataFactory;
import de.andwari.agon.model.event.Match;
import de.andwari.agon.model.player.Player;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FinderServiceTest {

    private final FinderService finderService = new FinderService();

    @Test
    void testFindMatches() {
        var event = TestDataFactory.createEvent();
        var player1 = TestDataFactory.getPlayer("player1");

        List<Match> playerMatches = finderService.findPlayerMatches(event, player1);

        assertEquals(playerMatches.size(), 3);
        assertEquals(playerMatches.get(0).getPlayer2().getName(),"player2");
        assertEquals(playerMatches.get(1).getPlayer1().getName(),"player4");
        assertEquals(playerMatches.get(2).getPlayer2().getName(),"player5");
    }

    @Test
    void testFindMatchesEmptyList() {
        var event = TestDataFactory.createEvent();
        var player7 = TestDataFactory.getPlayer("player7");

        List<Match> playerMatches = finderService.findPlayerMatches(event, player7);

        assertEquals(playerMatches.size(), 0);
    }

    @Test
    void testFindOpponents() {
        var event = TestDataFactory.createEvent();
        var player1 = TestDataFactory.getPlayer("player1");
        List<Match> playerMatches = finderService.findPlayerMatches(event, player1);

        List<Player> opponents = finderService.findOpponents(playerMatches, player1);

        assertEquals(opponents.size(), 3);
        assertEquals(opponents.get(0).getName(), "player2");
        assertEquals(opponents.get(1).getName(), "player4");
        assertEquals(opponents.get(2).getName(), "player5");
    }

    @Test
    void testFindOpponentsEmptyList() {
        var player7 = TestDataFactory.getPlayer("player7");

        List<Player> opponents = finderService.findOpponents(List.of(), player7);

        assertEquals(opponents.size(), 0);
    }

}