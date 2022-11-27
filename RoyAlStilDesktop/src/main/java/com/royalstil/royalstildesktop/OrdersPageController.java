package com.royalstil.royalstildesktop;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrdersPageController extends ElementController {

    //region fields

    private ConnectionDB connectionDB = new ConnectionDB();

    private HashMap<String, String> clientsMap;



    @FXML
    private Button addToOrderButton;

    @FXML
    private ComboBox<?> clientComboBox;

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
        clientsMap = connectionDB.sendQueryGoodsTypeHashMap("SELECT id_client, second_name, first_name, email FROM \"Main\".clients");
        List list = new ArrayList<>(clientsMap.keySet());
        clientComboBox.setItems(FXCollections.observableArrayList(list));

        TableColumn colId = new TableColumn("ID");
        colId.setCellValueFactory(new PropertyValueFactory<SelectedGoods, String>("id"));
        goodsOnOrderTable.getColumns().add(colId);

        TableColumn colName = new TableColumn("Наименование");
        colName.setCellValueFactory(new PropertyValueFactory<SelectedGoods, String>("name"));
        goodsOnOrderTable.getColumns().add(colName);

        TableColumn colNumber = new TableColumn("Количество");
        colNumber.setCellValueFactory(new PropertyValueFactory<SelectedGoods, String>("number"));
        goodsOnOrderTable.getColumns().add(colNumber);

        TableColumn colCost = new TableColumn("Цена");
        colCost.setCellValueFactory(new PropertyValueFactory<SelectedGoods, String>("cost"));
        goodsOnOrderTable.getColumns().add(colCost);

        TableColumn colSum = new TableColumn("Сумма");
        colSum.setCellValueFactory(new PropertyValueFactory<SelectedGoods, String>("sum"));
        goodsOnOrderTable.getColumns().add(colSum);
    }

    @Override
    public void setSelectedRow(HashMap<String, String> selectedRow){
        this.selectedRow = selectedRow;
        deleteButton.setDisable(false);
        updateButton.setDisable(false);
        id = Integer.parseInt(selectedRow.get("id_order"));
        idLabel.setText(idLabel.getText() + " № " + id);
        orderDatePicker.setValue(LocalDate.parse(selectedRow.get("date")));
        clientComboBox.getSelectionModel().selectFirst();
        for (int i = 0; i < clientComboBox.getItems().size(); i++) {
            if(clientComboBox.getValue().equals(selectedRow.get("second_name") +" "+ selectedRow.get("first_name") +" "+ selectedRow.get("email") +" "))
                break;
            clientComboBox.getSelectionModel().selectNext();
        }

        try {
            Statement statement = connectionDB.Connect().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT goods_id, name, cost, number FROM \"Main\".shopping_cart " +
                    "INNER JOIN \"Main\".goods ON goods_id = id_goods WHERE order_id = " + id);

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
        id = connectionDB.sendQueryWithId("insert into \"Main\".orders (client, date)" +
                "values ('" +
                clientsMap.get(clientComboBox.getValue()) +
                "', '" +
                orderDatePicker.getValue().toString() +
                "') " +
                "RETURNING id_order");
        addGoodsInCart();
        idLabel.setText("Заказ № " + id);
        newNotification(notificationPane);
    }

    @FXML
    private void onDeleteButtonClick(ActionEvent actionEvent) throws SQLException, IOException {
        connectionDB.sendQuery("DELETE FROM \"Main\".orders WHERE id_order = " + id);
        connectionDB.sendQuery("DELETE FROM \"Main\".shopping_cart WHERE order_id = " + id);
        idLabel.getScene().getWindow().hide();
    }

    @FXML
    private void onUpdateButtonClick(ActionEvent actionEvent) throws SQLException, IOException {
        connectionDB.sendQueryWithId("UPDATE \"Main\".orders SET" +
                " date = '" +
                orderDatePicker.getValue() +
                "', client = " +
                "'" +
                clientsMap.get(clientComboBox.getValue()) +
                "'" +
                "WHERE id_order = " +
                id +
                "RETURNING id_order");
        connectionDB.sendQuery("DELETE FROM \"Main\".shopping_cart WHERE order_id = " + id);
        addGoodsInCart();
        newNotification(notificationPane);
    }

    private void addGoodsInCart() throws SQLException, IOException {
        for (int i = 0; i < goodsOnOrderTable.getItems().size(); i++) {
            connectionDB.sendQuery("INSERT INTO \"Main\".shopping_cart (order_id, goods_id, number)" +
                    "values (" +
                    id +
                    ", '" +
                    goodsOnOrderTable.getItems().get(i).getId() +
                    "', " +
                    goodsOnOrderTable.getItems().get(i).getNumber() +
                    ")" +
                    "RETURNING order_id");
        }
    }
}
