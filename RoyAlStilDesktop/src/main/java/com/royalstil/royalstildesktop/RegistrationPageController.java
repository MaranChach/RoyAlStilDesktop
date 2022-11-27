package com.royalstil.royalstildesktop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class RegistrationPageController {


    private boolean codeConfirmed = false;

    private boolean loginConfirmed = false;

    private ConnectionDB connection = new ConnectionDB();

    private String confirmationCode;

    @FXML
    private Button registerButton;

    @FXML
    private Button confirmEmailButton;

    @FXML
    private Button sendCodeButton;

    @FXML
    private Label confirmMessageLabel;

    @FXML
    private TextField confirmationCodeField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField loginField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField secondNameField;

    @FXML
    private void onSendCodeButtonField(ActionEvent actionEvent) {
        Integer code = new Random().nextInt(100000, 999999);
        confirmationCode = code.toString();
        EmailUtil.createEmail(emailField.getText() , confirmationCode);
        confirmEmailButton.setDisable(false);
        confirmationCodeField.setDisable(false);
        confirmMessageLabel.setVisible(true);
        confirmMessageLabel.setText("Код отправлен");
    }

    @FXML
    private void onConfirmButtonField(ActionEvent event) {
        if (confirmationCode.toString().equals(confirmationCodeField.getText().toString())) {
            codeConfirmed = true;
            confirmMessageLabel.setVisible(true);
            confirmMessageLabel.setText("Код верный");
        }
        else {
            confirmMessageLabel.setVisible(true);
            confirmMessageLabel.setText("Неверный код подтверждения");
        }
    }

    public void onEmailChanged(ActionEvent actionEvent) throws SQLException, IOException {

    }

    @FXML
    private void onRegisterButtonField(ActionEvent actionEvent) throws SQLException, IOException {
        if(!(codeConfirmed && loginConfirmed))
            return;

        connection.sendQuery("INSERT INTO \"Main\".employees (second_name, first_name, email, phone_number, login, password) " +
                "VALUES ('" +
                secondNameField.getText() +
                "', '" +
                firstNameField.getText() +
                "', '" +
                emailField.getText() +
                "', '" +
                phoneNumberField.getText() +
                "', '" +
                loginField.getText() +
                "', '" +
                passwordField.getText() +
                "')");
        firstNameField.getScene().getWindow().hide();
    }

    @FXML
    private void onLoginChanged(ActionEvent actionEvent) throws SQLException, IOException {
        Statement statement = connection.Connect().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM \"Main\".employees WHERE login LIKE '" + loginField.getText() + "'");

        if (resultSet.next())
            loginField.setText("Логин не уникален");
        else
            loginConfirmed = true;
    }
}
