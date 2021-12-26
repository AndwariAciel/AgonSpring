package de.andwari.agon.app.controller;

import de.andwari.agon.app.start.MyFxmlLoader;
import de.andwari.agon.app.util.DataBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EventStartPageController extends FxController {

    private final MyFxmlLoader loader;

    @FXML
    public Button startEliminationBtn;
    @FXML
    public Button startTournamentBtn;

    @FXML
    public void initialize() {
        startTournamentBtn.setGraphic(new ImageView(
                new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("images/trophy.png")))
        ));
        startTournamentBtn.setText(null);
        startTournamentBtn.setOnAction(event -> createNewEvent());

        startEliminationBtn.setGraphic(new ImageView(
                new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("images/elimination.png")))
        ));
        startEliminationBtn.setText(null);
        startEliminationBtn.setOnAction(event -> notYet());
    }

    @Override
    public void setDataAndInit(DataBundle data) {
        // No additional data required
    }

    private void createNewEvent() {
        loader.loadNew(this, PlayerSelectionPageController.class, null);
    }

    private void notYet() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Not yet...");
        alert.setHeaderText("Not yet implemented. Coming soon...");
        alert.setContentText(null);
        alert.showAndWait();
    }
}
