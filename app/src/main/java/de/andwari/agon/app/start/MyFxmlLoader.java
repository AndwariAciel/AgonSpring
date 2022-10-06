package de.andwari.agon.app.start;

import de.andwari.agon.app.controller.FxController;
import de.andwari.agon.app.util.DataBundle;
import de.andwari.agon.core.service.ResourceBundleService;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;

@Component
@RequiredArgsConstructor
public class MyFxmlLoader {

    private final FxWeaver fxWeaver;
    private final ResourceBundleService rbService;

    public FxController loadNewAndWait(FxController source, Class<? extends FxController> controllerClass, DataBundle data) {
        Stage stage = new Stage();
        FxController controller = loadAndInit(stage, source, controllerClass, data);
        stage.showAndWait();
        return controller;
    }

    public FxController loadNew(FxController source, Class<? extends FxController> controllerClass, DataBundle data) {
        Stage stage = new Stage();
        FxController controller = loadAndInit(stage, source, controllerClass, data);
        stage.show();
        return controller;
    }

    public FxController load(Stage stage, FxController source, Class<? extends FxController> controllerClass, DataBundle data) {
        FxController controller = loadAndInit(stage, source, controllerClass, data);
        stage.show();
        return controller;
    }

    public FxController load(Class<? extends FxController> controllerClass, DataBundle data) {
        FxController controller = fxWeaver.load(
                controllerClass,
                rbService.getBundle()
        ).getController();
        controller.setDataAndInit(data);
        return controller;
    }

    private FxController loadAndInit(Stage stage, FxController source, Class<? extends FxController> controllerClass, DataBundle data) {
        var controllerAndView = fxWeaver.load(controllerClass,
                rbService.getBundle());
        var scene = new Scene((Parent) requireNonNull(controllerAndView.getView().orElse(null)));
        stage.setScene(scene);
        controllerAndView.getController().setStageAndSource(stage, source);
        controllerAndView.getController().setDataAndInit(data);
        return controllerAndView.getController();
    }

}
