package com.royalstil.royalstildesktop;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Font;

import javax.security.auth.callback.Callback;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;



public class MainPage{

    private ConnectionDB connection;
    private Font mainFont = Font.loadFont(getClass().getResource("InterFont/Inter-Bold.otf").toExternalForm(), 18);

    @FXML
    private TableView<?> mainTable;

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
    //endregion


    @FXML
    private void initialize(){
        connection = new ConnectionDB();
        setFonts();
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
    }

    /*private void fillTable(String query) throws SQLException, IOException {
        ResultSet resultSet = connection.selectQuery(query);
        for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
            final int j = i;
            TableColumn newCol = new TableColumn(resultSet.getMetaData().getColumnName(i + 1));

            newCol.setCellValueFactory(new Callback(<TableColumn.CellDataFeatures<ObservableList, String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> cellDataFeatures) {
                    return new SimpleStringProperty(cellDataFeatures.getValue().get(j).toString());
                }
            });

            mainTable.getColumns().add();
        }

        while(resultSet.next()){

        }
    }*/

    //region OnMenuButtonsClickEvents
    @FXML
    private void onButtonClientsClick(ActionEvent event){

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



    /*private void reOpenTable(TableView newTable){
        if(openedTable != null){
            openedTable.setVisible(false);
        }
        openedTable = newTable;
        openedTable.setVisible(true);
    }*/

}
