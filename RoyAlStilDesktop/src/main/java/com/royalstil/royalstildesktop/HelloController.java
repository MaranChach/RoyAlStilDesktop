package com.royalstil.royalstildesktop;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArrayBase;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    protected TableView tableSex;

    @FXML
    protected void onHelloButtonClick() throws SQLException, IOException {
        ConnectionDB connectionDB = new ConnectionDB();
        ArrayList list = connectionDB.selectQueryArr("select * from \"Main\".sex");
        ObservableList observable = FXCollections.observableArrayList(list);
        tableSex.getColumns().add(list.get(0));
        for (var str : list){
            welcomeText.setText(str.toString());
        }
    }
}