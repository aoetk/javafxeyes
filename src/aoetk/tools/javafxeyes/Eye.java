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

    private void initBind() {
        outerEyeball.radiusXProperty().bind(this.widthProperty().divide(2));
        outerEyeball.radiusYProperty().bind(this.heightProperty().divide(2));
        innerEyeball.radiusXProperty().bind(this.widthProperty().divide(2).multiply(0.8));
        innerEyeball.radiusYProperty().bind(this.heightProperty().divide(2).multiply(0.8));
        eye.radiusXProperty().bind(this.widthProperty().divide(2).multiply(0.18));
        eye.radiusYProperty().bind(this.heightProperty().divide(2).multiply(0.18));
    }

}
