package de.andwari.agon.business.player;

import de.andwari.agon.business.mapper.PlayerMapper;
import de.andwari.agon.core.entity.PlayerEntity;
import de.andwari.agon.core.exception.PlayerExistsException;
import de.andwari.agon.core.repository.PlayerRepository;
import de.andwari.agon.model.player.Player;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository repository;
    private final PlayerMapper mapper;
    private static Player bye;

    @PostConstruct
    void createBye() {
        bye = Player.builder()
                .id(-1L)
                .name("BYE")
                .build();
    }

    public List<Player> findAll() {
        return repository.findAll().stream().map(mapper::toModel).collect(Collectors.toList());
    }

    public Player addPlayer(Player player) throws PlayerExistsException {
        Optional<PlayerEntity> possiblePlayer = repository.findAllByNameOrDci(player.getName(), player.getDci()).stream()
                .filter(p -> (!p.getDci().isEmpty() || p.getName().equals(player.getName())))
                .findAny();
        if(possiblePlayer.isPresent())
            throw new PlayerExistsException();

        return mapper.toModel(repository.save(mapper.toEntity(player)));
    }

    public void deletePlayer(long id) {
        repository.deleteById(id);
    }

    public void updatePlayer(Player player) {
        repository.save(mapper.toEntity(player));
    }

    public Player getBye() {
        return bye;
    }

}
