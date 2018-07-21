package aoetk.tools.javafxeyes;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("JavaFXEyes");
        primaryStage.setScene(new Scene(new Eye(), Color.TRANSPARENT));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
