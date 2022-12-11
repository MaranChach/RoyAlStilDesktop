package com.royalstil.royalstildesktop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class ChartPageController {

    private TableView reportTable;
    @FXML
    private PieChart salesChart;

    @FXML
    private Label caption;

    @FXML
    private void initialize(){

    }

    public void setData(TableView reportTable) {
        this.reportTable = reportTable;

        ObservableList<PieChart.Data> dataList = FXCollections.observableArrayList();
        HashMap<String, Double> dataMap = new HashMap<>();
        for (int i = 0; i < reportTable.getItems().size(); i++) {
            ObservableList element = (ObservableList) reportTable.getItems().get(i);
            if(!dataMap.containsKey(element.get(5))){
                dataMap.put(element.get(5).toString(), Double.parseDouble(element.get(9).toString()));
            }
            else{
                dataMap.put(element.get(5).toString(), dataMap.get(element.get(5).toString()) + Double.parseDouble(element.get(9).toString()));
            }
        }
        dataMap.forEach((key, value) -> dataList.add(new PieChart.Data(key, value)));
        salesChart.setData(dataList);
        for (final PieChart.Data data : salesChart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            caption.setTranslateX(data.getNode().getTranslateX());
                               caption.setTranslateY(data.getNode().getTranslateY());
                            caption.setText(String.valueOf(data.getPieValue()) + " руб");
                        }
                    });
        }

    }

    public void setReportTable(TableView reportTable) {

    }


}

