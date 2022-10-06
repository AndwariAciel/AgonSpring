package de.andwari.agon.app.controller.views;

import de.andwari.agon.app.controller.FxController;
import de.andwari.agon.app.item.MatchItem;
import de.andwari.agon.app.util.DataBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@FxmlView("/pages/views/match-item.fxml")
public class MatchItemController extends FxController {

    public static final String MATCH_KEY = "match.key";
    private static final String DEFAULT_SCORE = "-";
    private static final String STATUS_PLAYING = "playing";
    private static final String STATUS_FINISHED = "finished";

    @FXML
    private HBox box;

    @FXML
    private Label status;
    @FXML
    private Label player1;
    @FXML
    private Label player2;
    @FXML
    private Label scoreP1;
    @FXML
    private Label scoreP2;

    public HBox getBox() {
        return box;
    }

    @Override
    public void setDataAndInit(DataBundle data) {
        MatchItem item = (MatchItem) data.getData(MATCH_KEY);
        status.setText(STATUS_PLAYING);
        player1.setText(item.getPlayer1());
        player2.setText(item.getPlayer2());
        scoreP1.setText(DEFAULT_SCORE);
        scoreP2.setText(DEFAULT_SCORE);
    }
}
