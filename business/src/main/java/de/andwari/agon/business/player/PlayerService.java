package de.andwari.agon.business.player;

import com.sun.istack.NotNull;
import de.andwari.agon.business.mapper.PlayerMapper;
import de.andwari.agon.core.exception.PlayerExistsException;
import de.andwari.agon.core.repository.PlayerRepository;
import de.andwari.agon.model.player.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository repository;
    private final PlayerMapper mapper;

    public List<Player> findAll() {
        return repository.findAll().stream().map(mapper::toModel).collect(Collectors.toList());
    }

    public Player addPlayer(@NotNull Player player) throws PlayerExistsException {
        repository.findAllByNameOrDci(player.getName(), player.getDci()).stream()
                .filter(p -> !p.getDci().isEmpty()).findAny().orElseThrow(PlayerExistsException::new);

        return mapper.toModel(repository.save(mapper.toEntity(player)));

    }

    public void deletePlayer(long id) {
        repository.deleteById(id);
    }

    public void updatePlayer(@NotNull Player player) {
        repository.save(mapper.toEntity(player));
    }
}
