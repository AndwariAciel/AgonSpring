package de.andwari.agon.app.start;

import de.andwari.agon.app.start.controller.MainController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartListener implements ApplicationListener<ApplicationStartedEvent> {

    @Autowired
    private MyFxmlLoader loader;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        loader.load(event.getStage(), MainController.class);
    }
}