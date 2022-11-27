package com.royalstil.royalstildesktop;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ConnectionDB {
    private Connection connection = null;
    private Statement statement = null;

    public static String GetConnectionString() throws IOException {
        //Path configPath = Path.of("\\RoyAlStilDesktop\\src\\main\\resources\\config");
        //return Files.readString(configPath);
        return "jdbc:postgresql://46.229.214.241:5432/TrantinDB";
    }

    public static String GetLogin() throws IOException {
        //Path configPath = Path.of("\\src\\config");
        //return Files.readString(configPath);
        return "PKS";
    }

    public static String GetPassword() throws IOException {
        //Path configPath = Path.of("\\src\\config");
        //return Files.readString(configPath);
        return "PKS";
    }

    public Connection Connect() throws IOException, SQLException{
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(GetConnectionString(), GetLogin(), GetPassword());
            this.connection = connection;
            return connection;
        }
        catch (SQLException e){
            System.out.println("Connection error");
            e.printStackTrace();
        }
        this.connection = connection;
        return connection;
    }

    public void Disconnect() throws SQLException {
        try{
            if (this.connection != null)
                connection.close();
            if (this.statement != null)
                statement.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void sendQuery(String query) throws IOException, SQLException {
        Connect();
        try{
            if (connection == null) {
                connection = Connect();
            }
            statement = connection.createStatement();
            statement.execute(query);
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Connection error");
        }
        finally {
            Disconnect();
        }
    }

    public ResultSet selectQuery(String query) throws IOException, SQLException {
        Connect();
        ResultSet resultSet = null;
        try{
            if (connection == null){
                connection = Connect();
            }
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            return resultSet;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Connection error");
        }
        finally {
            Disconnect();
        }
        return resultSet;
    }

    public HashMap selectQueryArr(String query) throws IOException, SQLException {
        Connect();
        ResultSet resultSet = null;
        HashMap result = new HashMap<String, String>();
        try {
            if (connection == null) {
                connection = Connect();
            }
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);



            while (resultSet.next()){
                for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
                    result.put(resultSet.getMetaData().getColumnName(i), resultSet.getString(i));
                }
            }

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection error");
        } finally {
            Disconnect();
        }
        return result;
    }

    public int sendQueryWithId(String query) throws IOException, SQLException {
        Connect();
        ResultSet resultSet = null;
        try {
            if (connection == null) {
                connection = Connect();
            }
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);



            while (resultSet.next()){
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection error");
        } finally {
            Disconnect();
        }
        return 0;
    }

    public HashMap<String, String> sendQueryGoodsTypeHashMap(String query) throws SQLException, IOException {
        HashMap<String, String> hashMap = new HashMap();
        try {
            Connect();
            ResultSet resultSet = connection.createStatement().executeQuery(query);

            while (resultSet.next()){
                String data = "";
                for (int i = 2; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    data += resultSet.getString(i) + " ";
                }
                hashMap.put(data, resultSet.getString(1));
            }
            return hashMap;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            Disconnect();
        }
        return hashMap;
    }

    public static ObservableList fillTable(TableView tableView, String query) throws SQLException, IOException {
        tableView.getColumns().clear();
        tableView.getItems().clear();
        ObservableList tableData = FXCollections.observableArrayList();
        Statement statement = new ConnectionDB().Connect().createStatement();
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

            tableView.getColumns().addAll(col);
        }

        while(resultSet.next()){
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                row.add(resultSet.getString(i));
            }
            tableData.add(row);
        }
        tableView.setItems(tableData);
        return tableData;
    }
}
