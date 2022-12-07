package com.royalstil.royalstildesktop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;


public class ReportPageController {

    @FXML
    private Button createReportButton;

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
        ConnectionDB.fillTable(reportTable, "SELECT date AS \"Дата\", first_name AS \"Имя\", second_name AS \"Фамилия\", phone_number AS \"Номер телефона\", name AS \"Наименование товара\", used AS \"Б/У\", cost AS \"Цена\", number AS \"Количество\" FROM \"Main\".shopping_cart \n" +
                "INNER JOIN \"Main\".goods ON goods_id = id_goods \n" +
                "INNER JOIN \"Main\".orders ON order_id = id_order \n" +
                "INNER JOIN \"Main\".clients ON orders.client = id_client\n" +
                "WHERE date >= '" + fromDatePicker.getValue() + "' AND \n" +
                "        date <= '" + toDatePicker.getValue() + "' AND passed = true");

    }
}
