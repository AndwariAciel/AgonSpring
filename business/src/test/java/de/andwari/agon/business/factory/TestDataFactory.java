package de.andwari.agon.business.factory;

import de.andwari.agon.model.event.*;
import de.andwari.agon.model.player.Player;

import java.util.List;

public class TestDataFactory {

    public static AgonEvent createEvent() {
        return AgonEvent.builder()
                .rounds(List.of(
                        Round.builder()
                                .open(false)
                                .matches(List.of(
                                        Match.builder()
                                                .player1(getPlayer("player1"))
                                                .player2(getPlayer("player2"))
                                                .result(Result.G21)
                                                .build(),
                                        Match.builder()
                                                .player1(getPlayer("player3"))
                                                .player2(getPlayer("player4"))
                                                .result(Result.G12)
                                                .build(),
                                        Match.builder()
                                                .player1(getPlayer("player5"))
                                                .player2(getPlayer("player6"))
                                                .result(Result.G11)
                                                .build()
                                ))
                                .build(),
                        Round.builder()
                                .open(false)
                                .matches(List.of(
                                        Match.builder()
                                                .player1(getPlayer("player4"))
                                                .player2(getPlayer("player1"))
                                                .result(Result.G02)
                                                .build(),
                                        Match.builder()
                                                .player1(getPlayer("player5"))
                                                .player2(getPlayer("player3"))
                                                .result(Result.G21)
                                                .build(),
                                        Match.builder()
                                                .player1(getPlayer("player2"))
                                                .player2(getPlayer("player6"))
                                                .result(Result.G21)
                                                .build()
                                ))
                                .build(),
                        Round.builder()
                                .open(false)
                                .matches(List.of(
                                        Match.builder()
                                                .player1(getPlayer("player1"))
                                                .player2(getPlayer("player5"))
                                                .result(Result.G02)
                                                .build(),
                                        Match.builder()
                                                .player1(getPlayer("player2"))
                                                .player2(getPlayer("player4"))
                                                .result(Result.G21)
                                                .build(),
                                        Match.builder()
                                                .player1(getPlayer("player3"))
                                                .player2(getPlayer("player6"))
                                                .result(Result.G12)
                                                .build()
                                ))
                                .build()
                ))
                .players(List.of(
                        getPlayer("player1"),
                        getPlayer("player2"),
                        getPlayer("player3"),
                        getPlayer("player4"),
                        getPlayer("player5"),
                        getPlayer("player6")
                ))
                .build();
    }

    public static List<Match> createMatchesForPlayer5() {
        return List.of(
                Match.builder()
                        .player1(getPlayer("player5"))
                        .player2(getPlayer("player6"))
                        .result(Result.G11)
                        .build(),
                Match.builder()
                        .player1(getPlayer("player5"))
                        .player2(getPlayer("player3"))
                        .result(Result.G21)
                        .build(),
                Match.builder()
                        .player1(getPlayer("player1"))
                        .player2(getPlayer("player5"))
                        .result(Result.G02)
                        .build()
        );
    }

    public static Player getPlayer(String name) {
        return Player.builder()
                .dci("DCI-" + name)
                .name(name)
                .member(true)
                .build();
    }

    public static List<Match> createMatchesForPlayer1() {
        return List.of(
                Match.builder()
                        .player1(getPlayer("player1"))
                        .player2(getPlayer("player2"))
                        .result(Result.G21)
                        .build(),
                Match.builder()
                        .player1(getPlayer("player4"))
                        .player2(getPlayer("player1"))
                        .result(Result.G02)
                        .build(),
                Match.builder()
                        .player1(getPlayer("player1"))
                        .player2(getPlayer("player5"))
                        .result(Result.G02)
                        .build()
        );
    }

    public static List<Match> createMatchesForPlayer3() {
        return List.of(
                Match.builder()
                        .player1(getPlayer("player3"))
                        .player2(getPlayer("player4"))
                        .result(Result.G12)
                        .build(),
                Match.builder()
                        .player1(getPlayer("player5"))
                        .player2(getPlayer("player3"))
                        .result(Result.G21)
                        .build(),
                Match.builder()
                        .player1(getPlayer("player3"))
                        .player2(getPlayer("player6"))
                        .result(Result.G12)
                        .build()
        );
    }
}
