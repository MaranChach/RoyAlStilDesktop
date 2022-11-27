package com.royalstil.royalstildesktop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class ClientPageController extends ElementController{


    //region fields


    private ConnectionDB connection;

    @FXML
    private AnchorPane notificationPane;

    @FXML
    private AnchorPane parentPane;

    @FXML
    private TextField birthdayField;

    @FXML
    private Button deleteButton;

    @FXML
    private Button updateButton;

    @FXML
    private TextField emailField;

    @FXML
    private TextField firstNameField;

    @FXML
    private Label idLabel;

    @FXML
    private TableView<?> ownOrdersTable;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private Button saveButton;

    @FXML
    private TextField secondNameField;
    //endregion

    @FXML
    private void initialize(){
        connection = new ConnectionDB();
    }

    @Override
    public void setSelectedRow(HashMap<String, String> selectedRow){
        this.selectedRow = selectedRow;
        updateButton.setDisable(false);
        deleteButton.setDisable(false);
        secondNameField.setText(selectedRow.get("second_name"));
        firstNameField.setText(selectedRow.get("first_name"));
        birthdayField.setText(selectedRow.get("birth_date"));
        emailField.setText(selectedRow.get("email"));
        phoneNumberField.setText(selectedRow.get("phone_number"));
        id = Integer.parseInt(selectedRow.get("id_client"));
        idLabel.setText("Клиент № " + id);
    }

    public void onAddButtonClick(ActionEvent event) throws SQLException, IOException {
        String query = "INSERT INTO \"Main\".clients (second_name, first_name, login, password, phone_number, email, birth_date) VALUES" +
                " ('" +
                secondNameField.getText() +
                "', " +
                "'" +
                firstNameField.getText() +
                "', " +
                "'" +
                new Random().nextInt(100000, 999999) +
                "', " +
                "'" +
                new Random().nextInt(10000000, 99999999) +
                "', " +
                "'" +
                phoneNumberField.getText() +
                "', " +
                "'" +
                emailField.getText() +
                "', " +
                "'" +
                birthdayField.getText() +
                "') RETURNING id_client";

        id = connection.sendQueryWithId(query);
        idLabel.setText("Клиент № " + id);
        newNotification(notificationPane);
    }


    public void onDeleteButtonClick(ActionEvent event) throws SQLException, IOException {
        connection.sendQuery("DELETE FROM \"Main\".clients WHERE " +
                "id_client = " +
                id);
        newNotification(notificationPane);
        firstNameField.getScene().getWindow().hide();
    }

    public void onUpdateButtonClick(ActionEvent event) throws SQLException, IOException {
        String query = "UPDATE \"Main\".clients SET" +
                " second_name = '" +
                secondNameField.getText() +
                "', first_name = " +
                "'" +
                firstNameField.getText() +
                "', phone_number = " +
                "'" +
                phoneNumberField.getText() +
                "', email = " +
                "'" +
                emailField.getText() +
                "', birth_date = " +
                "'" +
                birthdayField.getText() +
                "' " +
                "WHERE id_client = " +
                id +
                "RETURNING id_client";
        connection.sendQuery(query);
        newNotification(notificationPane);
    }
}
