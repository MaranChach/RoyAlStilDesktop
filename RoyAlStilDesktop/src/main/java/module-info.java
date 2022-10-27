module com.royalstil.royalstildesktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.royalstil.royalstildesktop to javafx.fxml;
    exports com.royalstil.royalstildesktop;
}