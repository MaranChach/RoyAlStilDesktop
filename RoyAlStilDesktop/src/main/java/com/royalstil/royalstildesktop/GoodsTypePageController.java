package com.royalstil.royalstildesktop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

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
        nameField.setText(selectedRow.get("name"));
    }

    @FXML
    void onAddButtonClick(ActionEvent event) {

    }

    @FXML
    void onDeleteButtonClick(ActionEvent event) {

    }

    @FXML
    void onUpdateButtonClick(ActionEvent event) {

    }
}
