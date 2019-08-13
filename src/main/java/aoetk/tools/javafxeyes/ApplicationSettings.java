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

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.Objects;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Settings of the application.
 */
public class ApplicationSettings {

    /**
     * Initial x origin.
     */
    static final double DEFAULT_STAGE_X = 0.0;

    /**
     * Initial y origin.
     */
    static final double DEFAULT_STAGE_Y = 0.0;

    /**
     * Initial width of a window.
     */
    static final double DEFAULT_WIDTH = 145.0;

    /**
     * Initial height of a window.
     */
    static final double DEFAULT_HEIGHT = 100.0;

    private static final ApplicationSettings INSTANCE = new ApplicationSettings();

    private Preferences prefs;

    private DoubleProperty stageX = new SimpleDoubleProperty(this, "stageX", DEFAULT_STAGE_X);

    private DoubleProperty stageY = new SimpleDoubleProperty(this, "stageY", DEFAULT_STAGE_Y);

    private DoubleProperty stageWidth = new SimpleDoubleProperty(this, "stageWidth", DEFAULT_WIDTH);

    private DoubleProperty stageHeight = new SimpleDoubleProperty(this, "stageHeight", DEFAULT_HEIGHT);

    private ApplicationSettings() {
        prefs = Preferences.userNodeForPackage(ApplicationSettings.class);
        restore();
    }

    /**
     * Save settings.
     *
     * @throws BackingStoreException Fails to save settings.
     */
    public void save() throws BackingStoreException {
        Objects.requireNonNull(prefs);
        prefs.putDouble(stageX.getName(), stageX.get());
        prefs.putDouble(stageY.getName(), stageY.get());
        prefs.putDouble(stageWidth.getName(), stageWidth.get());
        prefs.putDouble(stageHeight.getName(), stageHeight.get());
        prefs.flush();
    }

    private void restore() {
        Objects.requireNonNull(prefs);
        stageX.set(prefs.getDouble(stageX.getName(), stageX.get()));
        stageY.set(prefs.getDouble(stageY.getName(), stageY.get()));
        stageWidth.set(prefs.getDouble(stageWidth.getName(), stageWidth.get()));
        stageHeight.set(prefs.getDouble(stageHeight.getName(), stageHeight.get()));
    }

    public static ApplicationSettings getInstance() {
        return INSTANCE;
    }

    public final double getStageX() {
        return stageX.get();
    }

    public DoubleProperty stageXProperty() {
        return stageX;
    }

    public final void setStageX(double stageX) {
        this.stageX.set(stageX);
    }

    public final double getStageY() {
        return stageY.get();
    }

    public DoubleProperty stageYProperty() {
        return stageY;
    }

    public final void setStageY(double stageY) {
        this.stageY.set(stageY);
    }

    public double getStageWidth() {
        return stageWidth.get();
    }

    public DoubleProperty stageWidthProperty() {
        return stageWidth;
    }

    public void setStageWidth(double stageWidth) {
        this.stageWidth.set(stageWidth);
    }

    public double getStageHeight() {
        return stageHeight.get();
    }

    public DoubleProperty stageHeightProperty() {
        return stageHeight;
    }

    public void setStageHeight(double stageHeight) {
        this.stageHeight.set(stageHeight);
    }

}
