package de.andwari.agon.app.controller;

import de.andwari.agon.app.util.DataBundle;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FxmlView("pages/event.fxml")
public class EventPageController extends FxController {

    public static final String EVENT_KEY = "event.key";

    public Label lbBye;
    public ListView listViewOfMatches;
    public TableView listViewOfRankings;
    public TableColumn tcStandingRank;
    public TableColumn tcStandingScore;
    public TableColumn tcStandingPoints;
    public TableColumn tcStandingPlayer;
    public Label lbRound;

    @Override
    public void setDataAndInit(DataBundle data) {

    }

    public void goToPreviousRound(ActionEvent actionEvent) {
    }

    public void goToNextRound(ActionEvent actionEvent) {
    }

    public void finishRound(ActionEvent actionEvent) {
    }

    public void finishEvent(ActionEvent actionEvent) {
    }

    public void reopenEvent(ActionEvent actionEvent) {
    }

    public void revokeRound(ActionEvent actionEvent) {
    }

    public void showDetails(ActionEvent actionEvent) {
    }

    public void selectPlayer(ActionEvent actionEvent) {
    }
}
