package de.andwari.agon.business;

import de.andwari.agon.model.event.AgonEvent;
import de.andwari.agon.model.player.Player;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataFactory {

    public static AgonEvent createEvent(int players) {
        return AgonEvent.builder()
                .eventName("event")
                .currentRound(0)
                .date(LocalDate.now())
                .players(DataFactory.createListOfPlayers(players))
                .build();
    }

    private static List<Player> createListOfPlayers(int players) {
        var list = new ArrayList<Player>();
        for(int i = 0; i < players; i++)
            list.add(Player.builder().name("player" + i).build());
        return list;
    }
}
