package de.andwari.agon.app.controller;

import de.andwari.agon.app.comparator.StandingComparator;
import de.andwari.agon.app.controller.views.MatchViewController;
import de.andwari.agon.app.item.MatchItem;
import de.andwari.agon.app.item.StandingItem;
import de.andwari.agon.app.listdata.cell.MatchCell;
import de.andwari.agon.app.listdata.cell.StandingCell;
import de.andwari.agon.app.mapper.MatchItemMapper;
import de.andwari.agon.app.mapper.StandingMapper;
import de.andwari.agon.app.start.MyFxmlLoader;
import de.andwari.agon.app.util.DataBundle;
import de.andwari.agon.business.service.EventService;
import de.andwari.agon.business.service.MatchService;
import de.andwari.agon.business.service.StandingService;
import de.andwari.agon.model.event.AgonEvent;
import de.andwari.agon.model.event.Match;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static de.andwari.agon.app.controller.views.MatchViewController.CONTROLLER_KEY;
import static de.andwari.agon.app.controller.views.MatchViewController.MATCH_KEY;

@Component
@RequiredArgsConstructor
@FxmlView("/pages/event.fxml")
public class EventPageController extends FxController {

    public static final String EVENT_KEY = "event.key";

    private AgonEvent event;
    private final MyFxmlLoader loader;
    private final MatchItemMapper matchMapper;
    private final StandingMapper standingMapper;
    private final EventService eventService;
    private final MatchService matchService;
    private final StandingService standingService;
    private final MatchViewController matchViewController;

    public Label lbBye;
    private ObservableList<MatchItem> listOfMatches;
    private ObservableList<StandingItem> listOfStandings;
    public ListView<MatchItem> listViewOfMatches;
    public TableView<StandingItem> listViewOfStandings;
    public TableColumn<StandingItem, String> tcStandingRank;
    public TableColumn<StandingItem, String> tcStandingScore;
    public TableColumn<StandingItem, String> tcStandingPoints;
    public TableColumn<StandingItem, String> tcStandingPlayer;
    public Label lbRound;

    @Override
    public void setDataAndInit(DataBundle data) {
        listOfMatches = FXCollections.observableArrayList();
        listViewOfMatches.setItems(listOfMatches);
        listViewOfMatches.setCellFactory(matchItemListView -> new MatchCell(loader));

        listOfStandings = FXCollections.observableArrayList();
        listViewOfStandings.setItems(listOfStandings);
        tcStandingRank.setCellValueFactory(cellData -> cellData.getValue().getRankProperty());
        tcStandingRank.setCellFactory(cb -> new StandingCell());
        tcStandingPlayer.setCellValueFactory(cellData -> cellData.getValue().getPlayerProperty());
        tcStandingPlayer.setCellFactory(cb -> new StandingCell());
        tcStandingScore.setCellValueFactory(cellData -> cellData.getValue().getStandingStringProperty());
        tcStandingScore.setCellFactory(cb -> new StandingCell());
        tcStandingPoints.setCellValueFactory(cellData -> cellData.getValue().getScoreProperty());
        tcStandingPoints.setCellFactory(cb -> new StandingCell());

        tcStandingRank.setSortable(false);
        tcStandingPlayer.setSortable(false);
        tcStandingScore.setSortable(false);
        tcStandingPoints.setSortable(false);


        event = (AgonEvent) data.getData(EVENT_KEY);
        //TODO Save Event
        event.getRounds().get(0).getMatches()
                .stream()
                .map(matchMapper::toMatchItem)
                .forEach(listOfMatches::add);

        updateStandings();

        var dataBundle = DataBundle.create(MATCH_KEY, listOfMatches.get(0));
        dataBundle.addData(CONTROLLER_KEY, this);
        matchViewController.setDataAndInit(dataBundle);

        listViewOfMatches.setOnMouseClicked(event ->
                matchViewController.updateMatch(listViewOfMatches.getSelectionModel().getSelectedItem())
        );
    }

    private void updateStandings() {
        listOfStandings.clear();
        listOfStandings.addAll(
                event.getPlayers().stream()
                        .map(p -> standingMapper.toItem(
                                standingService.findStandingForPlayer(event, p),
                                p.getName())
                        )
                        .collect(Collectors.toList())
        );
        listOfStandings.sort(new StandingComparator());
        for (int i = 1; i <= listOfStandings.size(); i++) {
            listOfStandings.get(i - 1).setRank(Integer.toString(i));
        }

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

    public void finishMatch(MatchItem matchItem) {
        listOfMatches.remove(matchItem);
        listOfMatches.add(matchItem);
        listViewOfMatches.getSelectionModel().clearAndSelect(listOfMatches.size() - 1);
        matchViewController.updateMatch(matchItem);
        var match = matchService.findMatchById(matchItem.getMatchId(), event);
        matchService.updateMatch(match, matchItem.getWinsPlayer1(), matchItem.getWinsPlayer2());
        eventService.updateMatch(event, match);
        matchService.updateStandingForPlayer(event, match.getPlayer1());
        matchService.updateStandingForPlayer(event, match.getPlayer2());
        updateStandings();
    }

}
