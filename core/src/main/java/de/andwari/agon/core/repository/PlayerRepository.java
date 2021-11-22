package de.andwari.agon.core.repository;

import de.andwari.agon.core.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {

    List<PlayerEntity> findAllByNameOrDci(String name, String dci);
}
