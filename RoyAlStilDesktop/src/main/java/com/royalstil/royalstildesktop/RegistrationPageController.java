package com.royalstil.royalstildesktop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
        //setFonts();
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
    private void onSendCodeButtonField(ActionEvent actionEvent) throws SQLException, IOException {
        if(!onEmailChanged(new ActionEvent()))
            return;

        Integer code = new Random().nextInt(100000, 999999);
        confirmationCode = code.toString();
        EmailUtil.createEmail(emailField.getText() , confirmationCode);
        confirmEmailButton.setDisable(false);
        confirmationCodeField.setDisable(false);

        confirmMessageLabel.setText("Код отправлен");
        newNotification(confirmMessageLabel);
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

    public boolean onEmailChanged(ActionEvent actionEvent) throws SQLException, IOException {
        codeConfirmed = false;
        if (FieldsMatch.emailCheck(emailField.getText())){
            sendCodeButton.setDisable(false);
            return true;
        }
        else {
            //sendCodeButton.setDisable(true);
            confirmMessageLabel.setText("Некорректный адрес");
            newNotification(confirmMessageLabel);
            return false;
        }
    }

    @FXML
    private void onRegisterButtonField(ActionEvent actionEvent) throws SQLException, IOException {
        onLoginChanged(new ActionEvent());
        onPhoneNumberChanged(new ActionEvent());

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
        ResultSet resultSet = statement.executeQuery("SELECT USER " + loginField.getText());//* FROM \"Main\".employees WHERE login LIKE '" + loginField.getText() + "'");

        if (resultSet.next())
            loginField.setText("Логин не уникален");
        else
            loginConfirmed = true;
    }

    @FXML
    private void onPhoneNumberChanged(ActionEvent actionEvent) {
        if (phoneNumberField.getText().equals("")){
            phoneNumberConfirmed = false;
            phoneNumberField.setText("Введите номер");
            return;
        }

        if (FieldsMatch.phoneNumberCheck(phoneNumberField.getText())){
            phoneNumberConfirmed = true;
        }
        else {
            phoneNumberConfirmed = false;
            phoneNumberField.setText("Некорректный номер");
        }

    }
}
