package de.andwari.agon.app.controller;

import de.andwari.agon.app.util.DataBundle;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/pages/start-page.fxml")
public class StartPageController extends FxController {

    @Override
    public void setDataAndInit(DataBundle data) {
        //not needed
    }

}
