module com.example.lab4_mapv2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    //requires org.testng;
    requires org.junit.jupiter.api;
    //requires junit;
    requires java.sql;
    requires junit;
    requires org.testng;
    //requires org.xerial.sqlitejdbc;

    opens com.example.lab4_mapv2 to javafx.fxml;
    exports com.example.lab4_mapv2;
}