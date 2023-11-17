package de.andwari.agon.app.controller;

import de.andwari.agon.app.event.PlayerCreatedEvent;
import de.andwari.agon.app.item.PlayerItem;
import de.andwari.agon.app.mapper.PlayerItemMapper;
import de.andwari.agon.app.start.MyFxmlLoader;
import de.andwari.agon.app.util.DataBundle;
import de.andwari.agon.business.player.PlayerService;
import de.andwari.agon.core.service.ResourceBundleService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.*;

@Service
@FxmlView("/pages/player-selection.fxml")
@RequiredArgsConstructor
public class PlayerSelectionPageController extends FxController implements ApplicationListener<PlayerCreatedEvent> {

    @FXML
    private Label lblPlayers;
    @FXML
    private TextField tfSearchPlayerEvent;
    @FXML
    private TableView<PlayerItem> tvPlayersInEvent;
    private ObservableList<PlayerItem> listOfPlayersInEvent;
    @FXML
    private TableColumn<PlayerItem, String> tcSelectedPlayer;
    @FXML
    private TableColumn<PlayerItem, String> tcSelectedDci;
    @FXML
    private TextField tfSearchPlayer;
    @FXML
    private TableView<PlayerItem> tvPlayers;
    private ObservableList<PlayerItem> listOfPlayers;
    @FXML
    private TableColumn<PlayerItem, String> tcSelectPlayer;
    @FXML
    private TableColumn<PlayerItem, String> tcSelectDci;

    private final MyFxmlLoader loader;
    private final PlayerService playerService;
    private final PlayerItemMapper mapper;
    private final ResourceBundleService rbService;

    private static final String PLAYER_TEXT_KEY = "player.select.inevent";

    @FXML
    public void initialize() {
        setupPlayers();
        setupPlayersInEvent();
        updatePlayerSizeLabel();
    }

    @Override
    public void setDataAndInit(DataBundle data) {
        //not needed
    }

    public void startEvent() {
        DataBundle bundle = DataBundle.empty();
        bundle.addData(
                EventSettingsPageController.PLAYERS_KEY,
                listOfPlayersInEvent.stream()
                        .map(mapper::toModel)
                        .collect(Collectors.toList())
        );
        bundle.addData(
                EventSettingsPageController.CONTROLLER_KEY,
                this
        );
        loader.loadNew(this, EventSettingsPageController.class, bundle);
    }

    public void startEventManually() {
    }

    private void setupPlayers() {
        listOfPlayers = FXCollections.observableList(playerService.findAll()
                .stream().map(mapper::toItem).collect(Collectors.toList()));
        SortedList<PlayerItem> sortedList = new SortedList<>(listOfPlayers);
        sortedList.setComparator(Comparator.comparing(PlayerItem::getComparableName));
        FilteredList<PlayerItem> filteredList = new FilteredList<>(sortedList);
        tvPlayers.setItems(filteredList);

        tcSelectPlayer.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        tcSelectDci.setCellValueFactory(cellData -> cellData.getValue().dciProperty());

        tfSearchPlayer.textProperty().addListener((observable, oldValue, newValue) ->
                filteredList.setPredicate(p -> filterPlayer(p, newValue))
        );

        tvPlayers.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                movePlayer(listOfPlayers, listOfPlayersInEvent, tvPlayers.getSelectionModel().getSelectedItem());
            }
        });
    }

    private void setupPlayersInEvent() {
        listOfPlayersInEvent = FXCollections.observableArrayList();
        SortedList<PlayerItem> sortedList = new SortedList<>(listOfPlayersInEvent);
        sortedList.setComparator(Comparator.comparing(PlayerItem::getComparableName));
        FilteredList<PlayerItem> filteredList = new FilteredList<>(sortedList);
        tvPlayersInEvent.setItems(filteredList);

        tcSelectedPlayer.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        tcSelectedDci.setCellValueFactory(cellData -> cellData.getValue().dciProperty());

        tfSearchPlayerEvent.textProperty().addListener((observable, oldValue, newValue) ->
                filteredList.setPredicate(p -> filterPlayer(p, newValue))
        );

        tvPlayersInEvent.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                movePlayer(listOfPlayersInEvent, listOfPlayers, tvPlayersInEvent.getSelectionModel().getSelectedItem());
            }
        });
    }

    private boolean filterPlayer(PlayerItem player, String filter) {
        if (filter.isEmpty()) {
            return true;
        }
        String smallName = player.getName().toLowerCase();
        String smallSearch = filter.toLowerCase();
        String dci = player.getDci();
        return smallName.contains(smallSearch) || (dci != null && dci.contains(filter));
    }

    private void movePlayer(ObservableList<PlayerItem> fromList, ObservableList<PlayerItem> toList, PlayerItem player) {
        toList.add(player);
        fromList.remove(player);
        updatePlayerSizeLabel();
    }

    @Override
    public void onApplicationEvent(PlayerCreatedEvent event) {
        if(nonNull(listOfPlayers))
            listOfPlayers.add(mapper.toItem(event.getPlayer()));
    }

    private void updatePlayerSizeLabel() {
        lblPlayers.setText(String.format(rbService.getBundle().getString(PLAYER_TEXT_KEY), listOfPlayersInEvent.size()));
    }

}
