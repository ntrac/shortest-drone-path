module trac {
    requires javafx.controls;
    requires javafx.fxml;

    opens trac to javafx.fxml;
    exports trac;
}
