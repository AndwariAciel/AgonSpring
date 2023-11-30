package de.andwari.agon.business.matcher;

import de.andwari.agon.business.matcher.model.MatchPair;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.matching.blossom.v5.KolmogorovWeightedPerfectMatching;
import org.jgrapht.alg.matching.blossom.v5.ObjectiveSense;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.util.SupplierUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

@Service
class Matcher {

    public List<MatchPair> getMatching(Map<Integer, List<Integer>> groups,
                                              List<MatchPair> playedMatches) {
        Graph<Integer, DefaultWeightedEdge> graph = addVerticesAndWeights(groups, playedMatches);

        KolmogorovWeightedPerfectMatching<Integer, DefaultWeightedEdge> matching =
                new KolmogorovWeightedPerfectMatching<>(graph, ObjectiveSense.MINIMIZE);
        return createPairings(matching.getMatching().getEdges(), graph);
    }

    private List<MatchPair> createPairings(Set<DefaultWeightedEdge> edges,
                                                  Graph<Integer, DefaultWeightedEdge> graph) {
        List<MatchPair> pairings = new ArrayList<>();
        for (DefaultWeightedEdge edge : edges) {
            pairings.add(MatchPair.builder().player1(graph.getEdgeSource(edge))
                    .player2(graph.getEdgeTarget(edge))
                    .build()
            );
        }
        return pairings;
    }

    private Graph<Integer, DefaultWeightedEdge> addVerticesAndWeights(Map<Integer, List<Integer>> groups,
                                                                             List<MatchPair> playedMatches) {
        var graph =
                new SimpleWeightedGraph<>(
                        SupplierUtil.createIntegerSupplier(getStart(groups)),
                        SupplierUtil.createDefaultWeightedEdgeSupplier());

        groups.values().stream().flatMap(List::stream).forEach(graph::addVertex);

//        for (int i = 0; i < getNumberOfPlayers(groups); i++) {
//            graph.addVertex();
//        }
        addWeights(graph, groups, playedMatches);
        return graph;
    }

    private int getStart(Map<Integer, List<Integer>> groups) {
        if (groups.get(groups.size() - 1).contains(-1)) {
            return -1;
        }
        return 0;
    }

    private void addWeights(Graph<Integer, DefaultWeightedEdge> graph, Map<Integer, List<Integer>> groups,
                                   List<MatchPair> playedMatches) {
        var unwrappedGroups = unwrapGroups(groups);
        for (int i = 0; i < unwrappedGroups.size(); i++) {
            for (int j = i + 1; j < unwrappedGroups.size(); j++) {
                var player1 = unwrappedGroups.get(i);
                var player2 = unwrappedGroups.get(j);
                if (!containsPair(playedMatches, player1.getLeft(), player2.getLeft())) {
                    Graphs.addEdge(graph, player1.getLeft(), player2.getLeft(),
                            calculateWeight(player1, player2));
                }
            }
        }
    }

    private double calculateWeight(Pair<Integer, Integer> player1, Pair<Integer, Integer> player2) {
        return Math.abs(player1.getRight() - player2.getRight()) * 10.;
    }

    private boolean containsPair(List<MatchPair> playedMatches, Integer player1, Integer player2) {
        return playedMatches.stream().anyMatch(p -> p.getPlayer1().equals(player1) && p.getPlayer2().equals(player2) ||
                p.getPlayer1().equals(player2) && p.getPlayer2().equals(player1));
    }

    private int getNumberOfPlayers(Map<Integer, List<Integer>> groups) {
        return groups.values().stream().flatMapToInt(list -> IntStream.of(list.size())).sum();
    }

    private List<Pair<Integer, Integer>> unwrapGroups(Map<Integer, List<Integer>> groups) {
        List<Pair<Integer, Integer>> list = new ArrayList<>();
        for (Map.Entry<Integer, List<Integer>> entry : groups.entrySet()) {
            for (Integer player : entry.getValue()) {
                list.add(new ImmutablePair<>(player, entry.getKey()));
            }
        }
        return list;
    }
}
