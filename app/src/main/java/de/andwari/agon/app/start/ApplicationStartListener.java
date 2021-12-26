package de.andwari.agon.app.start;

import de.andwari.agon.app.controller.MainController;
import de.andwari.agon.app.util.DataBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartListener implements ApplicationListener<ApplicationStartedEvent> {

    @Autowired
    private MyFxmlLoader loader;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        loader.load(event.getStage(), null, MainController.class, DataBundle.empty());
    }
}
