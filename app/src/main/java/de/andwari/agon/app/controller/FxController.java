package de.andwari.agon.app.controller;

import de.andwari.agon.app.util.DataBundle;
import javafx.beans.value.ChangeListener;
import javafx.stage.Stage;

public abstract class FxController {

    protected Stage stage;
    protected FxController source;

    public void setStageAndSource(Stage stage, FxController source) {
        this.stage = stage;
        this.source = source;
    }

    public abstract void setDataAndInit(DataBundle data);

    public ChangeListener<Number> getWidthListener() {
        return null;
    }
    public ChangeListener<Number> getHeightListener() {
        return null;
    }
}
