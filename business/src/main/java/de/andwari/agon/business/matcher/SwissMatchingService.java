package de.andwari.agon.business.matcher;

import de.andwari.agon.model.event.AgonEvent;
import de.andwari.agon.model.event.Match;
import de.andwari.agon.model.player.Player;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

@Service
public class SwissMatchingService {

    public Map<Pair<Long, Long>, Integer> getSwissMatchings(AgonEvent event) {
        var mapOfPoints = createMapOfPoints(event);
        var playedPairs = event.getRounds().stream()
                .flatMap(r -> r.getMatches().stream())
                .map(PlayersPair::new)
                .toList();
        var playerIds = new ArrayList<>(event.getPlayers().stream()
                .map(Player::getId)
                .toList());
        if (playerIds.size() % 2 == 1) {
            playerIds.add(-1L);
        }
        var pairs = new HashMap<Pair<Long, Long>, Integer>();
        for (int x = 0; x < playerIds.size(); x++) {
            for (int y = x + 1; y < playerIds.size(); y++) {
                var pair = new PlayersPair(playerIds.get(x), playerIds.get(y));
                if (!playedPairs.contains(pair)) {
                    pairs.put(new ImmutablePair<>(pair.p1, pair.p2),
                            calculatePointsDifference(pair, mapOfPoints)
                    );
                }
            }
        }
        return pairs;
    }

    private Integer calculatePointsDifference(PlayersPair pair, Map<Long, Integer> mapOfPoints) {
        return abs(mapOfPoints.get(pair.p1) - mapOfPoints.get(pair.p2));
    }

    private Map<Long, Integer> createMapOfPoints(AgonEvent event) {
        var mapOfPlayers = event.getPlayers().stream()
                .filter(p -> !p.isDropped())
                .collect(Collectors
                        .toMap(Player::getId, p -> p.getStanding().getScore()));
        if (mapOfPlayers.size() % 2 == 1) {
            mapOfPlayers.put(-1L, 0);
        }
        return mapOfPlayers;
    }

    private class PlayersPair {

        Long p1;
        Long p2;

        public PlayersPair(Match match) {
            this.p1 = match.getPlayer1().getId();
            this.p2 = match.getPlayer2().getId();
        }

        public PlayersPair(Long p1, Long p2) {
            this.p1 = p1;
            this.p2 = p2;
        }

        @Override
        public boolean equals(Object obj) {
            boolean result = false;
            if (!(obj instanceof PlayersPair other)) {
                return false;
            }
            result = (Objects.equals(this.p1, other.p1) && Objects.equals(this.p2, other.p2)) ||
                    (Objects.equals(this.p2, other.p1) && Objects.equals(this.p1, other.p2));
            return result;
        }
    }


}
