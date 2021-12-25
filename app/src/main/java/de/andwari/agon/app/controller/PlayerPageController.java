package de.andwari.agon.app.controller;

import de.andwari.agon.app.event.PlayerCreatedEvent;
import de.andwari.agon.app.item.PlayerItem;
import de.andwari.agon.app.mapper.PlayerItemMapper;
import de.andwari.agon.app.start.MyFxmlLoader;
import de.andwari.agon.app.util.Data;
import de.andwari.agon.app.util.DataBundle;
import de.andwari.agon.business.player.PlayerService;
import de.andwari.agon.core.exception.PlayerExistsException;
import de.andwari.agon.model.player.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;


@Component
@RequiredArgsConstructor
@FxmlView("/pages/player-page.fxml")
public class PlayerPageController implements FxController {

    private final PlayerService playerService;
    private final PlayerItemMapper mapper;
    private final MyFxmlLoader fxmlLoader;
    private final ApplicationEventPublisher applicationEventPublisher;

    @FXML
    public TextField tfPlayername;
    @FXML
    public TextField tfDciNumber;
    @FXML
    public CheckBox cbMember;
    @FXML
    public Label lbWarning;
    @FXML
    public TableView<PlayerItem> tvListOfPlayers;
    @FXML
    public TableColumn<PlayerItem, String> tcName;
    @FXML
    public TableColumn<PlayerItem, String> tcDci;
    @FXML
    public TableColumn<PlayerItem, String> tcMember;
    @FXML
    public TextField tfSearch;
    private ObservableList<PlayerItem> listOfPlayers;

    @FXML
    public void initialize() {
        listOfPlayers = FXCollections.observableList(playerService.findAll().stream()
                .map(mapper::toItem).collect(Collectors.toList()));
        SortedList<PlayerItem> sortedList = new SortedList<>(listOfPlayers);
        sortedList.setComparator(Comparator.comparing(PlayerItem::getComparableName));
        FilteredList<PlayerItem> filteredList = new FilteredList<>(sortedList);
        tvListOfPlayers.setItems(filteredList);

        tvListOfPlayers.setRowFactory(tv -> {
            TableRow<PlayerItem> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    editPlayer(row.getItem());
                }
            });
            return row;
        });

        tcName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        tcDci.setCellValueFactory(cellData -> cellData.getValue().dciProperty());
        tcMember.setCellValueFactory(cellData -> cellData.getValue().memberProperty());

        tfSearch.textProperty().addListener((observable, oldValue, newValue) ->
                filteredList.setPredicate(p -> filterPlayer(p, newValue))
        );
    }

    private void editPlayer(PlayerItem item) {
        int index = listOfPlayers.indexOf(item);
        DataBundle dataBundle = DataBundle.empty();
        dataBundle.addData(PlayerEditPageController.PLAYER_KEY, mapper.toModel(item));
        PlayerEditPageController controller = (PlayerEditPageController) fxmlLoader.loadNewAndWait(
                PlayerEditPageController.class, dataBundle);
        listOfPlayers.remove(index);
        listOfPlayers.add(mapper.toItem(controller.getPlayer()));
    }

    @Override
    public void setDataAndInit(Stage stage, DataBundle data) {
        // Not required
    }

    public void addPlayer() {
        lbWarning.setVisible(false);
        try {
            Player newPlayer = playerService.addPlayer(
                    Player.builder().name(tfPlayername.getText()).dci(tfDciNumber.getText())
                            .member(cbMember.isSelected()).build()
            );
            listOfPlayers.add(mapper.toItem(newPlayer));
            applicationEventPublisher.publishEvent(new PlayerCreatedEvent(this, newPlayer));
            clearFields();
        } catch (PlayerExistsException e) {
            lbWarning.setVisible(true);
        }
    }

    public void deletePlayer() {
        PlayerItem selectedPlayer = tvListOfPlayers.getSelectionModel().getSelectedItem();
        if (isNull(selectedPlayer)) {
            return;
        }
        playerService.deletePlayer(selectedPlayer.getId());
        listOfPlayers.remove(selectedPlayer);
    }

    private void clearFields() {
        tfPlayername.setText(Strings.EMPTY);
        tfDciNumber.setText(Strings.EMPTY);
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

}
