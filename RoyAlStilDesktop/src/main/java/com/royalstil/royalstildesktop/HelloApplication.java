package com.royalstil.royalstildesktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("LoginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setTitle("Вход");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        launch();

        /*(ClassUML classUML = new ClassUML();
        Class[] classes = new Class[]{
                ChartPageController.class, Client.class, ClientPageController.class,
                ConnectionDB.class, ElementController.class, EmailUtil.class,
                FieldsMatch.class, Fonts.class, GoodsModalWindowController.class,
                GoodsPageController.class, GoodsTypePageController.class, HelloApplication.class,
                LoginPageController.class, MainPageController.class, Menus.class,
                OrdersPageController.class, ProvidersPageController.class, ReportPageController.class,
                RegistrationPageController.class, ReportPageController.class, SelectedGoods.class, ReceiptsPageController.class
        };

        for (Class className : classes) {
            classUML.printSummary(className);
        }*/
    }
}