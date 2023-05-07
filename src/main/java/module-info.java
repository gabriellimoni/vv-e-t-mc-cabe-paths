module com.vvet {
    requires javafx.controls;
    requires javafx.fxml;
    requires guru.nidi.graphviz;
    requires java.desktop;

    opens com.vvet.mccabe to javafx.fxml;
    opens com.vvet.view.controller to javafx.fxml;
    exports com.vvet;

}
