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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Window;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class JavaFXEyesController implements Initializable {

    private static final double RESIZE_MARGIN = 10.0;

    @FXML
    private HBox rootPane;

    @FXML
    private Eye leftEye;

    @FXML
    private Eye rightEye;

    private AnimationTimer timer;

    private ContextMenu contextMenu;

    private InitWindowState initWindowState;

    private InitMouseState initMouseState;

    private EventType eventType = EventType.NONE;

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
        rootPane.setOnMouseMoved(this::changeMouseCursor);
        rootPane.setOnMouseExited(event -> rootPane.setCursor(Cursor.DEFAULT));
        rootPane.setOnMousePressed(event -> {
            timer.stop();
            final Window window = getWindow();
            initWindowState = new InitWindowState(window.getX(), window.getY(), window.getWidth(), window.getHeight());
            initMouseState = new InitMouseState(event.getScreenX(), event.getScreenY());
            if (rootPane.getCursor() == Cursor.OPEN_HAND) {
                rootPane.setCursor(Cursor.CLOSED_HAND);
                eventType = EventType.MOVE;
            } else if (rootPane.getCursor() == Cursor.H_RESIZE) {
                eventType = EventType.WIDTH_RESIZE;
            } else if (rootPane.getCursor() == Cursor.V_RESIZE) {
                eventType = EventType.HEIGHT_RESIZE;
            } else if (rootPane.getCursor() == Cursor.NE_RESIZE
                    || rootPane.getCursor() == Cursor.NW_RESIZE
                    || rootPane.getCursor() == Cursor.SE_RESIZE
                    || rootPane.getCursor() == Cursor.SW_RESIZE) {
                eventType = EventType.RESIZE;
            }
            event.consume();
        });
        rootPane.setOnMouseDragged(event -> {
            switch (eventType) {
                case MOVE:
                    moveWindow(event);
                    break;
                case RESIZE:
                    resizeWindow(event);
                    break;
                case HEIGHT_RESIZE:
                    resizeWindowHeight(event);
                    break;
                case WIDTH_RESIZE:
                    resizeWindowWidth(event);
                    break;
            }
            event.consume();
        });
        rootPane.setOnMouseReleased(event -> {
            timer.start();
            if (rootPane.getCursor() == Cursor.CLOSED_HAND) {
                rootPane.setCursor(Cursor.OPEN_HAND);
            }
            initWindowState = null;
            initMouseState = null;
            eventType = EventType.NONE;
        });
        rootPane.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(getWindow(), event.getScreenX(), event.getScreenY());
            }
        });
    }

    private void resizeWindowHeight(MouseEvent event) {
        final Window window = getWindow();
        window.setHeight(initWindowState.height + event.getScreenY() - initMouseState.screenY);
    }

    private void resizeWindowWidth(MouseEvent event) {
        final Window window = getWindow();
        window.setWidth(initWindowState.width + event.getScreenX() - initMouseState.screenX);
    }

    private void resizeWindow(MouseEvent event) {
        final Window window = getWindow();
        window.setWidth(initWindowState.width + event.getScreenX() - initMouseState.screenX);
        window.setHeight(initWindowState.height + event.getScreenY() - initMouseState.screenY);
    }

    private void moveWindow(MouseEvent event) {
        final Window window = getWindow();
        window.setX(initWindowState.x + event.getScreenX() - initMouseState.screenX);
        window.setY(initWindowState.y + event.getScreenY() - initMouseState.screenY);
    }

    private void changeMouseCursor(MouseEvent event) {
        final double sceneX = event.getSceneX();
        final double sceneY = event.getSceneY();
        if (sceneX <= RESIZE_MARGIN) {
            if (sceneY <= RESIZE_MARGIN) {
                rootPane.setCursor(Cursor.NW_RESIZE);
            } else if (sceneY > RESIZE_MARGIN && sceneY <= rootPane.getHeight() - RESIZE_MARGIN) {
                rootPane.setCursor(Cursor.H_RESIZE);
            } else {
                rootPane.setCursor(Cursor.SW_RESIZE);
            }
        } else if (sceneX > RESIZE_MARGIN && sceneX <= rootPane.getWidth() - RESIZE_MARGIN) {
            if (sceneY <= RESIZE_MARGIN) {
                rootPane.setCursor(Cursor.V_RESIZE);
            } else if (sceneY > RESIZE_MARGIN && sceneY <= rootPane.getHeight() - RESIZE_MARGIN) {
                rootPane.setCursor(Cursor.OPEN_HAND);
            } else {
                rootPane.setCursor(Cursor.V_RESIZE);
            }
        } else {
            if (sceneY <= RESIZE_MARGIN) {
                rootPane.setCursor(Cursor.NE_RESIZE);
            } else if (sceneY > RESIZE_MARGIN && sceneY <= rootPane.getHeight() - RESIZE_MARGIN) {
                rootPane.setCursor(Cursor.H_RESIZE);
            } else {
                rootPane.setCursor(Cursor.SE_RESIZE);
            }
        }
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

    private class InitWindowState {

        private final double x;

        private final double y;

        private final double width;

        private final double height;

        private InitWindowState(double x, double y, double width, double height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }

    private class InitMouseState {

        private final double screenX;

        private final double screenY;

        private InitMouseState(double screenX, double screenY) {
            this.screenX = screenX;
            this.screenY = screenY;
        }
    }

    private enum EventType {
        MOVE, RESIZE, HEIGHT_RESIZE, WIDTH_RESIZE, NONE
    }

}
