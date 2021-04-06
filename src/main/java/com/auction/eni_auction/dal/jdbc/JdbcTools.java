package com.auction.eni_auction.dal.jdbc;

import com.auction.eni_auction.dal.ConnectionProvider;

import java.sql.Connection;
import java.sql.SQLException;


class JdbcTools {
    static Connection connect() {
        Connection cnx = null;
        try {
            cnx = ConnectionProvider.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cnx;
    }
}