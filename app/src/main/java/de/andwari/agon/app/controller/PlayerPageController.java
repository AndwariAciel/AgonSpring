package de.andwari.agon.app.controller;

import de.andwari.agon.app.item.PlayerItem;
import de.andwari.agon.app.mapper.PlayerItemMapper;
import de.andwari.agon.business.player.PlayerService;
import de.andwari.agon.core.exception.PlayerExistsException;
import de.andwari.agon.model.player.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import net.rgielen.fxweaver.core.FxmlView;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
@FxmlView("/pages/player-page.fxml")
public class PlayerPageController {

    @Autowired
    private PlayerService playerService;
    @Autowired
    private PlayerItemMapper mapper;

    public TextField tfPlayername, tfDciNumber;
    public CheckBox cbMember;
    public Label lbWarning;
    public TableView<PlayerItem> tvListOfPlayers;
    private ObservableList<PlayerItem> listOfPlayers;
    public TableColumn<PlayerItem, String> tcName, tcDci, tcMember;
    public TextField tfSearch;

    @FXML
    public void initialize() {
        listOfPlayers = FXCollections.observableList(playerService.findAll().stream()
                .map(mapper::toItem).collect(Collectors.toList()));
        SortedList<PlayerItem> sortedList = new SortedList<>(listOfPlayers);
        sortedList.setComparator(Comparator.comparing(PlayerItem::getName));
        FilteredList<PlayerItem> filteredList = new FilteredList<>(sortedList);
        tvListOfPlayers.setItems(filteredList);

        tcName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        tcDci.setCellValueFactory(cellData -> cellData.getValue().dciProperty());
        tcMember.setCellValueFactory(cellData -> cellData.getValue().memberProperty());

        tfSearch.textProperty().addListener((observable, oldValue, newValue) ->
                filteredList.setPredicate(p -> filterPlayer(p, newValue))
        );
    }

    public void addPlayer() {
        lbWarning.setVisible(false);
        try {
            Player newPlayer = playerService.addPlayer(
                    Player.builder().name(tfPlayername.getText()).dci(tfDciNumber.getText())
                            .member(cbMember.isSelected()).build()
            );
            listOfPlayers.add(mapper.toItem(newPlayer));
            clearFields();
        } catch (PlayerExistsException e) {
            lbWarning.setVisible(true);
        }
    }

    public void deletePlayer() {
        PlayerItem selectedPlayer = tvListOfPlayers.getSelectionModel().getSelectedItem();
        if (isNull(selectedPlayer))
            return;
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
