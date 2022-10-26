package de.andwari.agon.business.service;

import de.andwari.agon.business.mapper.RoundMapper;
import de.andwari.agon.core.repository.RoundRepository;
import de.andwari.agon.model.event.Round;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoundService {

    private final RoundRepository roundRepository;
    private final RoundMapper roundMapper;

    public Round saveRound(Round round, Long eventId) {
        return roundMapper.toModel(
                roundRepository.save(roundMapper.toEntity(round, eventId)));
    }
}
