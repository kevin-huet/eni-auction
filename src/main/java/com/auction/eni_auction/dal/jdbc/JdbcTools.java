package com.auction.eni_auction.dal.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcTools {

    private static String urldb;
    private static String userdb;
    private static String passworddb;

    //bloc d'initialisation statique
    static {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        urldb = "jdbc:mysql://localhost/auction";
        userdb = "root";
        passworddb = "oktamer22";
    }

    public static Connection getConnection() throws SQLException{

        return DriverManager.getConnection(urldb, userdb, passworddb);
    }

}
