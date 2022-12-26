package de.andwari.agon.business.matcher.mapper;

import de.andwari.agon.business.matcher.model.MatchPair;
import de.andwari.agon.model.event.Match;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchMapper {

    public List<MatchPair> toMatchPair(List<Match> matches) {
        return matches.stream()
                .map(m -> MatchPair.builder().player1(m.getPlayer1().getId().intValue())
                        .player2(m.getPlayer2().getId().intValue())
                        .build())
                .collect(Collectors.toList());
    }

}
