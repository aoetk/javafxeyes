package aoetk.tools.javafxeyes;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Window;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class JavaFXEyesController implements Initializable {

    @FXML
    private Eye leftEye;

    @FXML
    private Eye rightEye;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateMousePosition();
            }
        };
        timer.start();
    }

    private void updateMousePosition() {
        SwingUtilities.invokeLater(() -> {
            final Point pointerLocation = MouseInfo.getPointerInfo().getLocation();
            Platform.runLater(() -> updateEye(pointerLocation.x, pointerLocation.y));
        });
    }

    private void updateEye(int mouseX, int mouseY) {
        final Scene scene = leftEye.getScene();
        final Window window = scene.getWindow();
        final double windowX = window.getX();
        final double windowY = window.getY();
        final double titleHeight = window.getHeight() - scene.getHeight();
        leftEye.updateEyePosition(mouseX - windowX, mouseY - windowY - titleHeight);
        rightEye.updateEyePosition(mouseX - windowX - leftEye.getWidth() - 5, mouseY - windowY - titleHeight);
    }

}
