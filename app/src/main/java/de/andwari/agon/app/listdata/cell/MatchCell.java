package de.andwari.agon.app.listdata.cell;

import de.andwari.agon.app.controller.views.MatchItemController;
import de.andwari.agon.app.item.MatchItem;
import de.andwari.agon.app.start.MyFxmlLoader;
import de.andwari.agon.app.util.DataBundle;
import javafx.scene.control.ListCell;
import lombok.RequiredArgsConstructor;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
public class MatchCell extends ListCell<MatchItem> {

    private final MyFxmlLoader loader;

    @Override
    protected void updateItem(MatchItem matchItem, boolean empty) {
        super.updateItem(matchItem, empty);
        if(!empty && nonNull(matchItem)) {
            MatchItemController itemController = (MatchItemController) loader.load(MatchItemController.class,
                    DataBundle.create(MatchItemController.MATCH_KEY, matchItem));
            setGraphic(itemController.getBox());
        }
    }
}
