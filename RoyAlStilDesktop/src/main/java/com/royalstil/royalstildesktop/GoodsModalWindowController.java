package com.royalstil.royalstildesktop;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class GoodsModalWindowController {


    private ConnectionDB connection = new ConnectionDB();

    @FXML
    private TableView<?> goodsTable;

    @FXML
    private TextField numberField;

    public OrdersPageController parentScene;

    private TableView tableView;

    private String id;

    @FXML
    private void initialize() throws SQLException, IOException {
        ConnectionDB.fillTable(goodsTable, "SELECT id_goods AS \"ID\", name AS \"Наименование\", remind AS \"Остаток\", cost AS \"Цена\", used AS \"Б/У\", name_goods_type AS \"Тип\" FROM \"Main\".goods " +
                "INNER JOIN \"Main\".goods_type on id_goods_type = goods_type_id");
        setListener();
    }


    public void setParentScene(OrdersPageController parentScene) {
        this.parentScene = parentScene;
    }

    public void setTableView(TableView tableView){
        this.tableView = tableView;
    }

    @FXML
    private void check(){
        System.out.println();
    }

    private void setListener(){
        TableView.TableViewSelectionModel selectionModel = goodsTable.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldVal, Object newVal) {
                if (newVal != null){
                    try {
                        HashMap<String, String> valueMap = new HashMap<>();
                        ObservableList valueList = (ObservableList) newVal;
                        for (int i = 0; i < goodsTable.getColumns().size(); i++) {
                            valueMap.put(goodsTable.getColumns().get(i).getText(), valueList.get(i).toString());
                        }
                        ObservableList list = FXCollections.observableArrayList();
                        tableView.getItems().add(new SelectedGoods(Integer.parseInt(valueMap.get("ID")), valueMap.get("Наименование"), Integer.parseInt(numberField.getText()), Double.parseDouble(valueMap.get("Цена"))));
                        goodsTable.getScene().getWindow().hide();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
