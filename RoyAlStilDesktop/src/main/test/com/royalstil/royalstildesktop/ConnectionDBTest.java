package com.royalstil.royalstildesktop;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.sql.*;


public class ConnectionDBTest extends Assert{

    @Test
    public void connect() throws IOException, SQLException {
        ConnectionDB connectionDB = new ConnectionDB();
        Connection expected = DriverManager.getConnection(ConnectionDB.GetConnectionString(), ConnectionDB.GetLogin(), ConnectionDB.GetPassword());
        Connection actual = connectionDB.Connect();
        ResultSet rsExpected = expected.createStatement().executeQuery("SELECT * FROM \"Main\".clients");
        rsExpected.next();
        ResultSet rsActual = actual.createStatement().executeQuery("SELECT * FROM \"Main\".clients");
        rsActual.next();
        assertEquals(rsExpected.getString(1) + rsExpected.getString(2), rsActual.getString(1) + rsActual.getString(2));
    }
}