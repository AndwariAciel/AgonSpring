package de.andwari.agon.core.service;

import de.andwari.agon.core.entity.EventEntity;
import de.andwari.agon.core.entity.RoundEntity;
import de.andwari.agon.core.repository.EventRepository;
import de.andwari.agon.core.repository.RoundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class EventRepositoryService {

    private final EventRepository eventRepository;
    private final RoundRepository roundRepository;

    public EventEntity save(EventEntity event) {
        List<RoundEntity> loadedRounds;
        event.getRounds().stream()
                .filter(r -> Objects.nonNull(r.getId()))
                .forEach(r -> {

                        }
                );
        return null;
    }

}
