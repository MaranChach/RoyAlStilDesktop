package com.royalstil.royalstildesktop;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;


public class MainPage{

    private ConnectionDB connection;
    private final Font mainFont = Font.loadFont(getClass().getResource("InterFont/Inter-Bold.otf").toExternalForm(), 18);

    private HashMap<String, String> selectedRowMap;
    @FXML
    private TableView<?> mainTable;
    private ObservableList tableData;

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
        fillTable("SELECT * FROM \"Main\".clients");
        mainTable.setItems(tableData);
        mainWindowLabel.setText("Клиенты");
    }
    @FXML
    private void onButtonOrdersClick(ActionEvent event){

    }
    @FXML
    private void onButtonGoodsClick(ActionEvent event){

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
    private void fillTable(String query) throws SQLException, IOException {
        mainTable.getColumns().clear();
        tableData = FXCollections.observableArrayList();
        Statement statement = connection.Connect().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
            //We are using non property style for making dynamic table
            final int j = i;
            TableColumn col = new TableColumn(resultSet.getMetaData().getColumnName(i + 1));
            col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(j).toString());
                }
            });

            mainTable.getColumns().addAll(col);
            System.out.println("Column [" + i + "] ");
        }

        while(resultSet.next()){
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                //Iterate Column
                row.add(resultSet.getString(i));
            }
            System.out.println("Row [1] added " + row);
            tableData.add(row);
        }
    }


    public void getRowData(){

    }

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
                        openNewClientScene();
                    }catch (IOException exception){
                        exception.printStackTrace();
                    }
                }
            }
        });
    }

    private void openNewClientScene() throws IOException {
        Stage clientStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ClientPage.fxml"));
        Parent root = loader.load();
        if(selectedRowMap != null){
            ElementController clientPageController = loader.getController();
            clientPageController.setSelectedRow(selectedRowMap);
        }
        Scene clientScene = new Scene(root);
        clientStage.setScene(clientScene);
        clientStage.setResizable(false);
        clientStage.setTitle("Клиент");
        clientStage.show();
    }

    @FXML
    private void onAddClientClick(ActionEvent actionEvent) throws IOException {
        selectedRowMap = null;
        openNewClientScene();
    }


    /*private void reOpenTable(TableView newTable){
        if(openedTable != null){
            openedTable.setVisible(false);
        }
        openedTable = newTable;
        openedTable.setVisible(true);
    }*/

}
