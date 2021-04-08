package com.auction.eni_auction.dal;

import com.auction.eni_auction.dal.jdbc.JdbcTools;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionProvider {
    private static DataSource dataSource;



    public static Connection getConnection() throws SQLException {
        return JdbcTools.getConnection();
    }
}