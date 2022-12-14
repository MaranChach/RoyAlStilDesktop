package com.royalstil.royalstildesktop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class LoginPageController extends ElementController {


    //region fields
    private ConnectionDB connection = new ConnectionDB();
    @FXML
    private Button loginButton;

    @FXML
    private TextField loginField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button registerButton;

    @FXML
    private Label unknownLoginPasswordLabel;

    @FXML
    private Label loginLogoLabel;
    //endregion

    @FXML
    private void initialize(){
        //setFonts();
    }

    private void setFonts(){
        Fonts fonts = new Fonts();
        unknownLoginPasswordLabel.setFont(fonts.mainFont);
        loginField.setFont(fonts.mainFont);
        passwordField.setFont(fonts.mainFont);
        loginButton.setFont(fonts.miniFont);
        registerButton.setFont(fonts.miniFont);
        loginLogoLabel.setFont(fonts.mainFont);
    }

    @FXML
    void onLoginButtonClick(ActionEvent event) throws IOException, SQLException {


        Statement statement = connection.Connect().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM \"Main\".employees WHERE login = " +
                "'" +
                loginField.getText() + "'");

        if(!resultSet.next()){
            newNotification(unknownLoginPasswordLabel);
            return;
        }
        if (!resultSet.getString("password").equals(passwordField.getText())){
            newNotification(unknownLoginPasswordLabel);
            return;
        }


        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setTitle("?????????????? ????????");
        stage.setScene(scene);
        stage.show();
        loginField.getScene().getWindow().hide();
    }

    @FXML
    void onRegisterButtonClick(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("RegistrationPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setTitle("??????????????????????");
        stage.setScene(scene);
        stage.show();
    }
}
