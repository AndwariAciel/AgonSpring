package de.andwari.agon.app.start;

import javafx.stage.Stage;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ApplicationStartedEvent extends ApplicationEvent {

    private Stage stage;

    public ApplicationStartedEvent(Object source, Stage stage) {
        super(source);
        this.stage = stage;
    }
}
