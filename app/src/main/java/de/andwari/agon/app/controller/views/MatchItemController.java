package de.andwari.agon.app.controller.views;

import de.andwari.agon.app.controller.FxController;
import de.andwari.agon.app.item.MatchItem;
import de.andwari.agon.app.util.DataBundle;
import de.andwari.agon.core.service.ResourceBundleService;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Service;

import static javafx.scene.paint.Color.*;

@RequiredArgsConstructor
@Service
@FxmlView("/pages/views/match-item.fxml")
public class MatchItemController extends FxController {

    public static final String MATCH_KEY = "match.key";
    private static final String DEFAULT_SCORE = "-";
    private static final String STATUS_PLAYING_KEY = "event.event.status.playing";
    private static final String STATUS_FINISHED_KEY = "event.event.status.finished";
    private static final String STATUS_FINISHED_BYE = "event.event.status.bye";

    @Getter
    public HBox box;
    public Label status;
    public Label player1;
    public Label player2;
    public Label scoreP1;
    public Label scoreP2;

    private final ResourceBundleService rbService;

    @Override
    public void setDataAndInit(DataBundle data) {
        MatchItem item = (MatchItem) data.getData(MATCH_KEY);
        setStatus(item);
        player1.setText(item.getPlayer1());
        player2.setText(item.getPlayer2());
        scoreP1.setText(DEFAULT_SCORE);
        scoreP2.setText(DEFAULT_SCORE);
        if(item.isFinished() && !item.isByeMatch()) {
            scoreP1.setText(item.getWinsPlayer1().toString());
            scoreP2.setText(item.getWinsPlayer2().toString());
            setColor(GREY);
        } else {
            scoreP1.setText(DEFAULT_SCORE);
            scoreP2.setText(DEFAULT_SCORE);
            setColor(item.isByeMatch() ? GREY : BLACK);
        }
    }

    private void setStatus(MatchItem match) {
        if(match.isByeMatch()) {
            status.setText(
                    rbService.getBundle().getString(STATUS_FINISHED_BYE)
            );
        } else if(match.isFinished())
            status.setText(
                    rbService.getBundle().getString(STATUS_FINISHED_KEY)
            );
        else
            status.setText(
                    rbService.getBundle().getString(STATUS_PLAYING_KEY)
            );
    }

    private void setColor(Color color) {
        scoreP1.setTextFill(color);
        scoreP2.setTextFill(color);
        player1.setTextFill(color);
        player2.setTextFill(color);
    }

}
