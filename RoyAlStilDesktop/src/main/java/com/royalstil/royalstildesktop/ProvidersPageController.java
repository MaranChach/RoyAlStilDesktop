package com.royalstil.royalstildesktop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class ProvidersPageController extends ElementController{



    @FXML
    private Button deleteButton;

    @FXML
    private Label idLabel;

    @FXML
    private TextField nameField;

    @FXML
    private TextField innField;

    @FXML
    private AnchorPane notificationPane;

    @FXML
    private AnchorPane parentPane;

    @FXML
    private Button saveButton;

    @FXML
    private Button updateButton;

    @FXML
    private void initialize(){

    }

    @Override
    public void setSelectedRow(HashMap<String, String> selectedRow){
        id = Integer.parseInt(selectedRow.get("ID"));
        nameField.setText(selectedRow.get("Наименование"));
        innField.setText(selectedRow.get("ИНН"));
        updateButton.setDisable(false);
        deleteButton.setDisable(false);
    }

    @FXML
    void onAddButtonClick(ActionEvent event) throws SQLException, IOException {
        id = connection.sendQueryWithId("INSERT INTO \"Main\".provider (name, inn) VALUES ('" + nameField.getText() + "', '" + innField.getText() + "') RETURNING id_provider");
        updateButton.setDisable(false);
        deleteButton.setDisable(false);
        newNotification(notificationPane);
        mainClass.onButtonProvidersClick(new ActionEvent());
    }

    @FXML
    void onDeleteButtonClick(ActionEvent event) throws SQLException, IOException {
        connection.sendQuery("DELETE FROM \"Main\".provider WHERE id_provider = " + id);
        mainClass.onButtonProvidersClick(new ActionEvent());
        idLabel.getScene().getWindow().hide();
    }

    @FXML
    void onUpdateButtonClick(ActionEvent event) throws SQLException, IOException {
        id = connection.sendQueryWithId("UPDATE \"Main\".provider SET name = '" + nameField.getText() + "', inn = '" + innField.getText() + "' WHERE id_provider = " + id + " RETURNING id_provider");
        newNotification(notificationPane);
        mainClass.onButtonProvidersClick(new ActionEvent());
    }
}
