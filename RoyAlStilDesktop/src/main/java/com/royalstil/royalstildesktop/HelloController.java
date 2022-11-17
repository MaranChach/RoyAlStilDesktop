package com.royalstil.royalstildesktop;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArrayBase;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class HelloController {
    private Font fontInter = new Font(getClass().getResource("InterFont/Inter-V.ttf").toExternalForm(), 18);
    @FXML
    private Label welcomeText;
    @FXML
    private Button searchButton;
    @FXML
    protected TableView tableSex;
    @FXML
    protected TableColumn<Client, Integer> id_client;

    @FXML
    void initialize(){
        searchButton.setFont(fontInter);
        setTableSettings();

    }

    public void setTableSettings(){
        //id_client.setCellValueFactory(new PropertyValueFactory<>("clid"));
    }

    /*@FXML
    protected void onHelloButtonClick() throws SQLException, IOException {
        tableSex.getColumns().clear();
        ConnectionDB connectionDB = new ConnectionDB();
        List<Client> list = connectionDB.selectQuery("select * from \"Main\".clients");
        ObservableList<Client> clients = FXCollections.observableArrayList(list);
        tableSex.setItems(clients);


        TableColumn<Client, Integer> colId = new TableColumn<Client, Integer>("id");
        colId.setCellValueFactory(new PropertyValueFactory<Client, Integer>("clid"));
        tableSex.getColumns().add(colId);

        TableColumn<Client, String> colFirstName = new TableColumn<Client, String>("firstname");
        colFirstName.setCellValueFactory(new PropertyValueFactory<Client, String>("firstName"));
        tableSex.getColumns().add(colFirstName);

        TableColumn<Client, String> colSecondtName = new TableColumn<Client, String>("secondname");
        colSecondtName.setCellValueFactory(new PropertyValueFactory<Client, String>("secondName"));
        tableSex.getColumns().add(colSecondtName);

        TableColumn<Client, String> colLogin = new TableColumn<Client, String>("login");
        colLogin.setCellValueFactory(new PropertyValueFactory<Client, String>("login"));
        tableSex.getColumns().add(colLogin);

        TableColumn<Client, String> colPassword = new TableColumn<Client, String>("password");
        colPassword.setCellValueFactory(new PropertyValueFactory<Client, String>("password"));
        tableSex.getColumns().add(colPassword);

        TableColumn<Client, String> colPhone = new TableColumn<Client, String>("phone");
        colPhone.setCellValueFactory(new PropertyValueFactory<Client, String>("phoneNumber"));
        tableSex.getColumns().add(colPhone);

        TableColumn<Client, String> colEmail = new TableColumn<Client, String>("email");
        colEmail.setCellValueFactory(new PropertyValueFactory<Client, String>("email"));
        tableSex.getColumns().add(colEmail);

    }*/

    private void tableFill(){

    }

}