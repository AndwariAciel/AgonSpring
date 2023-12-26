package de.andwari.agon.business.service;

import static de.andwari.agon.business.service.ByeService.BYE_ID;
import static de.andwari.agon.model.event.Result.BYE;
import static de.andwari.agon.model.event.Result.DEFAULT;
import static java.lang.Math.ceil;
import static java.lang.Math.log;

import de.andwari.agon.business.mapper.RoundMapper;
import de.andwari.agon.business.matcher.model.MatchPair;
import de.andwari.agon.business.player.PlayerService;
import de.andwari.agon.core.repository.RoundRepository;
import de.andwari.agon.model.event.AgonEvent;
import de.andwari.agon.model.event.Match;
import de.andwari.agon.model.event.Round;
import de.andwari.agon.model.player.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoundService {

    private final RoundRepository roundRepository;
    private final RoundMapper roundMapper;
    private final MatchService matchService;
    private final ByeService byeService;

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
        var round = Round.builder()
                .matches(new ArrayList<>())
                .eventId(event.getId())
                .bye(findPossibleBye(matches, event.getPlayers()).orElse(null))
                .open(true).build();
        matches.forEach(matchPair -> {
            var player1 = findPlayer(event.getPlayers(), matchPair.getPlayer1());
            var player2 = findPlayer(event.getPlayers(), matchPair.getPlayer2());
            var match = Match.builder()
                    .player1(player1)
                    .player2(player2)
                    .result(player2.getId().equals(BYE_ID) ? BYE : DEFAULT)
                    .byeMatch(player2.getId().equals(BYE_ID))
                    .build();
            match.setId(
                    matchService.saveMatch(match).getId());
            round.getMatches().add(match);
        });
        return round;
    }

    private Optional<Player> findPossibleBye(List<MatchPair> matches, List<Player> players) {
        return matches.stream()
                .filter(m -> m.getPlayer2().equals(BYE_ID))
                .findFirst()
                .map(pair -> findPlayer(players, pair.getPlayer1()));
    }

    private Player findPlayer(List<Player> players, Long player) {
        if (player.equals(BYE_ID)) {
            return byeService.getBye();
        }
        return players.stream().filter(p -> p.getId().equals(player)).findFirst().orElseThrow(
                () -> new IllegalStateException("No player found for ID: " + player)
        );
    }
}
