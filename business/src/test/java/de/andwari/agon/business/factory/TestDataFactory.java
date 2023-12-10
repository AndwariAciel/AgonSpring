package de.andwari.agon.business.factory;

import de.andwari.agon.core.entity.PlayerEntity;
import de.andwari.agon.model.event.*;
import de.andwari.agon.model.player.Player;

import java.util.*;

public class TestDataFactory {

    public static AgonEvent createEvent() {
        return AgonEvent.builder()
                .rounds(List.of(
                        Round.builder()
                                .open(false)
                                .matches(List.of(
                                        Match.builder()
                                                .player1(getPlayer(1))
                                                .player2(getPlayer(2))
                                                .result(Result.G21)
                                                .build(),
                                        Match.builder()
                                                .player1(getPlayer(3))
                                                .player2(getPlayer(4))
                                                .result(Result.G12)
                                                .build(),
                                        Match.builder()
                                                .player1(getPlayer(5))
                                                .player2(getPlayer(6))
                                                .result(Result.G11)
                                                .build()
                                ))
                                .build(),
                        Round.builder()
                                .open(false)
                                .matches(List.of(
                                        Match.builder()
                                                .player1(getPlayer(4))
                                                .player2(getPlayer(1))
                                                .result(Result.G02)
                                                .build(),
                                        Match.builder()
                                                .player1(getPlayer(5))
                                                .player2(getPlayer(3))
                                                .result(Result.G21)
                                                .build(),
                                        Match.builder()
                                                .player1(getPlayer(2))
                                                .player2(getPlayer(6))
                                                .result(Result.G21)
                                                .build()
                                ))
                                .build(),
                        Round.builder()
                                .open(false)
                                .matches(List.of(
                                        Match.builder()
                                                .player1(getPlayer(1))
                                                .player2(getPlayer(5))
                                                .result(Result.G02)
                                                .build(),
                                        Match.builder()
                                                .player1(getPlayer(2))
                                                .player2(getPlayer(4))
                                                .result(Result.G21)
                                                .build(),
                                        Match.builder()
                                                .player1(getPlayer(3))
                                                .player2(getPlayer(6))
                                                .result(Result.G12)
                                                .build()
                                ))
                                .build()
                ))
                .players(List.of(
                        getPlayer(1),
                        getPlayer(2),
                        getPlayer(3),
                        getPlayer(4),
                        getPlayer(5),
                        getPlayer(6)
                ))
                .build();
    }

    public static List<Match> createMatchesForPlayer5() {
        return List.of(
                Match.builder()
                        .player1(getPlayer(5))
                        .player2(getPlayer(6))
                        .result(Result.G11)
                        .build(),
                Match.builder()
                        .player1(getPlayer(5))
                        .player2(getPlayer(3))
                        .result(Result.G21)
                        .build(),
                Match.builder()
                        .player1(getPlayer(1))
                        .player2(getPlayer(5))
                        .result(Result.G02)
                        .build()
        );
    }

    public static Player getPlayer(long name) {
        return Player.builder()
                .id(name)
                .dci("DCI-" + name)
                .name("player" + name)
                .member(true)
                .standing(Standing.builder().score(3).build())
                .build();
    }

    public static List<Match> createMatchesForPlayer1() {
        return List.of(
                Match.builder()
                        .player1(getPlayer(1))
                        .player2(getPlayer(2))
                        .result(Result.G21)
                        .build(),
                Match.builder()
                        .player1(getPlayer(4))
                        .player2(getPlayer(1))
                        .result(Result.G02)
                        .build(),
                Match.builder()
                        .player1(getPlayer(1))
                        .player2(getPlayer(5))
                        .result(Result.G02)
                        .build()
        );
    }

    public static List<Match> createMatchesForPlayer3() {
        return List.of(
                Match.builder()
                        .player1(getPlayer(3))
                        .player2(getPlayer(4))
                        .result(Result.G12)
                        .build(),
                Match.builder()
                        .player1(getPlayer(5))
                        .player2(getPlayer(3))
                        .result(Result.G21)
                        .build(),
                Match.builder()
                        .player1(getPlayer(3))
                        .player2(getPlayer(6))
                        .result(Result.G12)
                        .build()
        );
    }

    public static List<PlayerEntity> createPlayerListWithoutDci() {
        return Arrays.asList(
                PlayerEntity.builder().name("Friedrich").dci("").build(),
                PlayerEntity.builder().name("Hans").dci("").build()
        );
    }

    public static List<PlayerEntity> createPlayerListWithDci() {
        return Arrays.asList(
                PlayerEntity.builder().name("Hans").dci("123").build()
        );
    }
}
