package com.royalstil.royalstildesktop;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainPage{


    private ConnectionDB connection;
    private final Font mainFont = Font.loadFont(getClass().getResource("InterFont/Inter-Regular.otf").toExternalForm(), 20);

    private Menus openedMenu;
    private HashMap<String, String> selectedRowMap;
    @FXML
    private TableView<?> mainTable;
    private ObservableList tableData;

    @FXML
    private TextField searchField;

    //region buttonFields
    @FXML
    private Button addButton;
    @FXML
    private Button clientsButton;
    @FXML
    private Button goodsButton;
    @FXML
    private Button ordersButton;
    @FXML
    private Button providersButton;
    @FXML
    private Button receiptsButton;
    @FXML
    private Button writeOffButton;
    //endregion
    //region labelFields
    @FXML
    private Label logoLabel;
    @FXML
    private Label mainWindowLabel;
    //endregion

    @FXML
    private void initialize(){
        connection = new ConnectionDB();
        setFonts();
        setListener();
    }

    //region OnMenuButtonsClickEvents
    @FXML
    private void onButtonClientsClick(ActionEvent event) throws SQLException, IOException {
        tableData = ConnectionDB.fillTable(mainTable, "SELECT id_client, second_name, first_name, phone_number, email, birth_date FROM \"Main\".clients");
        openedMenu = Menus.Clients;
        addButton.setDisable(false);
    }
    @FXML
    private void onButtonOrdersClick(ActionEvent event) throws SQLException, IOException {
        tableData = ConnectionDB.fillTable(mainTable, "SELECT id_order, second_name, first_name, email, date FROM \"Main\".orders INNER JOIN \"Main\".clients ON client = id_client");
        openedMenu = Menus.Orders;
        addButton.setDisable(false);
    }
    @FXML
    private void onButtonGoodsClick(ActionEvent event) throws SQLException, IOException {
        tableData = ConnectionDB.fillTable(mainTable, "SELECT id_goods, name, remind, cost, used, name_goods_type FROM \"Main\".goods " +
                "INNER JOIN \"Main\".goods_type on id_goods_type = goods_type_id");
        openedMenu = Menus.Goods;
        addButton.setDisable(false);
    }
    @FXML
    private void onButtonProvidersClick(ActionEvent event){

    }
      @FXML
    private void onButtonReceiptsClick(ActionEvent event){

    }
    @FXML
    private void onButtonWriteOffClick(ActionEvent event){

    }
    //endregion

    @FXML
    private void onMainTableClick(MouseEvent mouseEvent) {
        var a = mainTable.getSelectionModel().getSelectedItem();
    }

    private void setListener(){
        TableView.TableViewSelectionModel selectionModel = mainTable.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldVal, Object newVal) {
                if (newVal != null){
                    try {
                        HashMap<String, String> valueMap = new HashMap<>();
                        ObservableList valueList = (ObservableList) newVal;
                        for (int i = 0; i < mainTable.getColumns().size(); i++) {
                            valueMap.put(mainTable.getColumns().get(i).getText(), valueList.get(i).toString());
                        }
                        selectedRowMap = valueMap;
                        openNewScene(valueMap);
                    }catch (IOException exception){
                        exception.printStackTrace();
                    }
                }
            }
        });
    }

    private void openNewScene(HashMap<String, String> valueMap) throws IOException {
        Stage newStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getSceneFile());
        Parent root = loader.load();
        ElementController newPageController = loader.getController();
        newPageController.setSelectedRow(valueMap);
        newPageController.setEditable(true);
        Scene newScene = new Scene(root);
        newStage.setScene(newScene);
        newStage.setResizable(false);
        newStage.show();
    }

    private void openNewScene() throws IOException {
        Stage newStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getSceneFile());
        Parent root = loader.load();
        Scene newScene = new Scene(root);
        newStage.setScene(newScene);
        newStage.setResizable(false);
        newStage.show();
    }

    @FXML
    private void onAddButtonClick(ActionEvent actionEvent) throws IOException {
        openNewScene();
    }

    private URL getSceneFile(){
        switch (openedMenu){
            case Clients : return getClass().getResource("ClientPage.fxml");
            case Goods : return getClass().getResource("GoodsPage.fxml");
            case Orders : return getClass().getResource("OrdersPage.fxml");
            default : return null;
        }
    }

    @FXML
    private void setFonts(){
        addButton.setFont(mainFont);
        clientsButton.setFont(mainFont);
        goodsButton.setFont(mainFont);
        ordersButton.setFont(mainFont);
        providersButton.setFont(mainFont);
        receiptsButton.setFont(mainFont);
        writeOffButton.setFont(mainFont);
        logoLabel.setFont(mainFont);
        mainWindowLabel.setFont(mainFont);
    }

    public void onSearch(ActionEvent event) {
    }

    //region garbage
    /*
    private void fillTable(TableView tableView, String query) throws SQLException, IOException {
        mainTable.getColumns().clear();
        mainTable.getItems().clear();
        tableData = FXCollections.observableArrayList();
        Statement statement = connection.Connect().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
            final int j = i;
            TableColumn col = new TableColumn(resultSet.getMetaData().getColumnName(i + 1));
            col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(j).toString());
                }
            });

            mainTable.getColumns().addAll(col);
        }

        while(resultSet.next()){
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                row.add(resultSet.getString(i));
            }
            tableData.add(row);
        }
        tableView.setItems(tableData);
    }
    */

    /*private void reOpenTable(TableView newTable){
        if(openedTable != null){
            openedTable.setVisible(false);
        }
        openedTable = newTable;
        openedTable.setVisible(true);
    }*/
    //endregion

}
