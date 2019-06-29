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

    private static final ApplicationSettings INSTANCE = new ApplicationSettings();

    private Preferences prefs;

    private DoubleProperty stageX = new SimpleDoubleProperty(this, "stageX", 0.0);

    private DoubleProperty stageY = new SimpleDoubleProperty(this, "stageY", 0.0);

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
        prefs.flush();
    }

    private void restore() {
        Objects.requireNonNull(prefs);
        stageX.set(prefs.getDouble(stageX.getName(), stageX.get()));
        stageY.set(prefs.getDouble(stageY.getName(), stageY.get()));
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

}