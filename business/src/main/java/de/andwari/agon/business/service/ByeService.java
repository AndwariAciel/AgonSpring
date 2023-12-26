package de.andwari.agon.business.service;

import com.github.javafaker.Faker;
import de.andwari.agon.business.mapper.PlayerMapper;
import de.andwari.agon.core.entity.PlayerEntity;
import de.andwari.agon.core.repository.PlayerRepository;
import de.andwari.agon.model.player.Player;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ByeService {

    public static final Long BYE_ID = 1L;

    private final PlayerRepository repository;
    private final PlayerMapper mapper;
    private static Player bye;

    @PostConstruct
    void createBye() {
        repository.findById(BYE_ID)
                .ifPresentOrElse(
                        byeEntity ->
                                bye = mapper.toModel(byeEntity),
                        () -> {
                            bye = mapper.toModel(
                                    repository.save(mapper.toEntity(
                                            Player.builder()
                                                    .id(BYE_ID)
                                                    .name("BYE")
                                                    .build()
                                    ))
                            );
                            createDummyPlayers();
                        }
                );
    }

    private void createDummyPlayers() {
        var faker = new Faker();
        for (int i = 0; i < 20; i++) {
            repository.save(PlayerEntity.builder().name(faker.name().fullName()).build());
        }
    }

    public Player getBye() {
        return bye;
    }

}
