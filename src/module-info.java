module aoetk.tools.javafxeyes {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    exports aoetk.tools.javafxeyes;
    opens aoetk.tools.javafxeyes to javafx.fxml;
}
