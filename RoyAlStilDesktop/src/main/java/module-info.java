module com.royalstil.royalstildesktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.mail;
    requires activation;


    opens com.royalstil.royalstildesktop to javafx.fxml;
    exports com.royalstil.royalstildesktop;
}