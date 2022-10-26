package de.andwari.agon.app.item;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MatchItem {

    private Long matchId;
    private boolean finished;
    private String matchNumber;
    private String player1;
    private String player2;
    private Integer winsPlayer1;
    private Integer winsPlayer2;

}
