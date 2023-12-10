package de.andwari.agon.app.controller;

import de.andwari.agon.app.comparator.StandingComparator;
import de.andwari.agon.app.item.StandingItem;
import de.andwari.agon.app.listdata.cell.StandingCell;
import de.andwari.agon.app.mapper.StandingMapper;
import de.andwari.agon.app.util.DataBundle;
import de.andwari.agon.model.event.Standing;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@FxmlView("/pages/standing-page.fxml")
public class StandingsPageController extends FxController {

    public static final String KEY_STANDINGS = "key.standings";

    private final StandingMapper standingMapper;

    public TableView<StandingItem> tableStandings;
    public TableColumn<StandingItem, String> colRank;
    public TableColumn<StandingItem, String> colPlayer;
    public TableColumn<StandingItem, String> colScore;
    public TableColumn<StandingItem, String> colOpScore;
    public TableColumn<StandingItem, String> colGameScore;
    public TableColumn<StandingItem, String> colOpGameScore;

    private ObservableList<StandingItem> listOfStandings;

    @Override
    public void setDataAndInit(DataBundle data) {
        var standings = ((List<Standing>) data.getData(KEY_STANDINGS))
                .stream().map(standingMapper::toItem)
                .sorted(new StandingComparator().reversed())
                .toList();
        for (int i = 1; i <= standings.size(); i++) {
            standings.get(i - 1).setRank(Integer.toString(i));
        }
        listOfStandings = FXCollections.observableArrayList();
        listOfStandings.addAll(standings);
        tableStandings.setItems(listOfStandings);

        colRank.setCellValueFactory(cellData -> cellData.getValue().getRankProperty());
        colRank.setCellFactory(cb -> new StandingCell());
        colPlayer.setCellValueFactory(cellData -> cellData.getValue().getPlayerProperty());
        colPlayer.setCellFactory(cb -> new StandingCell());
        colScore.setCellValueFactory(cellData -> cellData.getValue().getScoreProperty());
        colScore.setCellFactory(cb -> new StandingCell());
        colOpScore.setCellValueFactory(cellData -> cellData.getValue().getOpponentMatchWinPercentageProperty());
        colOpScore.setCellFactory(cb -> new StandingCell());
        colGameScore.setCellValueFactory(cellData -> cellData.getValue().getGameWinPercentageProperty());
        colGameScore.setCellFactory(cb -> new StandingCell());
        colOpGameScore.setCellValueFactory(cellData -> cellData.getValue().getOpponentGameWinPercentageProperty());
        colOpGameScore.setCellFactory(cb -> new StandingCell());

        colRank.setSortable(false);
        colPlayer.setSortable(false);
        colScore.setSortable(false);
        colOpScore.setSortable(false);
        colOpGameScore.setSortable(false);
        colGameScore.setSortable(false);
        colOpGameScore.setSortable(false);
    }

    public void closeWindow(ActionEvent actionEvent) {
        stage.close();
    }

    public void dropPlayer(ActionEvent actionEvent) {
        var index = tableStandings.getSelectionModel().getSelectedIndex();
        if(index < 0)
            return;
        var standing = listOfStandings.get(index);
        standing.setDropped(true);
        ((EventPageController) source).dropPlayer(standing.getPlayerId());
        listOfStandings.set(index, standing);
    }
}
