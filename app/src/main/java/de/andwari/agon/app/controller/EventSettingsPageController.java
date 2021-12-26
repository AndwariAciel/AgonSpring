package de.andwari.agon.app.controller;

import de.andwari.agon.app.start.MyFxmlLoader;
import de.andwari.agon.app.util.DataBundle;
import de.andwari.agon.business.player.EventService;
import de.andwari.agon.model.player.Player;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Component
@FxmlView("/pages/event-settings.fxml")
public class EventSettingsPageController extends FxController {

    public final static String PLAYERS_KEY = "players.key";
    public final static String CONTROLLER_KEY = "controller.key";
    private List<Player> players;

    private final MyFxmlLoader loader;
    private final EventService eventService;

    @FXML
    private Label lbDate;
    @FXML
    private TextField tfEventName;
    @FXML
    private CheckBox cbRankpoints;

    @FXML
    public void initialize() {
        lbDate.setText(getDate());
    }

    private String getDate() {
        var date = LocalDate.now();
        return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public void startEvent() {
        var event = eventService.createNewEvent(
                players,
                tfEventName.getText(),
                cbRankpoints.isSelected());
        loader.load(
                source.stage,
                this,
                EventSeatingsPageController.class,
                DataBundle.create(
                        EventSeatingsPageController.EVENT_KEY,
                        event
                ));
        ((Stage) lbDate.getScene().getWindow()).close();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setDataAndInit(DataBundle data) {
        players = (List<Player>) data.getData(PLAYERS_KEY);
    }
}
