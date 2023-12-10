package de.andwari.agon.business.service;

import de.andwari.agon.business.mapper.RoundMapper;
import de.andwari.agon.business.matcher.model.MatchPair;
import de.andwari.agon.core.repository.RoundRepository;
import de.andwari.agon.model.event.AgonEvent;
import de.andwari.agon.model.event.Match;
import de.andwari.agon.model.event.Round;
import de.andwari.agon.model.player.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static de.andwari.agon.model.event.Result.DEFAULT;
import static java.lang.Math.ceil;
import static java.lang.Math.log;

@RequiredArgsConstructor
@Service
public class RoundService {

    private final RoundRepository roundRepository;
    private final RoundMapper roundMapper;

    public Round saveRound(Round round, Long eventId) {
        //TODO: database
//        return roundMapper.toModel(
//                roundRepository.save(roundMapper.toEntity(round, eventId)));
        return round;
    }

    public int getMaxNumberOfRounds(int numberOfPlayers) {
        return (int) ceil(log(numberOfPlayers) / log(2));
    }

    public Round createNextRound(List<MatchPair> matches, AgonEvent event) {
        Optional<Player> possibleBye = findPossibleBye(matches, event.getPlayers());
        var round = Round.builder()
                .matches(new ArrayList<>())
                .eventId(event.getId())
                .bye(possibleBye.orElse(null))
                .open(true).build();
        matches.forEach(match -> {
            var player1 = findPlayer(event.getPlayers(), match.getPlayer1());
            var player2 = findPlayer(event.getPlayers(), match.getPlayer2());
            round.getMatches().add(Match.builder()
                    .player1(player1)
                    .player2(player2)
                    .result(DEFAULT)
                    .build());
        });
        return round;
    }

    private Optional<Player> findPossibleBye(List<MatchPair> matches, List<Player> players) {
        Optional<MatchPair> byeMatch = matches.stream()
                .filter(m -> m.getPlayer1().equals(-1) || m.getPlayer2().equals(-1))
                .findFirst();
        if (byeMatch.isPresent()) {
            matches.remove(byeMatch.get());
            long bye;
            if (byeMatch.get().getPlayer1().equals(-1))
                bye = byeMatch.get().getPlayer2();
            else
                bye = byeMatch.get().getPlayer1();
            return Optional.of(findPlayer(players, bye));
        }
        return Optional.empty();
    }

    private Player findPlayer(List<Player> players, Long player) {
        return players.stream().filter(p -> p.getId().equals(player)).findFirst().orElseThrow(
                () -> new IllegalStateException("No player found for ID: " + player)
        );
    }
}
