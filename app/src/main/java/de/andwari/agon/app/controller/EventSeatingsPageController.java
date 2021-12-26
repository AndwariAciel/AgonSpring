package de.andwari.agon.app.controller;

import de.andwari.agon.app.item.SeatingsItem;
import de.andwari.agon.app.mapper.SeatingsMapper;
import de.andwari.agon.app.util.DataBundle;
import de.andwari.agon.business.player.EventService;
import de.andwari.agon.model.event.AgonEvent;
import de.andwari.agon.model.player.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
@FxmlView("/pages/event-seatings.fxml")
public class EventSeatingsPageController extends FxController {

    public static final String EVENT_KEY = "event.key";
    private AgonEvent event;

    @FXML
    private TableView<SeatingsItem> tvSeatings;
    private ObservableList<SeatingsItem> seatingsItems;
    @FXML
    private TableColumn<SeatingsItem, String> tcSeat;
    @FXML
    private TableColumn<SeatingsItem, String> tcPlayer;

    private final EventService eventService;
    private final SeatingsMapper mapper;

    @FXML
    public void initialize() {
        seatingsItems = FXCollections.observableArrayList();
        tvSeatings.setItems(seatingsItems);

        tcSeat.setCellValueFactory(value -> value.getValue().seatingNumber());
        tcPlayer.setCellValueFactory(cellData -> cellData.getValue().name());
    }

    @Override
    public void setDataAndInit(DataBundle data) {
        event = (AgonEvent) data.getData(EVENT_KEY);
        List<Player> seatings = eventService.createSeatings(event);
        for (int i = 0; i < seatings.size(); i++) {
            seatingsItems.add(mapper.toSeating(seatings.get(i), Integer.toString(i + 1)));
        }
    }

    public void startFirstRound() {
    }
}
