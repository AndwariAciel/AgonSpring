package de.andwari.agon.app.controller;

import static de.andwari.agon.app.controller.views.MatchViewController.CONTROLLER_KEY;
import static de.andwari.agon.app.controller.views.MatchViewController.MATCH_KEY;
import static java.lang.String.format;

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
import de.andwari.agon.business.matcher.SwissMatcher;
import de.andwari.agon.business.service.EventService;
import de.andwari.agon.business.service.MatchService;
import de.andwari.agon.business.service.RoundService;
import de.andwari.agon.core.service.ResourceBundleService;
import de.andwari.agon.model.event.AgonEvent;
import de.andwari.agon.model.player.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@RequiredArgsConstructor
@FxmlView("/pages/event.fxml")
public class EventPageController extends FxController {

    public static final String EVENT_KEY = "event.key";
    private static final String ROUND_RES_KEY = "event.event.roundheadline";

    private AgonEvent event;
    private final SwissMatcher matcher;
    private final MyFxmlLoader loader;
    private final MatchItemMapper matchMapper;
    private final StandingMapper standingMapper;
    private final EventService eventService;
    private final MatchService matchService;
    private final MatchViewController matchViewController;
    private final ResourceBundleService rbService;
    private final RoundService roundService;

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
    public Button btnPrevRound;
    public Button btnNextRound;
    public Button btnFinishRound;

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
        event.getRounds().get(event.getCurrentRound()).getMatches()
                .stream()
                .map(matchMapper::toMatchItem)
                .forEach(listOfMatches::add);
        setByeToFinished();

        updateStandings();

        var dataBundle = DataBundle.create(MATCH_KEY, listOfMatches.get(0));
        dataBundle.addData(CONTROLLER_KEY, this);
        matchViewController.setDataAndInit(dataBundle);

        listViewOfMatches.setOnMouseClicked(event ->
                matchViewController.updateMatch(
                        listViewOfMatches.getSelectionModel().getSelectedItem())
        );

        // Round options
        lbRound.setText(getRoundText());
        setVisibilityOfRoundButtons();
    }

    private void setByeToFinished() {
        listOfMatches.stream()
                .filter(MatchItem::isByeMatch)
                .findFirst()
                .ifPresent(m -> {
                    m.setFinished(true);
                    matchService.updateStandingForPlayer(event, m.getMatch().getPlayer1());
                });
    }

    private void setVisibilityOfRoundButtons() {
        btnPrevRound.setDisable(event.getCurrentRound() == 0);
        btnNextRound.setDisable(event.getCurrentRound() >= event.getRounds().size() - 1);
    }

    private String getRoundText() {
        return format(
                rbService.getBundle().getString(ROUND_RES_KEY),
                event.getCurrentRound() + 1);
    }

    private void updateStandings() {
        listOfStandings.clear();
        listOfStandings.addAll(
                event.getPlayers().stream()
                        .map(Player::getStanding)
                        .map(standingMapper::toItem)
                        .toList()
        );
        listOfStandings.sort(new StandingComparator().reversed());
        for (int i = 1; i <= listOfStandings.size(); i++) {
            listOfStandings.get(i - 1).setRank(Integer.toString(i));
        }

    }

    public void goToPreviousRound(ActionEvent actionEvent) {
    }

    public void goToNextRound(ActionEvent actionEvent) {
    }

    public void finishRound() {
        var matches = matcher.getMatchings(event);
        var nextRound = roundService.createNextRound(matches, event);
        event.getRounds().get(event.getCurrentRound()).setOpen(false);
        event.getRounds().add(nextRound);
        event.setCurrentRound(event.getCurrentRound() + 1);

        DataBundle bundle = DataBundle.empty();
        bundle.addData(EVENT_KEY, event);
        loader.load(
                stage,
                this,
                EventPageController.class,
                bundle
        );
    }

    public void finishEvent(ActionEvent actionEvent) {
    }

    public void reopenEvent(ActionEvent actionEvent) {
    }

    public void revokeRound(ActionEvent actionEvent) {
    }

    public void showDetails(ActionEvent actionEvent) {
        loader.loadNewAndWait(
                this,
                StandingsPageController.class,
                DataBundle.create(
                        StandingsPageController.KEY_STANDINGS,
                        event.getPlayers().stream()
                                .map(Player::getStanding)
                                .toList()
                ));
    }

    public void selectPlayer(ActionEvent actionEvent) {
    }

    public void finishMatch(MatchItem matchItem) {
        listOfMatches.remove(matchItem);
        listOfMatches.add(matchItem);
        listViewOfMatches.getSelectionModel().clearAndSelect(listOfMatches.size() - 1);
        var match = matchService.findMatchById(matchItem.getMatchId(), event);
        matchService.updateResult(match, matchItem.getWinsPlayer1(), matchItem.getWinsPlayer2());
        eventService.updateMatch(event, match);
        matchService.updateStandingForPlayers(event, match);
        updateStandings();
        updateFinishRoundButton();
    }

    private void updateFinishRoundButton() {
        listOfMatches.stream()
                .filter(m -> !m.isFinished())
                .findAny()
                .ifPresentOrElse(
                        m -> btnFinishRound.setDisable(true),
                        () -> btnFinishRound.setDisable(false)
                );
    }

    public void resetMatch(MatchItem matchItem) {
        listOfMatches.remove(matchItem);
        listOfMatches.add(matchItem);
        listViewOfMatches.getSelectionModel().clearAndSelect(listOfMatches.size() - 1);
        var match = matchService.findMatchById(matchItem.getMatchId(), event);
        matchService.resetMatch(match);
        eventService.updateMatch(event, match);
        matchService.updateStandingForPlayers(event, match);
        updateStandings();
    }

    public void dropPlayer(Long playerId) {
        event.getPlayers().stream().filter(p -> p.getId().equals(playerId)).findFirst()
                .ifPresent(p -> p.getStanding().setDropped(true));
        updateStandings();
    }

}
