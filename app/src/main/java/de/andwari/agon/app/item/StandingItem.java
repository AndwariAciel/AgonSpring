package de.andwari.agon.app.item;

import javafx.beans.property.SimpleStringProperty;

public class StandingItem {

    private Long standingId;
    private SimpleStringProperty rank;
    private SimpleStringProperty player;

    private SimpleStringProperty score; // matchWinPercentage
    private SimpleStringProperty opponentMatchWinPercentage;
    private SimpleStringProperty gameWinPercentage;
    private SimpleStringProperty opponentGameWinPercentage;

    private SimpleStringProperty standingString;
    private SimpleStringProperty rankingPoints;
    private SimpleStringProperty booster;
    private boolean dropped;

    public String getRank() {
        return rank.get();
    }

    public void setRank(String rank) {
        this.rank = new SimpleStringProperty(rank);
    }

    public SimpleStringProperty getRankProperty() {
        return rank;
    }

    public String getPlayer() {
        return player.get();
    }

    public void setPlayer(String player) {
        this.player = new SimpleStringProperty(player);
    }

    public SimpleStringProperty getPlayerProperty() {
        return player;
    }

    public String getScore() {
        return score.get();
    }

    public void setScore(String score) {
        this.score = new SimpleStringProperty(score);
    }

    public SimpleStringProperty getScoreProperty() {
        return score;
    }

    public String getOpponentGameWinPercentage() {
        return opponentGameWinPercentage.get();
    }

    public void setOpponentGameWinPercentage(String opponentGameWinPercentage) {
        this.opponentGameWinPercentage = new SimpleStringProperty(opponentGameWinPercentage);
    }

    public SimpleStringProperty getOpponentGameWinPercentageProperty() {
        return opponentGameWinPercentage;
    }

    public String getGameWinPercentage() {
        return gameWinPercentage.get();
    }

    public void setGameWinPercentage(String gameWinPercentage) {
        this.gameWinPercentage = new SimpleStringProperty(gameWinPercentage);
    }

    public SimpleStringProperty getMatchWinPercentageProperty() {
        return gameWinPercentage;
    }

    public String getOpponentMatchWinPercentage() {
        return opponentMatchWinPercentage.get();
    }

    public void setOpponentMatchWinPercentage(String opponentMatchWinPercentage) {
        this.opponentMatchWinPercentage = new SimpleStringProperty(opponentMatchWinPercentage);
    }

    public SimpleStringProperty getOpponentMatchWinPercentageProperty() {
        return opponentMatchWinPercentage;
    }

    public String getRankingPoints() {
        return rankingPoints.get();
    }

    public void setRankingPoints(String rankingPoints) {
        this.rankingPoints = new SimpleStringProperty(rankingPoints);
    }

    public SimpleStringProperty getRankingPointsProperty() {
        return rankingPoints;
    }

    public String getBooster() {
        return booster.get();
    }

    public void setBooster(String booster) {
        this.booster= new SimpleStringProperty(booster);
    }

    public SimpleStringProperty getBoosterProperty() {
        return booster;
    }

    public Long getStandingId() {
        return standingId;
    }

    public void setStandingId(Long standingId) {
        this.standingId = standingId;
    }

    public String getStandingString() {
        return standingString.get();
    }

    public void setStandingString(String standingString) {
        this.standingString = new SimpleStringProperty(standingString);
    }

    public SimpleStringProperty getStandingStringProperty() {
        return standingString;
    }

    public boolean isDropped() {
        return dropped;
    }

    public void setDropped(boolean dropped) {
        this.dropped = dropped;
    }
}
