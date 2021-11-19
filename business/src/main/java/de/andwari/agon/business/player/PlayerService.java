package de.andwari.agon.business.player;

import de.andwari.agon.business.mapper.PlayerMapper;
import de.andwari.agon.core.repository.PlayerRepository;
import de.andwari.agon.model.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository repository;
    @Autowired
    private PlayerMapper mapper;

    public void addPlayer(Player player) {

        repository.save(mapper.toEntity(player));
    }

}
