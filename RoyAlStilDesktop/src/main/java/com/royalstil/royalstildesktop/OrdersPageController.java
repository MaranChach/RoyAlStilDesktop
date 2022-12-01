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

public class OrdersPageController extends ElementController {

    //region fields

    private ConnectionDB connectionDB = new ConnectionDB();

    private HashMap<String, String> clientsMap;

    private boolean passed;

    @FXML
    private CheckBox passedCheckBox;

    @FXML
    private Button passButton;

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

    @FXML
    private Label goodsInOrderLabel;

    @FXML
    private Button deleteFromOrderButton;

    //endregion

    @FXML
    private void initialize() throws SQLException, IOException {
        clientsMap = connectionDB.sendQueryHashMap("SELECT id_client, second_name, first_name, email FROM \"Main\".clients");
        List list = new ArrayList<>(clientsMap.keySet());
        clientComboBox.setItems(FXCollections.observableArrayList(list));
        setFonts();
        setGoodsTable(goodsOnOrderTable);
    }

    @FXML
    private void setFonts(){
        Fonts fonts = new Fonts();
        idLabel.setFont(fonts.mainFont);
        passedCheckBox.setFont(fonts.mainFont);
        deleteFromOrderButton.setFont(fonts.miniFont);
        addToOrderButton.setFont(fonts.miniFont);
        idLabel.setFont(fonts.mainFont);
        goodsInOrderLabel.setFont(fonts.mainFont);
        deleteButton.setFont(fonts.miniFont);
        updateButton.setFont(fonts.miniFont);
        saveButton.setFont(fonts.miniFont);
        passButton.setFont(fonts.miniFont);
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
        id = Integer.parseInt(selectedRow.get("ID"));
        idLabel.setText(idLabel.getText() + " № " + id);
        orderDatePicker.setValue(LocalDate.parse(selectedRow.get("Дата")));
        clientComboBox.getSelectionModel().selectFirst();
        for (int i = 0; i < clientComboBox.getItems().size(); i++) {
            if(clientComboBox.getValue().equals(selectedRow.get("Фамилия") +" "+ selectedRow.get("Имя") +" "+ selectedRow.get("Почта") +" "))
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
        connectionDB.sendQuery("DELETE FROM \"Main\".shopping_cart WHERE order_id = " + id);
        connectionDB.sendQuery("DELETE FROM \"Main\".orders WHERE id_order = " + id);
        //ConnectionDB.fillTable(mainClass.mainTable, "select * from \"Main\".orders");
        mainClass.onButtonOrdersClick();
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
        passButton.setDisable(false);
        updateButton.setDisable(false);
        deleteButton.setDisable(false);
        saveButton.setDisable(true);
    }

    public void onPassButtonClick(ActionEvent actionEvent) throws SQLException, IOException {
        connectionDB.sendQuery("UPDATE \"Main\".orders SET" +
                " passed = true " +
                "WHERE id_order = " +
                id);

        for (int i = 0; i < goodsOnOrderTable.getItems().size(); i++) {
            connectionDB.sendQuery("UPDATE \"Main\".goods SET" +
                    " remind = remind - " +
                    goodsOnOrderTable.getItems().get(i).getNumber() +
                    " WHERE id_goods = " + goodsOnOrderTable.getItems().get(i).getId());
        }
        passed = true;
        passedCheckBox.setSelected(passed);
        passedCheckBox.getScene().getWindow().hide();
    }

    public void onDeleteFromOrderButtonClick(ActionEvent actionEvent) {
        SelectionModel<SelectedGoods> selectionModel = goodsOnOrderTable.getSelectionModel();
        SelectedGoods selectedItem = selectionModel.getSelectedItem();
        goodsOnOrderTable.getItems().remove(selectedItem);
    }
}
