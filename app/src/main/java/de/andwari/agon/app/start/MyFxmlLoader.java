package de.andwari.agon.app.start;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.ResourceBundle;

@Component
public class MyFxmlLoader {

    @Autowired
    private FxWeaver fxWeaver;

    public void load(Stage stage, Class<? extends Object> controllerClass) {
        Parent root = fxWeaver.loadView(controllerClass, ResourceBundle.getBundle("lang.lang", new Locale("DE")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
