package com.royalstil.royalstildesktop;



import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;


public class MainPageController {


    public static String userId;

    private ConnectionDB connection;
    //private final Font mainFont = new Fonts().mainFont;

    private Menus openedMenu;
    private HashMap<String, String> selectedRowMap;
    @FXML
    public TableView<?> mainTable;
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
    private Button goodsTypeButton;
    @FXML
    private Button reportButton;
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
        //setFonts();
        setListener();
    }

    //region OnMenuButtonsClickEvents
    @FXML
    public void onButtonClientsClick(ActionEvent event) throws SQLException, IOException {
        tableData = ConnectionDB.fillTable(mainTable, "SELECT id_client AS \"ID\", second_name AS \"Фамилия\", first_name AS \"Имя\", phone_number AS \"Номер телефона\", email AS \"Почта\", birth_date AS \"Дата рождения\" FROM \"Main\".clients");
        openedMenu = Menus.Clients;
        addButton.setDisable(false);
        mainWindowLabel.setText(clientsButton.getText());
        /*mainTable.getColumns().get(0).setText("ID");
        mainTable.getColumns().get(1).setText("Фамилия");
        mainTable.getColumns().get(2).setText("Имя");
        mainTable.getColumns().get(3).setText("Номер телефона");
        mainTable.getColumns().get(4).setText("Почта");
        mainTable.getColumns().get(5).setText("Дата рождения");*/
    }
    @FXML
    public void onButtonOrdersClick(ActionEvent event) throws SQLException, IOException {
        tableData = ConnectionDB.fillTable(mainTable, "SELECT id_order AS \"ID\", passed AS \"Проведён\", second_name AS \"Фамилия\", first_name AS \"Имя\", email AS \"Почта\", date AS \"Дата\" FROM \"Main\".orders INNER JOIN \"Main\".clients ON client = id_client");
        openedMenu = Menus.Orders;
        addButton.setDisable(false);
        mainWindowLabel.setText(ordersButton.getText());
        /*mainTable.getColumns().get(0).setText("ID");
        mainTable.getColumns().get(1).setText("Проведён");
        mainTable.getColumns().get(2).setText("Фамилия");
        mainTable.getColumns().get(3).setText("Имя");
        mainTable.getColumns().get(4).setText("Почта");
        mainTable.getColumns().get(5).setText("Дата");*/
    }
    @FXML
    public void onButtonGoodsClick(ActionEvent event) throws SQLException, IOException {
        tableData = ConnectionDB.fillTable(mainTable, "SELECT id_goods AS \"ID\", name AS \"Наименование\", remind AS \"Остаток\", cost AS \"Цена\", used AS \"Б/У\", name_goods_type AS \"Тип\", image_url AS \"Картинка\" FROM \"Main\".goods " +
                "INNER JOIN \"Main\".goods_type on id_goods_type = goods_type_id");
        openedMenu = Menus.Goods;
        addButton.setDisable(false);
        mainWindowLabel.setText(goodsButton.getText());
        /*mainTable.getColumns().get(0).setText("ID");
        mainTable.getColumns().get(1).setText("Наименование");
        mainTable.getColumns().get(2).setText("Остаток");
        mainTable.getColumns().get(3).setText("Цена");
        mainTable.getColumns().get(4).setText("Б/У");
        mainTable.getColumns().get(5).setText("Тип");*/
    }
    @FXML
    public void onButtonProvidersClick(ActionEvent event) throws SQLException, IOException {
        tableData = ConnectionDB.fillTable(mainTable, "SELECT id_provider AS \"ID\", name AS \"Наименование\", inn AS \"ИНН\" FROM \"Main\".provider");
        openedMenu = Menus.Providers;
        addButton.setDisable(false);
        mainWindowLabel.setText(providersButton.getText());
        /*mainTable.getColumns().get(0).setText("ID");
        mainTable.getColumns().get(1).setText("Наименование");
        mainTable.getColumns().get(2).setText("ИНН");*/
    }
      @FXML
    public void onButtonReceiptsClick(ActionEvent event) throws SQLException, IOException {
        tableData = ConnectionDB.fillTable(mainTable, "SELECT id_receipt_of_goods AS \"ID\", passed AS \"Проведён\", name AS \"Поставщик\", date AS \"Дата\" FROM \"Main\".receipt_of_goods INNER JOIN \"Main\".provider ON provider = id_provider");
        openedMenu = Menus.Receipts;
        addButton.setDisable(false);
        mainWindowLabel.setText(receiptsButton.getText());
        /*mainTable.getColumns().get(0).setText("ID");
        mainTable.getColumns().get(1).setText("Проведён");
        mainTable.getColumns().get(2).setText("Поставщик");
        mainTable.getColumns().get(3).setText("Дата");*/
    }
    @FXML
    public void onButtonGoodsTypeClick(ActionEvent event) throws SQLException, IOException {
        tableData = ConnectionDB.fillTable(mainTable, "SELECT id_goods_type AS \"ID\", name_goods_type AS \"Наименование\" FROM \"Main\".goods_type");
        openedMenu = Menus.GoodsType;
        addButton.setDisable(false);
        mainWindowLabel.setText(goodsTypeButton.getText());
        /*mainTable.getColumns().get(0).setText("ID");
        mainTable.getColumns().get(1).setText("Наименование");*/
    }

    @FXML
    private void onButtonReportClick(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ReportPage.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Отчёт по продажам");
        stage.show();
    }

    //endregion

    @FXML
    private void onMainTableClick(MouseEvent mouseEvent) {

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
                            if (valueList.get(i) != null){
                                valueMap.put(mainTable.getColumns().get(i).getText(), valueList.get(i).toString());
                            }
                            else
                                valueMap.put(mainTable.getColumns().get(i).getText(), "");
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
        newPageController.setMainClass(this);
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
        ElementController elementController = loader.getController();
        elementController.setMainClass(this);
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
            case Receipts : return getClass().getResource("ReceiptsPage.fxml");
            case GoodsType : return getClass().getResource("GoodsTypePage.fxml");
            case Providers : return getClass().getResource("ProviderPage.fxml");
            default : return null;
        }
    }

    @FXML
    private void setFonts(){
        Fonts fonts = new Fonts();
        addButton.setFont(fonts.mainFont);
        clientsButton.setFont(fonts.mainFont);
        goodsButton.setFont(fonts.mainFont);
        ordersButton.setFont(fonts.mainFont);
        providersButton.setFont(fonts.mainFont);
        receiptsButton.setFont(fonts.mainFont);
        goodsTypeButton.setFont(fonts.mainFont);
        logoLabel.setFont(fonts.logoFont);
        mainWindowLabel.setFont(fonts.mainFont);
    }

    public void onSearch(ActionEvent event) throws SQLException, IOException {
        switch (openedMenu){
            case Clients : ConnectionDB.fillTable(mainTable, "SELECT id_client AS \"ID\", second_name AS \"Фамилия\", first_name AS \"Имя\", phone_number AS \"Номер телефона\", email AS \"Почта\", birth_date AS \"Дата рождения\" FROM \"Main\".clients " +
                    "WHERE second_name LIKE '%" + searchField.getText() + "%' OR " +
                    "first_name LIKE '%" + searchField.getText() + "%' OR " +
                    "phone_number LIKE '%" + searchField.getText() + "%' OR " +
                    "email LIKE '%" + searchField.getText() + "%'  " +
                    "" );
                    break;
            case Goods : ConnectionDB.fillTable(mainTable, "SELECT id_goods AS \"ID\", name AS \"Наименование\", remind AS \"Остаток\", cost AS \"Цена\", used AS \"Б/У\", name_goods_type AS \"Тип\", image_url AS \"Картинка\" FROM \"Main\".goods " +
                    "INNER JOIN \"Main\".goods_type on id_goods_type = goods_type_id " +
                    "WHERE name LIKE '%" + searchField.getText() + "%' OR " +
                    "name_goods_type LIKE '%" + searchField.getText() + "%' " +
                    "" );
                    break;
            case Orders : break;
            case Receipts : break;
            case GoodsType : ConnectionDB.fillTable(mainTable, "SELECT id_goods_type AS \"ID\", name_goods_type AS \"Наименование\" FROM \"Main\".goods_type " +
                    "WHERE name_goods_type LIKE '%" + searchField.getText() + "%'");
                    break;
            case Providers : ConnectionDB.fillTable(mainTable, "SELECT id_provider AS \"ID\", name AS \"Наименование\", inn AS \"ИНН\" FROM \"Main\".provider " +
                    "WHERE name LIKE '%" + searchField.getText() + "%' OR " +
                    "inn LIKE '%" + searchField.getText() + "%' " +
                    "");
                    break;
            default : break;
        }
    }



    public void setFontsGlob(Node... nodes){
        for (Node node : nodes) {

        }
    }

    //region notes
    /*
    provider insert = INSERT INTO "Main".provider (name, inn) VALUES ('ООО КрутоеПредприятие', '123123123123')

     */
    //endregion
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
