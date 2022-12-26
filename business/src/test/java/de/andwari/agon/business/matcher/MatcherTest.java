package de.andwari.agon.business.matcher;

import de.andwari.agon.business.factory.TestDataFactory;
import de.andwari.agon.business.matcher.model.MatchPair;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class MatcherTest {

    private final Matcher matcher = new Matcher();

    @Test
    void testMatching() {
        List<MatchPair> matches = matcher.getMatching(TestDataFactory.createGroups(), TestDataFactory.createPlayedMatches());
        Assertions.assertThat(matches).hasSize(5);
    }

}