package de.andwari.agon.business.matcher;

import de.andwari.agon.business.matcher.model.MatchPair;
import org.apache.commons.lang3.tuple.Pair;
import org.jgrapht.Graph;
import org.jgrapht.alg.matching.blossom.v5.KolmogorovWeightedPerfectMatching;
import org.jgrapht.alg.matching.blossom.v5.ObjectiveSense;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
class Matcher {

    public List<MatchPair> getMatches(List<Long> players, Map<Pair<Long, Long>, Integer> possiblePairings) {
        var graph = new SimpleWeightedGraph<Long, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        players.forEach(graph::addVertex);
        possiblePairings.forEach((pair, pointDifference) -> {
            graph.addEdge(pair.getLeft(), pair.getRight());
            graph.setEdgeWeight(pair.getLeft(), pair.getRight(), pointDifference.doubleValue() * 10);
        });

        KolmogorovWeightedPerfectMatching<Long, DefaultWeightedEdge> matching =
                new KolmogorovWeightedPerfectMatching<>(graph, ObjectiveSense.MINIMIZE);
        return createPairings(matching.getMatching().getEdges(), graph);
    }

    private List<MatchPair> createPairings(Set<DefaultWeightedEdge> edges,
                                           Graph<Long, DefaultWeightedEdge> graph) {
        List<MatchPair> pairings = new ArrayList<>();
        for (DefaultWeightedEdge edge : edges) {
            pairings.add(MatchPair.builder().player1(graph.getEdgeSource(edge))
                    .player2(graph.getEdgeTarget(edge))
                    .build()
            );
        }
        return pairings;
    }

}
