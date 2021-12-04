package de.andwari.agon.business.player;

import com.sun.istack.NotNull;
import de.andwari.agon.business.mapper.PlayerMapper;
import de.andwari.agon.core.entity.PlayerEntity;
import de.andwari.agon.core.exception.PlayerExistsException;
import de.andwari.agon.core.repository.PlayerRepository;
import de.andwari.agon.model.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository repository;
    @Autowired
    private PlayerMapper mapper;

    public List<Player> findAll() {
        return repository.findAll().stream().map(p -> mapper.toModel(p)).collect(Collectors.toList());
    }

    public Player addPlayer(@NotNull Player player) throws PlayerExistsException {
        List<PlayerEntity> existingPlayers = repository.findAllByNameOrDci(player.getName(), player.getDci()).stream()
                .filter(p -> !p.getDci().isEmpty()).collect(Collectors.toList());
        if(existingPlayers.isEmpty())
            return mapper.toModel(repository.save(mapper.toEntity(player)));
        else
            throw new PlayerExistsException();
    }

    public void deletePlayer(long id) {
        repository.deleteById(id);
    }

    public void updatePlayer(@NotNull Player player) {
        repository.save(mapper.toEntity(player));
    }
}
