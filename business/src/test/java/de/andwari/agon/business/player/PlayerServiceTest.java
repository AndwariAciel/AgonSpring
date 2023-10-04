package de.andwari.agon.business.player;

import de.andwari.agon.business.factory.TestDataFactory;
import de.andwari.agon.business.mapper.PlayerMapper;
import de.andwari.agon.core.exception.PlayerExistsException;
import de.andwari.agon.core.repository.PlayerRepository;
import de.andwari.agon.model.player.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepo;

    @Mock
    private PlayerMapper mapper;

    @InjectMocks
    private PlayerService service;

    @Test
    void addPlayer() throws PlayerExistsException {
        doReturn(List.of()).when(playerRepo).findAllByNameOrDci(any(), any());

        service.addPlayer(Player.builder().name("Fritz").dci("1").build());

        verify(playerRepo, times(1)).save(any());
    }

    @Test
    void addExistingPlayerWithDci() {
        doReturn(TestDataFactory.createPlayerListWithDci()).when(playerRepo).findAllByNameOrDci(any(), any());

        assertThrows(PlayerExistsException.class, () ->
                service.addPlayer(Player.builder().name("Kai-Uwe").dci("123").build()));

        verify(playerRepo, times(0)).save(any());
    }

    @Test
    void addExistingPlayerWithoutDci() {
        doReturn(TestDataFactory.createPlayerListWithoutDci()).when(playerRepo).findAllByNameOrDci(any(), any());

        assertThrows(PlayerExistsException.class, () ->
                service.addPlayer(Player.builder().name("Hans").dci("").build()));

        verify(playerRepo, times(0)).save(any());
    }

    @Test
    void addExistingPlayerWithDciAndName() {
        doReturn(TestDataFactory.createPlayerListWithDci()).when(playerRepo).findAllByNameOrDci(any(), any());

        assertThrows(PlayerExistsException.class, () ->
                service.addPlayer(Player.builder().name("Hans").dci("123").build()));

        verify(playerRepo, times(0)).save(any());
    }


    @Test
    void addNewPlayerWithoutDci() throws PlayerExistsException {
        doReturn(TestDataFactory.createPlayerListWithoutDci()).when(playerRepo).findAllByNameOrDci(any(), any());

        service.addPlayer(Player.builder().name("Kai-Uwe").dci("").build());

        verify(playerRepo, times(1)).save(any());
    }
}