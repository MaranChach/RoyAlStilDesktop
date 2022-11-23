package com.royalstil.royalstildesktop;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
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
}
