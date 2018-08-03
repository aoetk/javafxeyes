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

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.stage.Window;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class JavaFXEyesController implements Initializable {

    @FXML
    private HBox rootPane;

    @FXML
    private Eye leftEye;

    @FXML
    private Eye rightEye;

    private AnimationTimer timer;

    private ContextMenu contextMenu;

    private double diffMouseX;

    private double diffMouseY;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateMousePosition();
            }
        };
        timer.start();
        contextMenu = createContextMenu();
        addListeners();
    }

    private ContextMenu createContextMenu() {
        final MenuItem exitMenu = new MenuItem("Quit JavaFXEyes");
        exitMenu.setOnAction(event -> {
            timer.stop();
            Platform.exit();
        });
        return new ContextMenu(exitMenu);
    }

    private void addListeners() {
        rootPane.setOnMouseEntered(event -> rootPane.setCursor(Cursor.OPEN_HAND));
        rootPane.setOnMouseExited(event -> rootPane.setCursor(Cursor.DEFAULT));
        rootPane.setOnMousePressed(event -> {
            timer.stop();
            final double screenX = event.getScreenX();
            final double screenY = event.getScreenY();
            final Window window = getWindow();
            diffMouseX = screenX - window.getX();
            diffMouseY = screenY - window.getY();
            rootPane.setCursor(Cursor.CLOSED_HAND);
            event.consume();
        });
        rootPane.setOnMouseDragged(event -> {
            final Window window = getWindow();
            window.setX(event.getScreenX() - diffMouseX);
            window.setY(event.getScreenY() - diffMouseY);
            event.consume();
        });
        rootPane.setOnMouseReleased(event -> {
            timer.start();
            rootPane.setCursor(Cursor.OPEN_HAND);
        });
        rootPane.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(getWindow(), event.getScreenX(), event.getScreenY());
            }
        });
    }

    private void updateMousePosition() {
        SwingUtilities.invokeLater(() -> {
            final Point pointerLocation = MouseInfo.getPointerInfo().getLocation();
            Platform.runLater(() -> updateEye(pointerLocation.x, pointerLocation.y));
        });
    }

    private void updateEye(int mouseX, int mouseY) {
        final Window window = getWindow();
        final double windowX = window.getX();
        final double windowY = window.getY();
        final double titleHeight = window.getHeight() - rootPane.getHeight();
        leftEye.updateEyePosition(mouseX - windowX, mouseY - windowY - titleHeight);
        rightEye.updateEyePosition(mouseX - windowX - leftEye.getWidth() - 5, mouseY - windowY - titleHeight);
    }

    private Window getWindow() {
        final Scene scene = rootPane.getScene();
        return scene.getWindow();
    }

}
