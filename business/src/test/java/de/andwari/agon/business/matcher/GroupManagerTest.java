package de.andwari.agon.business.matcher;

import de.andwari.agon.business.factory.TestDataFactory;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;


class GroupManagerTest {

    private final GroupManager groupManager = new GroupManager();

    @Test
    void testGrouping() {
        TreeMap<Integer, List<Integer>> groups = groupManager.convertPointsToGroups(TestDataFactory.createPointsAndPlayers());
        assertThat(groups).hasSize(4);
        assertThat(groups.get(0)).contains(0);
        assertThat(groups.get(0)).contains(3);
        assertThat(groups.get(1)).contains(5);
        assertThat(groups.get(2)).contains(1);
        assertThat(groups.get(2)).contains(2);
        assertThat(groups.get(3)).contains(4);
    }

}