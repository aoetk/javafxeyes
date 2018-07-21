package aoetk.tools.javafxeyes;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Ellipse;

import java.io.IOException;

public class Eye extends StackPane {

    @FXML
    private Ellipse outerEyeball;

    @FXML
    private Ellipse innerEyeball;

    @FXML
    private Ellipse eye;

    public Eye() throws IOException {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("Eye.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
        initBind();
    }

    /**
     * Update a position of an eye.
     *
     * @param mouseX Relative x coordinate from this component
     * @param mouseY Relative y coordinate from this component
     */
    public void updateEyePosition(double mouseX, double mouseY) {
        double localMouseX = mouseX - (this.getWidth() / 2.0);
        double localMouseY = mouseY - (this.getHeight() / 2.0);
        computeEyePosition(localMouseX, localMouseY);
    }

    private void computeEyePosition(double localMouseX, double localMouseY) {
        double parameter = Math.atan2(localMouseY, localMouseX);
        final double width = this.getWidth();
        final double height = this.getHeight();

        double eyeX = width / 4.0 * Math.cos(parameter);
        if (Math.abs(localMouseX) < Math.abs(eyeX)) {
            eyeX = localMouseX;
        }
        double eyeY = height / 4.0 * Math.cos(parameter);
        if ((Math.abs(localMouseY) < Math.abs(eyeY))) {
            eyeY = localMouseY;
        }
        eye.setTranslateX(eyeX);
        eye.setTranslateY(eyeY);
    }

    private void initBind() {
        outerEyeball.radiusXProperty().bind(this.widthProperty().divide(2));
        outerEyeball.radiusYProperty().bind(this.heightProperty().divide(2));
        innerEyeball.radiusXProperty().bind(this.widthProperty().divide(2).multiply(0.8));
        innerEyeball.radiusYProperty().bind(this.heightProperty().divide(2).multiply(0.8));
        eye.radiusXProperty().bind(this.widthProperty().divide(2).multiply(0.18));
        eye.radiusYProperty().bind(this.heightProperty().divide(2).multiply(0.18));
    }

}
