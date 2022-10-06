package de.andwari.agon.app.item;

import javafx.beans.property.SimpleStringProperty;

public class SeatingsItem {

    private long playerId;
    private final SimpleStringProperty name;
    private final SimpleStringProperty seatingNumber;

    public SeatingsItem() {
        this.name = new SimpleStringProperty();
        this.seatingNumber = new SimpleStringProperty();
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSeatingNumber() {
        return seatingNumber.get();
    }

    public void setSeatingNumber(String seatingNumber) {
        this.seatingNumber.set(seatingNumber);
    }

    public SimpleStringProperty name() {
        return name;
    }

    public SimpleStringProperty seatingNumber() {
        return seatingNumber;
    }
}
