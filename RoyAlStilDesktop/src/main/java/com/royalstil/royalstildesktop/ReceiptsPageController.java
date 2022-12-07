package com.royalstil.royalstildesktop;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReceiptsPageController extends ElementController {



    //region fields

    private ConnectionDB connectionDB = new ConnectionDB();

    private HashMap<String, String> providerMap;

    private boolean passed;

    @FXML
    private Button passButton;

    @FXML
    private Button addToOrderButton;

    @FXML
    private Button deleteFromReceiptButton;

    @FXML
    private CheckBox passedCheckBox;

    @FXML
    private ComboBox<?> providerComboBox;

    @FXML
    private Button deleteButton;

    @FXML
    private TableView<SelectedGoods> goodsOnOrderTable;

    @FXML
    private Label idLabel;

    @FXML
    private AnchorPane notificationPane;

    @FXML
    private DatePicker orderDatePicker;

    @FXML
    private AnchorPane parentPane;

    @FXML
    private Button saveButton;

    @FXML
    private Button updateButton;

    //endregion

    @FXML
    private void initialize() throws SQLException, IOException {
        providerMap = connectionDB.sendQueryHashMap("SELECT id_provider, name, inn FROM \"Main\".provider");
        List list = new ArrayList(providerMap.keySet());
        providerComboBox.setItems(FXCollections.observableArrayList(list));
        setGoodsTable(goodsOnOrderTable);
        orderDatePicker.setValue(LocalDate.now());
    }

    @Override
    public void setSelectedRow(HashMap<String, String> selectedRow){
        this.selectedRow = selectedRow;
        passed = parseBool(selectedRow.get("Проведён"));
        passedCheckBox.setSelected(passed);
        saveButton.setDisable(passed);
        deleteButton.setDisable(passed);
        updateButton.setDisable(passed);
        passButton.setDisable(passed);
        addToOrderButton.setDisable(passed);
        deleteFromReceiptButton.setDisable(passed);
        id = Integer.parseInt(selectedRow.get("ID"));
        idLabel.setText(idLabel.getText() + " № " + id);
        orderDatePicker.setValue(LocalDate.parse(selectedRow.get("Дата")));
        providerComboBox.getSelectionModel().selectFirst();
        for (int i = 0; i < providerComboBox.getItems().size(); i++) {
            if(providerComboBox.getValue().equals(selectedRow.get("Поставщик") +" "+ selectedRow.get("inn") + " "))
                break;
            providerComboBox.getSelectionModel().selectNext();
        }

        try {
            Statement statement = connectionDB.Connect().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT goods_id, name, cost, number FROM \"Main\".receipt_of_goods_cart " +
                    "INNER JOIN \"Main\".goods ON goods_id = id_goods WHERE receipt_of_goods_id = " + id);

            while (resultSet.next()){
                goodsOnOrderTable.getItems().add(new SelectedGoods(resultSet.getInt("goods_id"), resultSet.getString("name"), resultSet.getInt("number"), resultSet.getDouble("cost")));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }



    @FXML
    private void onAddToOrderButtonClick(ActionEvent actionEvent) throws IOException {
        Stage newStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GoodsModalWindow.fxml"));
        Scene newScene = new Scene(loader.load());
        newStage.setScene(newScene);
        GoodsModalWindowController controller = loader.getController();
        controller.setTableView(goodsOnOrderTable);
        newStage.setResizable(false);
        newStage.show();
    }

    @FXML
    private void onAddButtonClick(ActionEvent actionEvent) throws SQLException, IOException {
        id = connectionDB.sendQueryWithId("insert into \"Main\".receipt_of_goods (provider, date)" +
                "values ('" +
                providerMap.get(providerComboBox.getValue()) +
                "', '" +
                orderDatePicker.getValue().toString() +
                "') " +
                "RETURNING id_receipt_of_goods");
        addGoodsInCart();
        idLabel.setText("Поступление № " + id);
        newNotification(notificationPane);
        passButton.setDisable(false);
        updateButton.setDisable(false);
        deleteButton.setDisable(false);
        saveButton.setDisable(true);
        mainClass.onButtonReceiptsClick(new ActionEvent());
    }

    @FXML
    private void onDeleteButtonClick(ActionEvent actionEvent) throws SQLException, IOException {
        if(!confirmAction()){
            return;
        }
        connectionDB.sendQuery("DELETE FROM \"Main\".receipt_of_goods_cart WHERE receipt_of_goods_id = " + id);
        connectionDB.sendQuery("DELETE FROM \"Main\".receipt_of_goods WHERE id_receipt_of_goods = " + id);
        mainClass.onButtonReceiptsClick(new ActionEvent());
        idLabel.getScene().getWindow().hide();
    }

    @FXML
    private void onUpdateButtonClick(ActionEvent actionEvent) throws SQLException, IOException {
        connectionDB.sendQueryWithId("UPDATE \"Main\".receipt_of_goods SET" +
                " date = '" +
                orderDatePicker.getValue() +
                "', provider = " +
                "'" +
                providerMap.get(providerComboBox.getValue()) +
                "'" +
                "WHERE id_receipt_of_goods = " +
                id +
                "RETURNING id_receipt_of_goods");
        connectionDB.sendQuery("DELETE FROM \"Main\".receipt_of_goods_cart WHERE receipt_of_goods_id = " + id);
        addGoodsInCart();
        newNotification(notificationPane);
        mainClass.onButtonReceiptsClick(new ActionEvent());
    }

    private void addGoodsInCart() throws SQLException, IOException {
        for (int i = 0; i < goodsOnOrderTable.getItems().size(); i++) {
            connectionDB.sendQuery("INSERT INTO \"Main\".receipt_of_goods_cart (receipt_of_goods_id, goods_id, number)" +
                    "values (" +
                    id +
                    ", '" +
                    goodsOnOrderTable.getItems().get(i).getId() +
                    "', " +
                    goodsOnOrderTable.getItems().get(i).getNumber() +
                    ")" +
                    "RETURNING receipt_of_goods_id");
        }
    }

    public void onPassButtonClick(ActionEvent actionEvent) throws SQLException, IOException {
        connectionDB.sendQuery("UPDATE \"Main\".receipt_of_goods SET" +
                " passed = true " +
                "WHERE id_receipt_of_goods = " +
                id);

        for (int i = 0; i < goodsOnOrderTable.getItems().size(); i++) {
            connectionDB.sendQuery("UPDATE \"Main\".goods SET" +
                    " remind = remind + " +
                    goodsOnOrderTable.getItems().get(i).getNumber() +
                    " WHERE id_goods = " + goodsOnOrderTable.getItems().get(i).getId());
        }
        passed = true;
        passedCheckBox.setSelected(passed);
        passedCheckBox.getScene().getWindow().hide();
    }

    public void onDeleteFromReceiptButtonClick(ActionEvent actionEvent) {
        SelectionModel<SelectedGoods> selectionModel = goodsOnOrderTable.getSelectionModel();
        SelectedGoods selectedItem = selectionModel.getSelectedItem();
        goodsOnOrderTable.getItems().remove(selectedItem);
    }
}
