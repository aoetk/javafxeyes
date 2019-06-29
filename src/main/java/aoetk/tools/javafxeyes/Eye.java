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
        double localMouseX = mouseX - outerEyeball.getRadiusX();
        double localMouseY = mouseY - outerEyeball.getRadiusY();
        computeEyePosition(localMouseX, localMouseY);
    }

    private void computeEyePosition(double localMouseX, double localMouseY) {
        double parameter = Math.atan2(localMouseY, localMouseX);

        double eyeX = outerEyeball.getRadiusX() / 2.0 * Math.cos(parameter);
        if (Math.abs(localMouseX) < Math.abs(eyeX)) {
            eyeX = localMouseX;
        }
        double eyeY = outerEyeball.getRadiusY() / 2.0 * Math.sin(parameter);
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
