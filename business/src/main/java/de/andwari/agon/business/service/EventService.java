package de.andwari.agon.business.service;

import de.andwari.agon.business.mapper.EventMapper;
import de.andwari.agon.business.mapper.MatchMapper;
import de.andwari.agon.core.entity.EventEntity;
import de.andwari.agon.core.repository.EventRepository;
import de.andwari.agon.core.repository.MatchRepository;
import de.andwari.agon.model.event.*;
import de.andwari.agon.model.player.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final MatchService matchService;
    private final RoundService roundService;
    private final MatchRepository matchRepository;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final MatchMapper matchMapper;

    public AgonEvent createNewEvent(List<Player> players, String eventName, boolean ranked) {
        AgonEvent event = AgonEvent.builder()
                .eventName(eventName)
                .currentRound(0)
                .date(LocalDate.now())
                .rounds(new ArrayList<>())
                .players(players)
                .standings(
                        players.stream()
                                .map(p ->
                                        Standing.builder()
                                                .playerId(p.getId())
                                                .playerName(p.getName())
                                                .dropped(false)
                                                .build())
                                .collect(Collectors.toList())
                )
                .ranked(ranked)
                .build();
        event.getPlayers().forEach(player -> matchService.updateStandingForPlayer(event, player));
        EventEntity savedEvent = eventRepository.save(eventMapper.toEntity(event));
        event.setId(savedEvent.getId());
        return event;
    }

    public List<Player> createSeatings(AgonEvent event) {
        var seatings = new ArrayList<>(event.getPlayers());
        Collections.shuffle(seatings);
        return seatings;
    }

    public void createCrossPairings(List<Player> seatings, AgonEvent event) {
        // Check if bye
        Player bye = null;
        var tmpSeatings = new ArrayList<>(seatings);
        if (tmpSeatings.size() % 2 == 1) {
            bye = tmpSeatings.get(new Random().nextInt(tmpSeatings.size()) - 1);
            tmpSeatings.remove(bye);
        }
        event.setRounds(new ArrayList<>());
        var round = Round.builder().bye(bye).open(true).matches(new ArrayList<>()).build();
        // seatings size is now even.
        var halfSize = (tmpSeatings.size() / 2);
        for (int i = 0; i < halfSize; i++) {
            round.getMatches().add(
                    Match.builder()
                            .player1(tmpSeatings.get(i))
                            .player2(tmpSeatings.get(i + halfSize))
                            .result(Result.DEFAULT)
                            .build()
            );
        }
        event.getRounds().add(
                roundService.saveRound(round, event.getId())
        );
        eventRepository.save(eventMapper.toEntity(event));
    }

    public void updateMatch(AgonEvent event, Match match) {
        event.getRounds().stream().map(Round::getMatches).flatMap(Collection::stream)
                .filter(m -> m.getId().equals(match.getId())).findFirst()
                .ifPresent(m -> {
                    m.setResult(match.getResult());
                });
        matchService.updateStandingForPlayer(event, findEventPlayer(event, match.getPlayer1().getId()));
        matchService.updateStandingForPlayer(event, findEventPlayer(event, match.getPlayer2().getId()));
    }

    private Player findEventPlayer(AgonEvent event, Long id) {
        return event.getPlayers().stream().filter(player -> player.getId().equals(id)).findFirst().orElseThrow(
                () -> new RuntimeException("No Player found")
        );
    }
}
