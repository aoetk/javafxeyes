package aoetk.tools.javafxeyes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        final HBox base = FXMLLoader.load(getClass().getResource("JavaFXEyes.fxml"));
        primaryStage.setTitle("JavaFXEyes");
        primaryStage.setScene(new Scene(base, Color.TRANSPARENT));
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
