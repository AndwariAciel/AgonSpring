package de.andwari.agon.app.controller.views;

import de.andwari.agon.app.controller.EventPageController;
import de.andwari.agon.app.controller.FxController;
import de.andwari.agon.app.item.MatchItem;
import de.andwari.agon.app.util.DataBundle;
import de.andwari.agon.core.service.ResourceBundleService;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import static javafx.scene.input.MouseButton.PRIMARY;
import static javafx.scene.input.MouseButton.SECONDARY;
import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.GREY;

@RequiredArgsConstructor
@Component
@FxmlView("/pages/views/match-view.fxml")
public class MatchViewController extends FxController {

    public Label lbPlayer1;
    public Label lbScore1;
    public Label lbScore2;
    public Label lbPlayer2;
    public FlowPane fpClickLeft;
    public FlowPane fpClickRight;
    public Button buttonSubmit;

    private MatchItem match;
    private EventPageController eventPageController;
    private final ResourceBundleService rbService;

    public static final String MATCH_KEY = "match.key";
    public static final String CONTROLLER_KEY = "controller.key";
    private static final String SUBMIT_KEY = "event.event.button.submit";
    private static final String REVOKE_KEY = "event.event.button.revoke";

    @Override
    public void setDataAndInit(DataBundle data) {
        match = (MatchItem) data.getData(MATCH_KEY);
        eventPageController = (EventPageController) data.getData(CONTROLLER_KEY);
        fpClickLeft.setOnMouseClicked(this::updateLeft);
        fpClickRight.setOnMouseClicked(this::updateRight);
        updateMatch(match);
    }

    private void updateRight(MouseEvent mouseEvent) {
        if (match.isFinished())
            return;
        if (mouseEvent.getButton().equals(PRIMARY))
            match.setWinsPlayer2(
                    increaseScore(lbScore2, match.getWinsPlayer2()));
        else if (mouseEvent.getButton().equals(SECONDARY))
            match.setWinsPlayer2(
                    decreaseScore(lbScore2, match.getWinsPlayer2()));
    }

    private void updateLeft(MouseEvent mouseEvent) {
        if (match.isFinished())
            return;
        if (mouseEvent.getButton().equals(PRIMARY))
            match.setWinsPlayer1(
                    increaseScore(lbScore1, match.getWinsPlayer1()));
        else if (mouseEvent.getButton().equals(SECONDARY))
            match.setWinsPlayer1(
                    decreaseScore(lbScore1, match.getWinsPlayer1()));
    }

    private int decreaseScore(Label lbScore, int wins) {
        if (wins > 0)
            wins--;
        lbScore.setText(Integer.toString(wins));
        return wins;
    }

    private int increaseScore(Label lbScore, int wins) {
        if (wins < 2 && overAllWins())
            wins++;
        lbScore.setText(Integer.toString(wins));
        return wins;
    }

    private boolean overAllWins() {
        return (match.getWinsPlayer1() + match.getWinsPlayer2()) < 3;
    }

    public void submitOrResetMatch() {
        if (match.isFinished()) {
            match.setFinished(false);
            match.setWinsPlayer1(0);
            match.setWinsPlayer2(0);
            updateMatch(match);
            eventPageController.resetMatch(match);
        } else {
            match.setFinished(true);
            updateMatch(match);
            eventPageController.finishMatch(match);
        }
    }

    public void updateMatch(MatchItem matchItem) {
        if(!matchItem.isByeMatch()) {
            match = matchItem;
            lbPlayer1.setText(matchItem.getPlayer1());
            lbPlayer2.setText(matchItem.getPlayer2());
            lbScore1.setText(matchItem.getWinsPlayer1().toString());
            lbScore2.setText(matchItem.getWinsPlayer2().toString());
            if (matchItem.isFinished()) {
                setColor(GREY);
                buttonSubmit.setText(rbService.getBundle().getString(REVOKE_KEY));
            } else {
                setColor(BLACK);
                buttonSubmit.setText(rbService.getBundle().getString(SUBMIT_KEY));
            }
        }
    }

    private void setColor(Color color) {
        lbPlayer1.setTextFill(color);
        lbPlayer2.setTextFill(color);
        lbScore1.setTextFill(color);
        lbScore2.setTextFill(color);
    }
}
