package de.andwari.agon.app.listdata.cell;

import de.andwari.agon.app.item.StandingItem;
import javafx.scene.control.TableCell;
import javafx.scene.paint.Color;

public class StandingCell extends TableCell<StandingItem, String> {

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            return;
        }
        setText(item);
        StandingItem ranking = getTableRow().getItem();
        if (ranking != null && ranking.isDropped()) {
            setTextFill(Color.GRAY);
        } else if (ranking != null) {
            setTextFill(Color.BLACK);
        }
    }
}
