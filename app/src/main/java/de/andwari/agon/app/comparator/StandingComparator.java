package de.andwari.agon.app.comparator;

import de.andwari.agon.app.item.StandingItem;

import java.util.Comparator;

public class StandingComparator implements Comparator<StandingItem> {

    @Override
    public int compare(StandingItem s1, StandingItem s2) {
        if (s1.getScore().equals(s2.getScore())) {
            if (s1.getOpponentMatchWinPercentage().equals(s2.getOpponentMatchWinPercentage())) {
                if (s1.getGameWinPercentage().equals(s2.getGameWinPercentage())) {
                    if (s1.getOpponentGameWinPercentage().equals(s2.getOpponentGameWinPercentage())) {
                        return - s1.getPlayer().toLowerCase().compareTo(s2.getPlayer().toLowerCase());
                    }
                    return - s1.getOpponentGameWinPercentage().compareTo(s2.getOpponentGameWinPercentage());
                }
                return - s1.getGameWinPercentage().compareTo(s2.getGameWinPercentage());
            }
            return - s1.getOpponentMatchWinPercentage().compareTo(s2.getOpponentMatchWinPercentage());
        }
        return - s1.getScore().compareTo(s2.getScore());
    }
}
