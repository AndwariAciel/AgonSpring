package de.andwari.agon.app.start;

import static java.util.Objects.requireNonNull;

import de.andwari.agon.app.controller.FxController;
import de.andwari.agon.app.util.DataBundle;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyFxmlLoader {

    private final FxWeaver fxWeaver;

    public FxController loadNew(Class<? extends FxController> controllerClass) {
        return load(new Stage(), controllerClass);
    }

    public FxController load(Stage stage, Class<? extends FxController> controllerClass) {
        var controllerAndView = fxWeaver.load(controllerClass,
                ResourceBundle.getBundle("lang.lang", new Locale("DE")));
        controllerAndView.getController().setDataAndInit(stage, DataBundle.empty());
        var scene = new Scene((Parent) requireNonNull(controllerAndView.getView().orElse(null)));
        stage.setScene(scene);
        stage.show();
        return controllerAndView.getController();
    }

}
