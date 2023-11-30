package de.andwari.agon.business.matcher;

import de.andwari.agon.business.matcher.mapper.MatchMapper;
import de.andwari.agon.business.matcher.model.MatchPair;
import de.andwari.agon.business.matcher.model.PointsPair;
import de.andwari.agon.model.event.AgonEvent;
import de.andwari.agon.model.event.Match;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
@Component
public class SwissMatcher {

    private Map<Integer, List<Integer>> groups;
    private List<MatchPair> playedMatches;

    private final GroupManager groupManager;
    private final Matcher matcher;
    private final MatchMapper mapper;

    /**
     * Initialize this matcher after the first round by providing all played matches and players with points.
     *
     * @param playedMatches    a list of all played matches
     */
    public void init(List<Match> playedMatches) {
        this.playedMatches = mapper.toMatchPair(playedMatches);
        this.groups = new TreeMap<>();
    }

    /**
     * Update the groups by providing current points and players.
     *
     * @param playerWithPoints current players with points
     */
    public void update(List<PointsPair> playerWithPoints) {
        groups = groupManager.convertPointsToGroups(playerWithPoints);
    }

    /**
     * Add a player to a specific group. Use {@link GroupManager} to figure out the groups based on the points.
     *
     * @param group  the group where the player is to be added
     * @param player the player that should be added
     */
    private void addPlayerToGroup(int group, int player) {
        if (isNull(groups.get(group))) {
            groups.put(group, new ArrayList<>());
        }
        groups.get(group).add(player);
        Collections.shuffle(groups.get(group));
    }

    /**
     * @return Returns a list of matches based on the best possible combinations.
     */
    public List<MatchPair> getMatching() {
        verify();
        handleBye();
        var matches = matcher.getMatching(groups, playedMatches);
        playedMatches.addAll(matches);
        groups.clear();
        return matches;
    }

    private void verify() {
        var players = groups.values().stream().flatMap(List::stream).collect(Collectors.toList());
        if (players.stream().mapToInt(Integer::intValue).min().orElse(0) < 0) {
            throw new IllegalArgumentException("No negative player numbers allowed");
        }
        if (players.stream().anyMatch(i -> Collections.frequency(players, i) > 1)) {
            throw new IllegalArgumentException("Some players are duplicated");
        }
    }

    /**
     * If there is an uneven number of players a BYE is added.
     * It has always identifier -1 and it will always be added after the highest group.
     **/
    private void handleBye() {
        long size = groups.values().stream().mapToLong(List::size).sum();
        if (size % 2 == 1) {
            var maxGroup = groups.keySet().stream().mapToInt(Integer::intValue).max();
            maxGroup.ifPresent(i -> addPlayerToGroup(i + 1, -1));
        }
    }

    public void getMatchings(AgonEvent event) {

    }

}

