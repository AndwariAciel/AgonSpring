package de.andwari.agon.app.players.controller;

import de.andwari.agon.business.player.PlayerService;
import de.andwari.agon.model.player.Player;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/pages/player-page.fxml")
public class PlayerPageController {

    @Autowired
    private PlayerService playerService;

    public TextField tfPlayername;
    public TextField tfDciNumber;
    public CheckBox cbMember;
    public Label lbWarning;

    public void addPlayer() {
        playerService.addPlayer(
                Player.builder().name(tfPlayername.getText()).dci(tfDciNumber.getText())
                        .member(cbMember.isSelected()).build()
        );
    }

    public void deletePlayer() {
    }
}
