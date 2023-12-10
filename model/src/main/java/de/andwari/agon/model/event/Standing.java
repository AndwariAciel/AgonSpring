package de.andwari.agon.model.event;

import de.andwari.agon.model.player.Player;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Standing {

    private BigDecimal opponentMatchWinPercentage;
    private BigDecimal gameWinPercentage;
    private Integer score;
    private BigDecimal opponentGameWinPercentage;
    private String standingString;
    private Boolean dropped;
    private Player player;
}
