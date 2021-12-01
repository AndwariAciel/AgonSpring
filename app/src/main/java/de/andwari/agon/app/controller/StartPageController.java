package de.andwari.agon.app.controller;

import de.andwari.agon.app.util.DataBundle;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/pages/start-page.fxml")
public class StartPageController implements FxController {

    private Stage stage;

    @Override
    public void setDataAndInit(Stage stage, DataBundle data) {
        this.stage = stage;
    }

}
