package de.andwari.agon.app.item;

import javafx.beans.property.SimpleStringProperty;

public class PlayerItem {
    private long id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty dci;
    private final SimpleStringProperty member;
    private boolean isMember;

    public PlayerItem() {
        name = new SimpleStringProperty();
        dci = new SimpleStringProperty();
        member = new SimpleStringProperty();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDci() {
        return dci.get();
    }

    public void setDci(String dci) {
        this.dci.set(dci);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleStringProperty dciProperty() {
        return dci;
    }

    public SimpleStringProperty memberProperty() {
        return member;
    }

    public void setMember(boolean member) {
        isMember = member;
        if (isMember) {
            this.member.set("\u2714");
        } else {
            this.member.set("\u2718");
        }
    }

    public Boolean getMember() {
        return isMember;
    }

    public String getComparableName() {
        return getName().toLowerCase();
    }
}
