package de.andwari.agon.app.start;

import static java.util.Objects.requireNonNull;

import de.andwari.agon.app.controller.FxController;
import de.andwari.agon.app.util.DataBundle;
import java.util.Locale;
import java.util.ResourceBundle;

import de.andwari.agon.core.service.ResourceBundleService;
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
    private final ResourceBundleService rbService;

    public FxController loadNewAndWait(Class<? extends FxController> controllerClass, DataBundle data) {
        Stage stage = new Stage();
        FxController controller = loadAndInit(stage, controllerClass, data);
        stage.showAndWait();
        return controller;
    }

    public FxController loadNew(Class<? extends FxController> controllerClass, DataBundle data) {
        Stage stage = new Stage();
        FxController controller = loadAndInit(stage, controllerClass, data);
        stage.show();
        return controller;
    }

    public FxController load(Stage stage, Class<? extends FxController> controllerClass, DataBundle data) {
        FxController controller = loadAndInit(stage, controllerClass, data);
        stage.show();
        return controller;
    }

    private FxController loadAndInit(Stage stage, Class<? extends FxController> controllerClass, DataBundle data) {
        var controllerAndView = fxWeaver.load(controllerClass,
                rbService.getBundle());
        var scene = new Scene((Parent) requireNonNull(controllerAndView.getView().orElse(null)));
        stage.setScene(scene);
        controllerAndView.getController().setDataAndInit(stage, data);
        return controllerAndView.getController();
    }

}
