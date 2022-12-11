package com.royalstil.royalstildesktop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;
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

    @FXML
    private Label ownOrdersLabel;
    //endregion

    @FXML
    private void initialize(){
        connection = new ConnectionDB();
        //setFonts();
    }

    @FXML
    private void setFonts(){
        Fonts fonts = new Fonts();
        firstNameField.setFont(fonts.mainFont);
        secondNameField.setFont(fonts.mainFont);
        birthdayField.setFont(fonts.mainFont);
        emailField.setFont(fonts.mainFont);
        phoneNumberField.setFont(fonts.mainFont);
        idLabel.setFont(fonts.mainFont);
        ownOrdersLabel.setFont(fonts.mainFont);
        deleteButton.setFont(fonts.miniFont);
        updateButton.setFont(fonts.miniFont);
        saveButton.setFont(fonts.miniFont);
    }

    @Override
    public void setSelectedRow(HashMap<String, String> selectedRow){
        this.selectedRow = selectedRow;
        updateButton.setDisable(false);
        deleteButton.setDisable(false);
        secondNameField.setText(selectedRow.get("Фамилия"));
        firstNameField.setText(selectedRow.get("Имя"));
        birthdayField.setText(selectedRow.get("Дата рождения"));
        emailField.setText(selectedRow.get("Почта"));
        phoneNumberField.setText(selectedRow.get("Номер телефона"));
        id = Integer.parseInt(selectedRow.get("ID"));
        idLabel.setText("Клиент № " + id);


        try {
            ConnectionDB.fillTable(ownOrdersTable, "SELECT id_order, passed, second_name, first_name, email, date FROM \"Main\".orders INNER JOIN \"Main\".clients ON client = id_client WHERE client = " + id);
            ownOrdersTable.getColumns().get(0).setText("ID");
            ownOrdersTable.getColumns().get(1).setText("Проведён");
            ownOrdersTable.getColumns().get(2).setText("Фамилия");
            ownOrdersTable.getColumns().get(3).setText("Имя");
            ownOrdersTable.getColumns().get(4).setText("Почта");
            ownOrdersTable.getColumns().get(5).setText("Дата");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        mainClass.onButtonClientsClick(new ActionEvent());
    }

    public void onDeleteButtonClick(ActionEvent event) throws SQLException, IOException {
        if(!confirmAction()){
            return;
        }
        connection.sendQuery("DELETE FROM \"Main\".clients WHERE " +
                "id_client = " +
                id);
        newNotification(notificationPane);
        mainClass.onButtonClientsClick(new ActionEvent());
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
        mainClass.onButtonClientsClick(new ActionEvent());
    }
}
