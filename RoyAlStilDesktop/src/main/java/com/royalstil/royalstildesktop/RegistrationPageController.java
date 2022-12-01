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

public class RegistrationPageController extends ElementController{


    private boolean codeConfirmed = false;

    private boolean loginConfirmed = false;

    private boolean phoneNumberConfirmed = false;

    private ConnectionDB connection = new ConnectionDB();

    private String confirmationCode;

    @FXML
    private Button registerButton;

    @FXML
    private Button confirmEmailButton;

    @FXML
    private Button sendCodeButton;

    @FXML
    private Label errorRegisterLabel;

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
    private void initialize(){
        setFonts();
    }

    private void setFonts(){
        Fonts fonts = new Fonts();
        confirmationCodeField.setFont(fonts.mainFont);
        firstNameField.setFont(fonts.mainFont);
        secondNameField.setFont(fonts.mainFont);
        passwordField.setFont(fonts.mainFont);
        loginField.setFont(fonts.mainFont);
        emailField.setFont(fonts.mainFont);
        phoneNumberField.setFont(fonts.mainFont);
        confirmEmailButton.setFont(fonts.buttonFont);
        sendCodeButton.setFont(fonts.buttonFont);
        registerButton.setFont(fonts.mainFont);

    }

    @FXML
    private void onSendCodeButtonField(ActionEvent actionEvent) {
        Integer code = new Random().nextInt(100000, 999999);
        confirmationCode = code.toString();
        EmailUtil.createEmail(emailField.getText() , confirmationCode);
        confirmEmailButton.setDisable(false);
        confirmationCodeField.setDisable(false);

        confirmMessageLabel.setText("Код отправлен");
        newNotification(confirmationCodeField);
    }

    @FXML
    private void onConfirmButtonField(ActionEvent event) {
        if (confirmationCode.toString().equals(confirmationCodeField.getText().toString())) {
            codeConfirmed = true;

            confirmMessageLabel.setText("Код верный");
            newNotification(confirmMessageLabel);
        }
        else {
            confirmMessageLabel.setText("Неверный код подтверждения");
            newNotification(confirmMessageLabel);
        }
    }

    public void onEmailChanged(ActionEvent actionEvent) throws SQLException, IOException {
        codeConfirmed = false;
        if (FieldsMatch.emailCheck(emailField.getText())){
            sendCodeButton.setDisable(false);
        }
        else {
            sendCodeButton.setDisable(true);
        }
        System.out.println();
    }

    @FXML
    private void onRegisterButtonField(ActionEvent actionEvent) throws SQLException, IOException {
        if(!(codeConfirmed && loginConfirmed && phoneNumberConfirmed)){
            newNotification(errorRegisterLabel);
            return;
        }


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

    @FXML
    private void onPhoneNumberChanged(ActionEvent actionEvent) {
        if (FieldsMatch.phoneNumberCheck(phoneNumberField.getText())){
            phoneNumberConfirmed = true;
        }
        else
            phoneNumberConfirmed = false;
    }
}
