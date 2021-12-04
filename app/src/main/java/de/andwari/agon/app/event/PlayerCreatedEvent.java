package de.andwari.agon.app.event;

import de.andwari.agon.model.player.Player;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PlayerCreatedEvent extends ApplicationEvent {

    private final Player player;

    public PlayerCreatedEvent(Object source, Player player) {
        super(source);
        this.player = player;
    }
}
