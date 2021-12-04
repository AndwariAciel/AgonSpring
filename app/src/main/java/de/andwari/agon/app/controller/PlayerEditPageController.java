package de.andwari.agon.app.controller;

import de.andwari.agon.app.util.DataBundle;
import de.andwari.agon.business.player.PlayerService;
import de.andwari.agon.model.player.Player;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FxmlView("/pages/popups/player-edit.fxml")
public class PlayerEditPageController implements FxController {

    public static final String PLAYER_KEY = "player-key";

    private final PlayerService playerService;

    @FXML
    private TextField tfName;
    @FXML
    private TextField tfDci;
    @FXML
    private CheckBox cbMember;

    private Player player;

    @Override
    public void setDataAndInit(Stage stage, DataBundle data) {
        player = (Player) data.getData(PLAYER_KEY).orElseThrow(
                () -> new IllegalArgumentException("Player must be provided!")
        ).getValue();
        tfName.setText(player.getName());
        tfDci.setText(player.getDci());
        cbMember.setSelected(player.getMember());
    }

    public void save() {
        player.setName(tfName.getText());
        player.setDci(tfDci.getText());
        player.setMember(cbMember.isSelected());
        playerService.updatePlayer(player);
        Stage stage = (Stage) tfName.getScene().getWindow();
        stage.close();
    }

    public Player getPlayer() {
        return player;
    }
}
