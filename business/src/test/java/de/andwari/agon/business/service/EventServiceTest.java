package de.andwari.agon.business.service;

import de.andwari.agon.business.DataFactory;
import de.andwari.agon.model.event.AgonEvent;
import jdk.jfr.Event;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class EventServiceTest {

    @Test
    void createCrossPairings() {
        var event = DataFactory.createEvent(8);
        var service = new EventService();
        service.createCrossPairings(event.getPlayers(), event);
        assertThat(event.getRounds()).hasSize(1);
        assertThat(event.getRounds().get(0).getMatches()).hasSize(4);
    }

    @Test
    void createCrossPairingsWithBye() {
        var event = DataFactory.createEvent(7);
        var service = new EventService();
        service.createCrossPairings(event.getPlayers(), event);
        assertThat(event.getRounds()).hasSize(1);
        assertThat(event.getRounds().get(0).getMatches()).hasSize(3);
        assertThat(event.getRounds().get(0).getBye()).isNotNull();
    }
}