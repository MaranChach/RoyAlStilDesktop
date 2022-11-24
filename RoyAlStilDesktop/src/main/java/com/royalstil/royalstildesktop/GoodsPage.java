package com.royalstil.royalstildesktop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GoodsPage extends ElementController{


    //region fields

    private ConnectionDB connection = new ConnectionDB();

    private HashMap<String, String> goodsTypeMap;

    ObservableList list;

    @FXML
    private TextField birthdayField;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField emailField;

    @FXML
    private ComboBox<?> goodsTypeComboBox;

    @FXML
    private Label idLabel;

    @FXML
    private TextField nameField;

    @FXML
    private AnchorPane notificationPane;

    @FXML
    private AnchorPane parentPane;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private Button saveButton;

    @FXML
    private Button updateButton;

    @FXML
    private TextField costField;

    @FXML
    private TextField remainsField;

    @FXML
    private CheckBox usedCheckBox;

    //endregion

    @FXML
    private void initialize() throws SQLException, IOException {
        goodsTypeMap = connection.sendQueryGoodsTypeHashMap();
        List list = new ArrayList(goodsTypeMap.keySet());
        goodsTypeComboBox.setItems(FXCollections.observableArrayList(list));
        goodsTypeComboBox.getSelectionModel().select(1);
    }

    @Override
    public void setSelectedRow(HashMap<String, String> selectedRow){
        id = Integer.parseInt(selectedRow.get("id_goods"));
        nameField.setText(selectedRow.get("name"));
        costField.setText(selectedRow.get("cost"));
        remainsField.setText(selectedRow.get("remind"));
        if(selectedRow.get("used").equals("t"))
            usedCheckBox.setSelected(true);

        for (int i = 0; i < goodsTypeComboBox.getItems().size(); i++) {
            if (goodsTypeComboBox.getValue().equals(selectedRow.get("name_goods_type")))
                break;
            goodsTypeComboBox.getSelectionModel().selectNext();
        }
    }

    @FXML
    private void onAddButtonClick(ActionEvent event) throws SQLException, IOException {
        id = connection.sendQueryWithId("insert into \"Main\".goods (name, cost, remind, used, goods_type_id)" +
                "values ('" +
                nameField.getText() +
                "', " +
                costField.getText() +
                ", " +
                remainsField.getText() +
                ", " +
                usedCheckBox.isSelected() +
                ", " +
                goodsTypeMap.get(goodsTypeComboBox.getValue()) +
                ") " +
                "RETURNING id_goods");
        newNotification(notificationPane);
    }

    @FXML
    private void onDeleteButtonClick(ActionEvent event) throws SQLException, IOException {
        connection.sendQuery("DELETE FROM \"Main\".goods WHERE id_goods = " + id);
        costField.getScene().getWindow().hide();
        newNotification(notificationPane);
    }

    @FXML
    private void onUpdateButtonClick(ActionEvent event) throws SQLException, IOException {
        connection.sendQueryWithId("UPDATE \"Main\".goods SET" +
                " name = '" +
                nameField.getText() +
                "', cost = " +
                "'" +
                costField.getText() +
                "', remind = " +
                "'" +
                remainsField.getText() +
                "', used = " +
                "'" +
                usedCheckBox.isSelected() +
                "', goods_type_id = " +
                "'" +
                goodsTypeMap.get(goodsTypeComboBox.getValue()) +
                "' " +
                "WHERE id_goods = " +
                id +
                "RETURNING id_goods");
        newNotification(notificationPane);
    }
}
