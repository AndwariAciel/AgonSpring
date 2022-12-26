package de.andwari.agon.business.factory;

import de.andwari.agon.business.matcher.model.MatchPair;
import de.andwari.agon.business.matcher.model.PointsPair;
import de.andwari.agon.model.event.*;
import de.andwari.agon.model.player.Player;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

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

    public static List<PointsPair> createPointsAndPlayers() {
        var points = new ArrayList<PointsPair>();
        points.add(PointsPair.builder()
                .points(6)
                .player(0)
                .build());
        points.add(PointsPair.builder()
                .points(6)
                .player(3)
                .build());
        points.add(PointsPair.builder()
                .points(4)
                .player(5)
                .build());
        points.add(PointsPair.builder()
                .points(3)
                .player(1)
                .build());
        points.add(PointsPair.builder()
                .points(3)
                .player(2)
                .build());
        points.add(PointsPair.builder()
                .points(0)
                .player(4)
                .build());
        return points;
    }

    public static Map<Integer, List<Integer>> createGroups() {
        var map = new TreeMap<Integer, List<Integer>>();
        map.put(0, Arrays.asList(2,4,6,8, 9));
        map.put(1, Arrays.asList(20,40,60, 80));
        map.put(2, List.of(-1));
        return map;
    }

    public static List<MatchPair> createPlayedMatches() {
        return Arrays.asList(
                MatchPair.builder().player1(2).player2(20).build(),
                MatchPair.builder().player1(4).player2(40).build(),
                MatchPair.builder().player1(6).player2(60).build(),
                MatchPair.builder().player1(8).player2(80).build()
        );
    }
}
