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

    public static void Disconnect(Connection connection) throws SQLException {
        try{
            connection.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void sendQuery(String query) throws IOException, SQLException {
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
            if (connection != null)
                connection.close();
            if(statement != null)
                statement.close();
        }
    }

    public ResultSet selectQuery(String query) throws IOException, SQLException {
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
            if (connection != null)
                connection.close();
            if(statement != null)
                statement.close();
        }
        return resultSet;
    }

    /*public ArrayList selectQueryArr(String query) throws IOException, SQLException {
        ResultSet resultSet = null;
        ArrayList<Client> result = new ArrayList<Client>();
        try {
            if (connection == null) {
                connection = Connect();
            }
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()){
                result.add(new Client(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getString(6),resultSet.getString(7),resultSet.getDate(8)));
            }

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection error");
        } finally {
            if (connection != null)
                connection.close();
            if (statement != null)
                statement.close();
        }
        return result;
    }*/
}
