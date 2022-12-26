package de.andwari.agon.business.matcher;

import de.andwari.agon.business.matcher.model.PointsPair;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class GroupManager {

    public TreeMap<Integer, List<Integer>> convertPointsToGroups(List<PointsPair> playersWithPoints) {
        List<Integer> points = playersWithPoints.stream()
                .map(PointsPair::getPoints)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        var groups = new TreeMap<Integer, List<Integer>>();
        for(int i = 0; i < points.size(); i++) {
            int x = i;
            List<Integer> players = playersWithPoints.stream()
                    .filter(pp -> pp.getPoints().equals(points.get(x)))
                    .map(PointsPair::getPlayer)
                    .collect(Collectors.toList());
            groups.put(i, players);
        }
        return groups;
    }

}
