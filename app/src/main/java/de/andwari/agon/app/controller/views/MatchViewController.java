package de.andwari.agon.app.controller.views;

import de.andwari.agon.app.controller.FxController;
import de.andwari.agon.app.util.DataBundle;
import javafx.event.ActionEvent;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@FxmlView("/pages/views/match-view.fxml")
public class MatchViewController extends FxController {
    @Override
    public void setDataAndInit(DataBundle data) {
        
    }

    public void submitMatch(ActionEvent actionEvent) {
    }
}
