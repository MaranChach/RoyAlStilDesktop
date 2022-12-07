package com.royalstil.royalstildesktop;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;


public class ReportPageController {

    private Double summary = 0d;
    @FXML
    private TextField summaryField;

    @FXML
    private Button createReportButton;

    @FXML
    private Button createDiagramButton;

    @FXML
    private DatePicker fromDatePicker;

    @FXML
    private Label idLabel;

    @FXML
    private AnchorPane notificationPane;

    @FXML
    private AnchorPane parentPane;

    @FXML
    private DatePicker toDatePicker;

    @FXML
    private TableView reportTable;


    public void createReportButtonClick(ActionEvent event) throws SQLException, IOException {
        if(fromDatePicker.getValue() == null)
            fromDatePicker.setValue(LocalDate.of(1900, 1,1));

        if(toDatePicker.getValue() == null)
            toDatePicker.setValue(LocalDate.of(2100, 1,1));

        ConnectionDB.fillTable(reportTable, "SELECT date AS \"Дата\", first_name AS \"Имя\", second_name AS \"Фамилия\", phone_number AS \"Номер телефона\", name AS \"Наименование товара\", name_goods_type AS \"Тип товара\", used AS \"Б/У\", cost AS \"Цена\", number AS \"Количество\", cost * number AS \"Сумма\" FROM \"Main\".shopping_cart \n" +
                "INNER JOIN \"Main\".goods ON goods_id = id_goods \n" +
                "INNER JOIN \"Main\".orders ON order_id = id_order \n" +
                "INNER JOIN \"Main\".clients ON orders.client = id_client\n" +
                "INNER JOIN \"Main\".goods_type ON goods.goods_type_id = id_goods_type\n" +
                "WHERE date >= '" + fromDatePicker.getValue() + "' AND \n" +
                "date <= '" + toDatePicker.getValue() + "' AND passed = true");
        for (int i = 0; i < reportTable.getItems().size(); i++) {
            ObservableList item = (ObservableList) reportTable.getItems().get(i);

            summary += Double.parseDouble(item.get(9).toString());
        }
        summaryField.setText(summary.toString());
        createDiagramButton.setDisable(false);
    }

    public void createDiagramButtonClick(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ChartPage.fxml"));
        Parent parent = loader.load();

        ChartPageController pageController = loader.getController();
        pageController.setData(reportTable);

        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Диаграмма продаж");
        stage.show();
    }
}
