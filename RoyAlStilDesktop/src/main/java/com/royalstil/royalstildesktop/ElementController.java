package com.royalstil.royalstildesktop;

import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class ElementController {
    protected int id;
    protected boolean editable = false;

    protected ConnectionDB connection = new ConnectionDB();

    protected HashMap<String, String> selectedRow;

    public void setSelectedRow(HashMap<String, String> selectedRow){
        this.selectedRow = selectedRow;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    protected static void newNotification(Node notificationNode){
        Timer timer = new Timer("notificationTime");
        notificationNode.setVisible(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                notificationNode.setVisible(false);
            }
        }, 3000);
    }

    protected static void setGoodsTable(TableView goodsOnOrderTable){
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

    public static boolean parseBool(String string){
        if (string.equals("t"))
            return true;
        else
            return false;
    }
}
