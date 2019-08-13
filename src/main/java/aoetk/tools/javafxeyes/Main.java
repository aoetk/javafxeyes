/*
 * Copyright 2018 Takashi AOE
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
        restoreSettings(primaryStage);
        primaryStage.show();
    }

    private void restoreSettings(Stage primaryStage) {
        final ApplicationSettings settings = ApplicationSettings.getInstance();
        primaryStage.setX(settings.getStageX());
        primaryStage.setY(settings.getStageY());
        primaryStage.setWidth(settings.getStageWidth());
        primaryStage.setHeight(settings.getStageHeight());
        settings.stageXProperty().bind(primaryStage.xProperty());
        settings.stageYProperty().bind(primaryStage.yProperty());
        settings.stageWidthProperty().bind(primaryStage.widthProperty());
        settings.stageHeightProperty().bind(primaryStage.heightProperty());
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        final ApplicationSettings settings = ApplicationSettings.getInstance();
        settings.save();
    }

}
