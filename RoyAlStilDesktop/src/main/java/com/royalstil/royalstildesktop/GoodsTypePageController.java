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

public class GoodsTypePageController extends ElementController{


    @FXML
    private Button deleteButton;

    @FXML
    private Label idLabel;

    @FXML
    private TextField nameField;

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
        updateButton.setDisable(false);
        deleteButton.setDisable(false);

    }

    @FXML
    void onAddButtonClick(ActionEvent event) throws SQLException, IOException {
        id = connection.sendQueryWithId("INSERT INTO \"Main\".goods_type (name_goods_type) VALUES ('" + nameField.getText() + "') RETURNING id_goods_type");
        updateButton.setDisable(false);
        deleteButton.setDisable(false);
        newNotification(notificationPane);
        mainClass.onButtonGoodsTypeClick(new ActionEvent());
    }

    @FXML
    void onDeleteButtonClick(ActionEvent event) throws SQLException, IOException {
        if(!confirmAction())
            return;
        connection.sendQuery("DELETE FROM \"Main\".goods_type WHERE id_goods_type = " + id);
        mainClass.onButtonGoodsTypeClick(new ActionEvent());
        idLabel.getScene().getWindow().hide();
    }

    @FXML
    void onUpdateButtonClick(ActionEvent event) throws SQLException, IOException {
        id = connection.sendQueryWithId("UPDATE \"Main\".goods_type SET name_goods_type = '" + nameField.getText() + "' WHERE id_goods_type = " + id + " RETURNING id_goods_type");
        newNotification(notificationPane);
        mainClass.onButtonGoodsTypeClick(new ActionEvent());
    }
}
